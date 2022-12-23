package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.RuleMapper;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate implements RuleRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM rule;";

    private static final String FIND_ALL_BY_ACCIDENT_ID_QUERY = """
            SELECT
                *
            FROM
                rule
            WHERE
                id IN (
                SELECT
                    rule_id
                FROM
                    accidents_rules
                WHERE
                    accident_id = ?
                );
            """;

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM rule WHERE id = ?;";

    private static final String ADD_QUERY = "INSERT INTO rule(name) VALUES (?);";

    private static final String UPDATE_QUERY = "UPDATE rule SET name = ? WHERE id = ?;";

    private static final String DELETE_QUERY = "DELETE FROM rule WHERE id = ?;";

    private final JdbcTemplate jdbc;

    private final RuleMapper ruleMapper;

    @Override
    public List<Rule> findAll() {
        return jdbc.query(
                FIND_ALL_QUERY,
                ruleMapper
        );
    }

    @Override
    public List<Rule> findAllByAccidentId(int accidentId) {
        return jdbc.query(
                FIND_ALL_BY_ACCIDENT_ID_QUERY,
                ruleMapper,
                accidentId
        );
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject(
                        FIND_BY_ID_QUERY,
                        ruleMapper,
                        id
                )
        );
    }

    @Override
    public Optional<Rule> add(Rule rule) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement statement = con.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, rule.getName());
            return statement;
        }, holder);
        rule.setId((Integer) holder.getKeys().get("id"));
        return Optional.of(rule);
    }

    @Override
    public boolean update(Rule rule) {
        return jdbc.update(
                UPDATE_QUERY,
                rule.getName(),
                rule.getId()
        ) > 0;
    }

    @Override
    public boolean delete(Rule rule) {
        return jdbc.update(
                DELETE_QUERY,
                rule.getId()
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