package ru.msc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.msc.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

     Optional<User> findUserByEmail(String email);

     boolean existsByEmail(String email);

     boolean existsByLogin(String login);
}
