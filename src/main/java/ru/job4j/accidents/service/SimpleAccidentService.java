package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentJdbcTemplate accidentJdbcTemplate;

    @Override
    public List<Accident> findAll() {
        return accidentJdbcTemplate.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentJdbcTemplate.findById(id);
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        return accidentJdbcTemplate.add(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentJdbcTemplate.update(accident);
    }

    @Override
    public boolean delete(Accident accident) {
        return accidentJdbcTemplate.delete(accident);
    }

    @Override
    public boolean delete(int id) {
        return accidentJdbcTemplate.delete(id);
    }
}