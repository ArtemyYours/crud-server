package ru.balancetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.balancetracker.model.jpa.Icon;

@Repository
public interface IconRepository extends JpaRepository<Icon, Long> {

}
