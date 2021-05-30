package ru.balancetracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.balancetracker.model.jpa.Icon;
import ru.balancetracker.security.SecurityConstants;
import ru.balancetracker.service.IconService;

import java.util.List;

@RestController
@RequestMapping("/icon")
@AllArgsConstructor
public class IconController {
    private IconService service;

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/get-all")
    public List<Icon> getAll(){
        return service.getAll();
    }

}
