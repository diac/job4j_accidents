package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM accident;";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM accident WHERE id = ?;";

    private static final String ADD_QUERY = "INSERT INTO accident(name) VALUES (?);";

    private static final String UPDATE_QUERY = "UPDATE accident SET name = ? WHERE id = ?;";

    private static final String DELETE_QUERY = "DELETE FROM accident WHERE id = ?;";

    private final JdbcTemplate jdbc;

    @Override
    public List<Accident> findAll() {
        return jdbc.query(
                FIND_ALL_QUERY,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    return accident;
                }
        );
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject(
                        FIND_BY_ID_QUERY,
                        (rs, row) -> {
                            Accident accident = new Accident();
                            accident.setId(rs.getInt("id"));
                            accident.setName(rs.getString("name"));
                            accident.setText(rs.getString("text"));
                            accident.setAddress(rs.getString("address"));
                            return accident;
                        },
                        id
                )
        );
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        jdbc.update(
                ADD_QUERY,
                accident.getName()
        );
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return jdbc.update(
                UPDATE_QUERY,
                accident.getName()
        ) > 0;
    }

    @Override
    public boolean delete(Accident accident) {
        return jdbc.update(
                DELETE_QUERY,
                accident.getId()
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