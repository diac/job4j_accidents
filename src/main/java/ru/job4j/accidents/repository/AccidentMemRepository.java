package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMemRepository implements AccidentRepository {

    private final AtomicInteger autoIncrement;
    private final Map<Integer, Accident> store = new ConcurrentHashMap<>();

    public AccidentMemRepository() {
        this.autoIncrement = new AtomicInteger();
        for (int i = 1; i <= 5; i++) {
            String name = "Нарушение №" + i;
            String address = "Адрес №" + i;
            add(new Accident(0, name, name, address, new AccidentType(), new HashSet<>()));
        }
    }

    @Override
    public List<Accident> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Accident> add(Accident accident) {
        accident.setId(autoIncrement.incrementAndGet());
        Accident accidentInStore = store.putIfAbsent(accident.getId(), accident);
        return accidentInStore == null ? Optional.of(accident) : Optional.empty();
    }

    @Override
    public boolean update(Accident accident) {
        Accident accidentInStore = store.replace(accident.getId(), accident);
        return accidentInStore.equals(accident);
    }

    @Override
    public boolean delete(Accident accident) {
        Accident accidentInStore = store.remove(accident.getId());
        return accidentInStore.equals(accident);
    }

    @Override
    public boolean delete(int id) {
        Accident accidentInStore = store.remove(id);
        return accidentInStore != null;
    }
}