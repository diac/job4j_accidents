package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class AccidentHibernateRepository implements AccidentRepository {

    private static final String FIND_ALL_QUERY = "SELECT a FROM Accident a";

    private static final String FIND_BY_ID_QUERY = "SELECT a FROM Accident a WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Accident WHERE id = :fId";

    private final SessionFactory sf;

    @Override
    public List<Accident> findAll() {
        return tx(session -> session.createQuery(FIND_ALL_QUERY, Accident.class).list());
    }

    @Override
    public Optional<Accident> findById(int id) {
        return tx(session -> {
            var sq = session.createQuery(FIND_BY_ID_QUERY, Accident.class);
            sq.setParameter("fId", id);
            return sq.uniqueResultOptional();
        });
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        return tx(session -> {
            session.persist(accident);
            return Optional.of(accident);
        });
    }

    @Override
    public boolean update(Accident accident) {
        return tx(session -> {
            Accident merged = (Accident) session.merge(accident);
            return accident.equals(merged);
        });
    }

    @Override
    public boolean delete(Accident accident) {
        return tx(session -> {
            var sq = session.createQuery(DELETE_QUERY, Accident.class);
            sq.setParameter("fId", accident.getId());
            return sq.executeUpdate() > 0;
        });
    }

    @Override
    public boolean delete(int id) {
        return tx(session -> {
            var sq = session.createQuery(DELETE_QUERY, Accident.class);
            sq.setParameter("fId", id);
            return sq.executeUpdate() > 0;
        });
    }

    private <T> T tx(Function<Session, T> command) {
        var session = sf
                .withOptions()
                .openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}