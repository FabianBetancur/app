package com.ids.app.persistence.crud;

import com.ids.app.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserCrudRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String email);
}
