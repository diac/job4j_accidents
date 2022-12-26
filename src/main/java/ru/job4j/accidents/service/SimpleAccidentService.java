package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentHibernateRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentHibernateRepository accidentHibernateRepository;

    @Override
    public List<Accident> findAll() {
        return accidentHibernateRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentHibernateRepository.findById(id);
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        return accidentHibernateRepository.add(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentHibernateRepository.update(accident);
    }

    @Override
    public boolean delete(Accident accident) {
        return accidentHibernateRepository.delete(accident);
    }

    @Override
    public boolean delete(int id) {
        return accidentHibernateRepository.delete(id);
    }
}