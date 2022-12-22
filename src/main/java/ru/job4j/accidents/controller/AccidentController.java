package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AccidentController {

    private AccidentService accidentService;
    private AccidentTypeService accidentTypeService;
    private RuleService ruleService;

    @GetMapping("/accidents")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }

    @GetMapping("/addAccident")
    public String create(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String store(
            @ModelAttribute Accident accident,
            @RequestParam("type.id") int typeId,
            HttpServletRequest req
    ) {
        accident.setType(new AccidentType(typeId, null));
        String[] ruleIds = Optional.ofNullable(req.getParameterValues("rIds")).orElse(new String[0]);
        accident.setRules(
                Arrays.stream(ruleIds)
                        .map(id -> new Rule(Integer.parseInt(id), null))
                        .collect(Collectors.toSet())
        );
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
        model.addAttribute(
                "ruleIds",
                accident.get().getRules().stream()
                        .map(rule -> rule.getId())
                        .toList()
        );
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "editAccident";
    }

    @PatchMapping("/accidents/{id}")
    public String patch(
            @PathVariable("id") int id,
            @ModelAttribute("accident") Accident accident,
            HttpServletRequest req
    ) {
        accident.setId(id);
        String[] ruleIds = Optional.ofNullable(req.getParameterValues("rIds")).orElse(new String[0]);
        accident.setRules(
                Arrays.stream(ruleIds)
                        .map(ruleId -> new Rule(Integer.parseInt(ruleId), null))
                        .collect(Collectors.toSet())
        );
        accidentService.update(accident);
        return "redirect:/accidents";
    }
}