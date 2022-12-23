package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.AccidentTypeMapper;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM accident_type;";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM accident_type WHERE id = ?;";

    private static final String ADD_QUERY = "INSERT INTO accident_type(name) VALUES (?);";

    private static final String UPDATE_QUERY = "UPDATE accident_type SET name = ? WHERE id = ?;";

    private static final String DELETE_QUERY = "DELETE FROM accident_type WHERE id = ?;";

    private final JdbcTemplate jdbc;

    private final AccidentTypeMapper accidentTypeMapper;

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query(
                FIND_ALL_QUERY,
                accidentTypeMapper
        );
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject(
                        FIND_BY_ID_QUERY,
                        accidentTypeMapper,
                        id
                )
        );
    }

    @Override
    public Optional<AccidentType> add(AccidentType accidentType) {
        jdbc.update(
                ADD_QUERY,
                accidentType.getName()
        );
        return Optional.of(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType) {
        return jdbc.update(
                UPDATE_QUERY,
                accidentType.getName(),
                accidentType.getId()
        ) > 0;
    }

    @Override
    public boolean delete(AccidentType accidentType) {
        return jdbc.update(
                DELETE_QUERY,
                accidentType.getId()
        ) > 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                DELETE_QUERY,
                id
        ) > 0;
    }
}