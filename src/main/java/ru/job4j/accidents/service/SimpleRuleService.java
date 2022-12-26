package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleHibernateRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {

    private RuleHibernateRepository ruleHibernateRepository;

    @Override
    public List<Rule> findAll() {
        return ruleHibernateRepository.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleHibernateRepository.findById(id);
    }

    @Override
    public Optional<Rule> add(Rule rule) {
        return ruleHibernateRepository.add(rule);
    }

    @Override
    public boolean update(Rule rule) {
        return ruleHibernateRepository.update(rule);
    }

    @Override
    public boolean delete(Rule rule) {
        return ruleHibernateRepository.delete(rule);
    }

    @Override
    public boolean delete(int id) {
        return ruleHibernateRepository.delete(id);
    }
}