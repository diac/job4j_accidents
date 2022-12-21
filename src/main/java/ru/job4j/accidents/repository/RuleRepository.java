package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

public interface RuleRepository {

    List<Rule> findAll();

    Optional<Rule> findById(int id);

    Optional<Rule> add(Rule rule);

    boolean update(Rule rule);

    boolean delete(Rule rule);

    boolean delete(int id);
}