package ru.balancetracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balancetracker.model.jpa.Icon;
import ru.balancetracker.security.SecurityConstants;
import ru.balancetracker.service.IconService;

import java.util.List;

@RestController
@RequestMapping("/icon")
@AllArgsConstructor
public class IconController {
    private final IconService service;

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/get-all")
    public List<Icon> getAll() {
        return service.getAll();
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/upload-new")
    public Long uploadNew(@RequestBody String link) {
        return service.uploadNew(link);
    }

}
