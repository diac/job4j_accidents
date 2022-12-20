package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {

    List<Accident> findAll();

    Optional<Accident> findById(int id);

    Optional<Accident> add(Accident accident);

    boolean update(Accident accident);

    boolean delete(Accident accident);

    boolean delete(int id);
}