package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RuleMemRepository implements RuleDefaultRepository {

    private final AtomicInteger autoIncrement;
    private final Map<Integer, Rule> store = new ConcurrentHashMap<>();

    public RuleMemRepository() {
        autoIncrement = new AtomicInteger();
        add(new Rule(1, "Статья. 1"));
        add(new Rule(2, "Статья. 2"));
        add(new Rule(3, "Статья. 3"));
    }

    @Override
    public List<Rule> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public List<Rule> findAllByAccidentId(int accidentId) {
        return null;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Rule> add(Rule rule) {
        rule.setId(autoIncrement.incrementAndGet());
        Rule ruleInStore = store.putIfAbsent(rule.getId(), rule);
        return ruleInStore == null ? Optional.of(rule) : Optional.empty();
    }

    @Override
    public boolean update(Rule rule) {
        Rule ruleInStore = store.replace(rule.getId(), rule);
        return rule.equals(ruleInStore);
    }

    @Override
    public boolean delete(Rule rule) {
        Rule ruleInStore = store.remove(rule.getId());
        return rule.equals(ruleInStore);
    }

    @Override
    public boolean delete(int id) {
        Rule ruleInStore = store.remove(id);
        return ruleInStore != null;
    }
}