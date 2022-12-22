package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final RuleJdbcTemplate ruleJdbcTemplate;

    private static final String FIND_ALL_QUERY = """
            SELECT 
                * 
            FROM 
                accident AS Accident
            INNER JOIN
                accident_type AS AccidentType ON AccidentType.id = Accident.type_id;
            """;

    private static final String FIND_BY_ID_QUERY = """
            SELECT 
                * 
            FROM 
                accident AS Accident
            INNER JOIN
                accident_type AS AccidentType ON AccidentType.id = Accident.type_id    
            WHERE 
                Accident.id = ?;""";

    private static final String ADD_QUERY = """
            INSERT INTO 
                accident(name, text, address, type_id) 
            VALUES (?, ?, ?, ?);""";

    private static final String UPDATE_QUERY = """
            UPDATE 
                accident 
            SET 
                name = ?,
                text = ?,
                address = ?,
                type_id = ? 
            WHERE 
                id = ?;""";

    private static final String DELETE_QUERY = "DELETE FROM accident WHERE id = ?;";

    private static final String DELETE_RELATED_RULES_BY_ACCIDENT_ID_QUERY
            = "DELETE FROM accidents_rules WHERE accident_id = ?";

    private static final String ASSOCIATE_ACCIDENT_WITH_RULE_QUERY
            = "INSERT INTO accidents_rules(accident_id, rule_id) VALUES (?, ?)";

    private final JdbcTemplate jdbc;

    @Override
    public List<Accident> findAll() {
        return jdbc.query(
                FIND_ALL_QUERY,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt(1));
                    accident.setName(rs.getString(2));
                    accident.setText(rs.getString(3));
                    accident.setAddress(rs.getString(4));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt(6));
                    accidentType.setName(rs.getString(7));
                    accident.setType(accidentType);
                    accident.setRules(new HashSet<>(ruleJdbcTemplate.findAllByAccidentId(accident.getId())));
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
                            accident.setId(rs.getInt(1));
                            accident.setName(rs.getString(2));
                            accident.setText(rs.getString(3));
                            accident.setAddress(rs.getString(4));
                            AccidentType accidentType = new AccidentType();
                            accidentType.setId(rs.getInt(6));
                            accidentType.setName(rs.getString(7));
                            accident.setType(accidentType);
                            accident.setRules(new HashSet<>(ruleJdbcTemplate.findAllByAccidentId(accident.getId())));
                            return accident;
                        },
                        id
                )
        );
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement statement = con.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, holder);
        accident.setId((Integer) holder.getKeys().get("id"));
        accident.getRules().forEach(rule -> jdbc.update(ASSOCIATE_ACCIDENT_WITH_RULE_QUERY, accident.getId(), rule.getId()));
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident accident) {
        boolean success = jdbc.update(
                UPDATE_QUERY,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        ) > 0;
        jdbc.update(
                DELETE_RELATED_RULES_BY_ACCIDENT_ID_QUERY,
                accident.getId()
        );
        accident.getRules().forEach(rule -> {
            jdbc.update(ASSOCIATE_ACCIDENT_WITH_RULE_QUERY, accident.getId(), rule.getId());
        });
        return success;
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