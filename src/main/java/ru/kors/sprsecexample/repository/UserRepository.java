package ru.kors.sprsecexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kors.sprsecexample.model.MyUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByName(String username);
}
