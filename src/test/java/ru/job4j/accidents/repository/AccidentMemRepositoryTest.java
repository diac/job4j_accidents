package ru.job4j.accidents.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        AccidentMemRepository.class,
        AtomicInteger.class
})
public class AccidentMemRepositoryTest {

    @Autowired
    private AccidentDefaultRepository accidentDefaultRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        Accident accident = new Accident(0, value, value, value, new AccidentType(0, value), new HashSet<>());
        accidentDefaultRepository.add(accident);
        Accident accidentInRepository = accidentDefaultRepository.findById(accident.getId())
                .orElse(new Accident());
        assertThat(accidentInRepository).isEqualTo(accident);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        Accident accident = new Accident(0, value, value, value, new AccidentType(0, value), new HashSet<>());
        accidentDefaultRepository.add(accident);
        List<Accident> accidents = accidentDefaultRepository.findAll();
        assertThat(accidents.contains(accident)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        Accident accident = new Accident(0, value, value, value, new AccidentType(0, value), new HashSet<>());
        accidentDefaultRepository.add(accident);
        accident.setName(accident.getName() + "_updated");
        boolean success = accidentDefaultRepository.update(accident);
        Accident accidentInRepository = accidentDefaultRepository.findById(accident.getId())
                .orElse(new Accident());
        assertThat(success).isTrue();
        assertThat(accidentInRepository).isEqualTo(accident);
        assertThat(accidentInRepository.getName()).isEqualTo(accident.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        Accident accident = new Accident(0, value, value, value, new AccidentType(0, value), new HashSet<>());
        accidentDefaultRepository.add(accident);
        int accidentId = accident.getId();
        boolean success = accidentDefaultRepository.delete(accident);
        Optional<Accident> accidentInRepository = accidentDefaultRepository.findById(accidentId);
        assertThat(success).isTrue();
        assertThat(accidentInRepository).isEmpty();
    }

    @Test
    public void whenById() {
        String value = String.valueOf(System.currentTimeMillis());
        Accident accident = new Accident(0, value, value, value, new AccidentType(0, value), new HashSet<>());
        accidentDefaultRepository.add(accident);
        int accidentId = accident.getId();
        boolean success = accidentDefaultRepository.delete(accidentId);
        Optional<Accident> accidentInRepository = accidentDefaultRepository.findById(accidentId);
        assertThat(success).isTrue();
        assertThat(accidentInRepository).isEmpty();
    }
}