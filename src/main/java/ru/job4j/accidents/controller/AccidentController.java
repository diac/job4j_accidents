package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {

    private AccidentService accidentService;

    @GetMapping("/accidents")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }

    @GetMapping("/addAccident")
    public String create(Model model) {
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String store(@ModelAttribute Accident accident) {
        accidentService.add(accident);
        return "redirect:/accidents";
    }

    @GetMapping("/accidents/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        Optional<Accident> accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            return "redirect:/accidents";
        }
        model.addAttribute("accident", accident.get());
        return "editAccident";
    }

    @PatchMapping("/accidents/{id}")
    public String patch(@PathVariable("id") int id, @ModelAttribute("accident") Accident accident) {
        accident.setId(id);
        accidentService.update(accident);
        return "redirect:/accidents";
    }
}