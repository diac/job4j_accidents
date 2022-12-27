package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

public interface AccidentTypeDefaultRepository {

    List<AccidentType> findAll();

    Optional<AccidentType> findById(int id);

    Optional<AccidentType> add(AccidentType accidentType);

    boolean update(AccidentType accidentType);

    boolean delete(AccidentType accidentType);

    boolean delete(int id);
}