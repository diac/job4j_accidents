package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {

    private RuleRepository ruleRepository;

    @Override
    public List<Rule> findAll() {
        List<Rule> rules = new ArrayList<>();
        ruleRepository.findAll()
                .forEach(rules::add);
        return rules;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    @Override
    public Optional<Rule> add(Rule rule) {
        return Optional.of(ruleRepository.save(rule));
    }

    @Override
    public boolean update(Rule rule) {
        Rule saved = ruleRepository.save(rule);
        return rule.equals(saved);
    }

    @Override
    public boolean delete(Rule rule) {
        ruleRepository.delete(rule);
        return !ruleRepository.existsById(rule.getId());
    }

    @Override
    public boolean delete(int id) {
        Rule rule = new Rule();
        rule.setId(id);
        ruleRepository.delete(rule);
        return !ruleRepository.existsById(id);
    }
}