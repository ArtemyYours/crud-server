package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.balanceTracker.model.jpa.Icon;

@Repository
public interface IconRepository extends JpaRepository<Icon, Long> {

}
