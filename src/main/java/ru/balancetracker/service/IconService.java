package ru.balancetracker.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.balancetracker.model.jpa.Icon;
import ru.balancetracker.repository.IconRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IconService {
    private final IconRepository repository;

    public List<Icon> getAll(){
        return repository.findAll();
    }

    public Long uploadNew(@NonNull String link){
        Icon icon = new Icon();
        icon.setLink(link);
        return repository.save(icon).getId();
    }

}
