package ru.balancetracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balancetracker.model.jpa.Icon;
import ru.balancetracker.repository.IconRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IconService {
    private IconRepository repository;

    public List<Icon> getAll(){
        return repository.findAll();
    }

}
