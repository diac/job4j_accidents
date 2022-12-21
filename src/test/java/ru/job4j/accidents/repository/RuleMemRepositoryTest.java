package ru.job4j.accidents.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        RuleMemRepository.class,
        AtomicInteger.class
})
public class RuleMemRepositoryTest {

    @Autowired
    private RuleRepository ruleRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        Rule rule = new Rule(0, value);
        ruleRepository.add(rule);
        Rule ruleInRepository = ruleRepository.findById(rule.getId()).orElse(new Rule());
        assertThat(ruleInRepository).isEqualTo(rule);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Rule rule = new Rule(0, value);
        ruleRepository.add(rule);
        List<Rule> rules = ruleRepository.findAll();
        assertThat(rules.contains(rule)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Rule rule = new Rule(0, value);
        ruleRepository.add(rule);
        rule.setName(rule.getName() + "_update");
        boolean success = ruleRepository.update(rule);
        Rule ruleInRepository = ruleRepository.findById(rule.getId()).orElse(new Rule());
        assertThat(success).isTrue();
        assertThat(ruleInRepository).isEqualTo(rule);
        assertThat(ruleInRepository.getName()).isEqualTo(rule.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Rule rule = new Rule(0, value);
        ruleRepository.add(rule);
        int ruleId = rule.getId();
        boolean success = ruleRepository.delete(rule);
        Optional<Rule> ruleInRepository = ruleRepository.findById(ruleId);
        assertThat(success).isTrue();
        assertThat(ruleInRepository).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        Rule rule = new Rule(0, value);
        ruleRepository.add(rule);
        int ruleId = rule.getId();
        boolean success = ruleRepository.delete(ruleId);
        Optional<Rule> ruleInRepository = ruleRepository.findById(ruleId);
        assertThat(success).isTrue();
        assertThat(ruleInRepository).isEmpty();
    }
}