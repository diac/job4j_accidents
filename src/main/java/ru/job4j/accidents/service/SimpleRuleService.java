package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {

    private RuleRepository ruleRepository;

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    @Override
    public Optional<Rule> add(Rule rule) {
        return ruleRepository.add(rule);
    }

    @Override
    public boolean update(Rule rule) {
        return ruleRepository.update(rule);
    }

    @Override
    public boolean delete(Rule rule) {
        return ruleRepository.delete(rule);
    }

    @Override
    public boolean delete(int id) {
        return ruleRepository.delete(id);
    }
}