package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernateRepository implements AccidentDefaultRepository {

    private static final String FIND_ALL_QUERY = "SELECT a FROM Accident a JOIN FETCH a.rules";

    private static final String FIND_BY_ID_QUERY = "SELECT a FROM Accident a JOIN FETCH a.rules WHERE a.id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM Accident WHERE id = :fId";

    private final CrudRepository crudRepository;

    @Override
    public List<Accident> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, Accident.class);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                Accident.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        return crudRepository.optional(session -> {
            session.persist(accident);
            return accident;
        });
    }

    @Override
    public boolean update(Accident accident) {
        return crudRepository.execute(session -> {
            Accident merged = (Accident) session.merge(accident);
            return accident.equals(merged);
        });
    }

    @Override
    public boolean delete(Accident accident) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", accident.getId()));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}