package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernateRepository implements AccidentTypeDefaultRepository {

    private static final String FIND_ALL_QUERY = "SELECT at FROM AccidentType at";

    private static final String FIND_BY_ID_QUERY = "SELECT at FROM AccidentType at WHERE id = :fId";

    private static final String DELETE_QUERY = "DELETE FROM AccidentType WHERE id = :fId";

    private final CrudRepository crudRepository;

    @Override
    public List<AccidentType> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, AccidentType.class);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                AccidentType.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<AccidentType> add(AccidentType accidentType) {
        return crudRepository.optional(session -> {
            session.persist(accidentType);
            return accidentType;
        });
    }

    @Override
    public boolean update(AccidentType accidentType) {
        return crudRepository.execute(session -> {
            AccidentType merged = (AccidentType) session.merge(accidentType);
            return accidentType.equals(merged);
        });
    }

    @Override
    public boolean delete(AccidentType accidentType) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", accidentType.getId()));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}