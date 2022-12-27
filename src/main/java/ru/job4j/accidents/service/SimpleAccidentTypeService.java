package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public List<AccidentType> findAll() {
        List<AccidentType> accidentTypes = new ArrayList<>();
        accidentTypeRepository.findAll()
                .forEach(accidentTypes::add);
        return accidentTypes;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public Optional<AccidentType> add(AccidentType accidentType) {
        return Optional.of(accidentTypeRepository.save(accidentType));
    }

    @Override
    public boolean update(AccidentType accidentType) {
        AccidentType saved = accidentTypeRepository.save(accidentType);
        return accidentType.equals(saved);
    }

    @Override
    public boolean delete(AccidentType accidentType) {
        accidentTypeRepository.delete(accidentType);
        return !accidentTypeRepository.existsById(accidentType.getId());
    }

    @Override
    public boolean delete(int id) {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(id);
        accidentTypeRepository.delete(accidentType);
        return !accidentTypeRepository.existsById(id);
    }
}