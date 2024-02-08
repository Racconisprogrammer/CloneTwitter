package com.example.twitterclone.repository;

import com.example.twitterclone.entity.Role;
import com.example.twitterclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "update User u set u.firstName = :firstName," +
            " u.lastName = :lastName," + "u.email =:email," + "u.roles =:roles where u.id =:id")
    void update(String firstName, String lastName, String email, Long id, Set<Role> roles);

}
