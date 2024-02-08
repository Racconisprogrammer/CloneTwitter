package com.example.twitterclone.service;


import com.example.twitterclone.entity.Role;
import com.example.twitterclone.entity.User;
import com.example.twitterclone.registration.RegistrationRequest;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IUserService {
    List<User> getAllUsers();

    User registrationUser(RegistrationRequest registrationRequest);

    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    User getUserByPrincipal(Principal principal);

    void update(User user, Map<String, String> form);
}
