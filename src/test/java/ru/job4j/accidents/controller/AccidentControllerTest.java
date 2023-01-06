package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccidentService accidentService;

    @Autowired
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
        AccidentType accidentType = accidentTypeService.add(new AccidentType(0, "Test"))
                .orElse(new AccidentType());
        Accident accident = accidentService.add(
                        new Accident(0, "Test", "Test", "Test", accidentType, new HashSet<>())
                )
                .orElse(new Accident());
        mockMvc.perform(
                        get("/accidents/edit")
                                .param("id", String.valueOf(accident.getId()))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"));
    }
}