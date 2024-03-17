package com.ids.app.persistence.crud;

import com.ids.app.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserCrudRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String email);
    @Query(value = "SELECT p FROM User p")
    Optional<List<User>> getByColumns();
}
