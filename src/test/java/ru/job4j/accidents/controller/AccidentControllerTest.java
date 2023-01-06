package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @MockBean
    private AccidentTypeService accidentTypeService;

    @Test
    @WithMockUser
    public void whenIndexView() throws Exception {
        mockMvc.perform(get("/accidents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser
    public void whenAddAccidentView() throws Exception {
        mockMvc.perform(get("/addAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void whenEditAccidentView() throws Exception {
        String value = String.valueOf(System.currentTimeMillis());
        AccidentType accidentType = accidentTypeService.add(new AccidentType(0, value))
                .orElse(new AccidentType());
        Accident accident = new Accident(0, value, value, value, accidentType, new HashSet<>());
        when(accidentService.findById(0)).thenReturn(Optional.of(accident));
        mockMvc.perform(
                        get("/accidents/edit")
                                .param("id", "0")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"));
    }

    @Test
    @WithMockUser
    public void whenStoreAccidentThenRedirect() throws Exception {
        String value = String.valueOf(System.currentTimeMillis());
        AccidentType accidentType = accidentTypeService.add(new AccidentType(0, value))
                .orElse(new AccidentType());
        Accident accident = new Accident(0, value, value, value, accidentType, new HashSet<>());
        mockMvc.perform(
                        post("/saveAccident")
                                .param("type.id", String.valueOf(accidentType.getId()))
                                .flashAttr("accident", accident)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).add(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(accident.getName());
    }

    @Test
    @WithMockUser
    public void whenPatchAccidentThenRedirect() throws Exception {
        String value = String.valueOf(System.currentTimeMillis());
        AccidentType accidentType = accidentTypeService.add(new AccidentType(0, value))
                .orElse(new AccidentType());
        Accident accident = new Accident(0, value, value, value, accidentType, new HashSet<>());
        mockMvc.perform(
                patch("/accidents/{id}", 0)
                        .flashAttr("accident", accident)
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(accident.getName());
    }
}