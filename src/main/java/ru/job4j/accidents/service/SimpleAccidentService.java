package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;

    @Override
    public List<Accident> findAll() {
        List<Accident> accidents = new ArrayList<>();
        accidentRepository.findAll()
                .forEach(accidents::add);
        return accidents;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        return Optional.of(accidentRepository.save(accident));
    }

    @Override
    public boolean update(Accident accident) {
        Accident saved = accidentRepository.save(accident);
        return accident.equals(saved);
    }

    @Override
    public boolean delete(Accident accident) {
        accidentRepository.delete(accident);
        return !accidentRepository.existsById(accident.getId());
    }

    @Override
    public boolean delete(int id) {
        Accident accident = new Accident();
        accident.setId(id);
        accidentRepository.delete(accident);
        return !accidentRepository.existsById(id);
    }
}