package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleHibernateRepository implements RuleDefaultRepository {

    private static final String FIND_ALL_QUERY = "SELECT r FROM Rule r";

    private static final String FIND_BY_ID_QUERY = "SELECT r FROM Rule r WHERE id = :fId";

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
                    accident_id = :fId
                );
            """;

    private static final String DELETE_QUERY = "DELETE FROM Rule WHERE id = :fId";

    private final CrudRepository crudRepository;

    @Override
    public List<Rule> findAll() {
        return crudRepository.query(FIND_ALL_QUERY, Rule.class);
    }

    @Override
    public List<Rule> findAllByAccidentId(int accidentId) {
        return crudRepository.query(
                FIND_ALL_BY_ACCIDENT_ID_QUERY,
                Rule.class,
                Map.of("fId", accidentId)
        );
    }

    @Override
    public Optional<Rule> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_QUERY,
                Rule.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<Rule> add(Rule rule) {
        return crudRepository.optional(session -> {
            session.persist(rule);
            return rule;
        });
    }

    @Override
    public boolean update(Rule rule) {
        return crudRepository.execute(session -> {
            Rule merged = (Rule) session.merge(session);
            return rule.equals(merged);
        });
    }

    @Override
    public boolean delete(Rule rule) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", rule.getId()));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.execute(DELETE_QUERY, Map.of("fId", id));
    }
}