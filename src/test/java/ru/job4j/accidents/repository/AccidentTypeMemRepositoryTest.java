package ru.job4j.accidents.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {
        AccidentTypeMemRepository.class,
        AtomicInteger.class
})
public class AccidentTypeMemRepositoryTest {

    @Autowired
    private AccidentTypeDefaultRepository accidentTypeDefaultRepository;

    @Test
    public void whenCreate() {
        String value = String.valueOf(System.currentTimeMillis());
        AccidentType accidentType = new AccidentType(0, value);
        accidentTypeDefaultRepository.add(accidentType);
        AccidentType accidentTypeInRepository = accidentTypeDefaultRepository.findById(accidentType.getId())
                .orElse(new AccidentType());
        assertThat(accidentTypeInRepository).isEqualTo(accidentType);
    }

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        AccidentType accidentType = new AccidentType(0, value);
        accidentTypeDefaultRepository.add(accidentType);
        List<AccidentType> accidentTypes = accidentTypeDefaultRepository.findAll();
        assertThat(accidentTypes.contains(accidentType)).isTrue();
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        AccidentType accidentType = new AccidentType(0, value);
        accidentTypeDefaultRepository.add(accidentType);
        accidentType.setName(accidentType.getName() + "_updated");
        boolean success = accidentTypeDefaultRepository.update(accidentType);
        AccidentType accidentTypeInRepository = accidentTypeDefaultRepository.findById(accidentType.getId())
                .orElse(new AccidentType());
        assertThat(success).isTrue();
        assertThat(accidentTypeInRepository).isEqualTo(accidentType);
        assertThat(accidentTypeInRepository.getName()).isEqualTo(accidentType.getName());
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        AccidentType accidentType = new AccidentType(0, value);
        accidentTypeDefaultRepository.add(accidentType);
        int accidentTypeId = accidentType.getId();
        boolean success = accidentTypeDefaultRepository.delete(accidentType);
        Optional<AccidentType> accidentTypeInRepository = accidentTypeDefaultRepository.findById(accidentTypeId);
        assertThat(success).isTrue();
        assertThat(accidentTypeInRepository).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        String value = String.valueOf(System.currentTimeMillis());
        AccidentType accidentType = new AccidentType(0, value);
        accidentTypeDefaultRepository.add(accidentType);
        int accidentTypeId = accidentType.getId();
        boolean success = accidentTypeDefaultRepository.delete(accidentTypeId);
        Optional<AccidentType> accidentTypeInRepository = accidentTypeDefaultRepository.findById(accidentTypeId);
        assertThat(success).isTrue();
        assertThat(accidentTypeInRepository).isEmpty();
    }
}