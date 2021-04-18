package ru.balanceTracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.balanceTracker.model.jpa.Icon;
import ru.balanceTracker.service.IconService;

import java.util.List;

@RestController
@RequestMapping("/icon")
@AllArgsConstructor
public class IconController {
    private IconService service;

    @GetMapping("/get-all")
    public List<Icon> getAll(){
        return service.getAll();
    }

}
