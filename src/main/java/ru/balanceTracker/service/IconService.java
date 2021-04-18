package ru.balanceTracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balanceTracker.model.jpa.Icon;
import ru.balanceTracker.repository.IconRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IconService {
    private IconRepository repository;

    public List<Icon> getAll(){
        return repository.findAll();
    }

}
