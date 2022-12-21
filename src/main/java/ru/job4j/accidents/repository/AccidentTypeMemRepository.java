package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMemRepository implements AccidentTypeRepository {

    private final AtomicInteger autoIncrement;
    private final Map<Integer, AccidentType> store = new ConcurrentHashMap<>();

    public AccidentTypeMemRepository() {
        this.autoIncrement = new AtomicInteger();
        add(new AccidentType(1, "Две машины"));
        add(new AccidentType(2, "Машина и человек"));
        add(new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public List<AccidentType> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<AccidentType> add(AccidentType accidentType) {
        accidentType.setId(autoIncrement.incrementAndGet());
        AccidentType accidentTypeInStore = store.putIfAbsent(accidentType.getId(), accidentType);
        return accidentTypeInStore == null ? Optional.of(accidentType) : Optional.empty();
    }

    @Override
    public boolean update(AccidentType accidentType) {
        AccidentType accidentTypeInStore = store.replace(accidentType.getId(), accidentType);
        return accidentType.equals(accidentTypeInStore);
    }

    @Override
    public boolean delete(AccidentType accidentType) {
        AccidentType accidentTypeInStore = store.remove(accidentType.getId());
        return accidentType.equals(accidentTypeInStore);
    }

    @Override
    public boolean delete(int id) {
        AccidentType accidentType = store.remove(id);
        return accidentType != null;
    }
}