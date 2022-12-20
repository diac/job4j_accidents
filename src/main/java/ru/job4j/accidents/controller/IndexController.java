package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
public class IndexController {

    private AccidentService accidentService;

    @GetMapping("/")
    public String index(Model model) {
        for (int i = 1; i <= 5; i++) {
            String name = "Нарушение №" + i;
            String address = "Адрес №" + i;
            accidentService.add(new Accident(0, name, name, address));
        }
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}