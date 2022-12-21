package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

    private AccidentTypeRepository accidentTypeRepository;

    @Override
    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public Optional<AccidentType> add(AccidentType accidentType) {
        return accidentTypeRepository.add(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType) {
        return accidentTypeRepository.update(accidentType);
    }

    @Override
    public boolean delete(AccidentType accidentType) {
        return accidentTypeRepository.delete(accidentType);
    }

    @Override
    public boolean delete(int id) {
        return accidentTypeRepository.delete(id);
    }
}