package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeHibernateRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

    private AccidentTypeHibernateRepository accidentTypeHibernateRepository;

    @Override
    public List<AccidentType> findAll() {
        return accidentTypeHibernateRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeHibernateRepository.findById(id);
    }

    @Override
    public Optional<AccidentType> add(AccidentType accidentType) {
        return accidentTypeHibernateRepository.add(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType) {
        return accidentTypeHibernateRepository.update(accidentType);
    }

    @Override
    public boolean delete(AccidentType accidentType) {
        return accidentTypeHibernateRepository.delete(accidentType);
    }

    @Override
    public boolean delete(int id) {
        return accidentTypeHibernateRepository.delete(id);
    }
}