package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balanceTracker.model.Icon;

public interface IconRepository extends JpaRepository<Icon, Long> {

}
