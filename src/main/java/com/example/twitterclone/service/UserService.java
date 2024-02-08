package com.example.twitterclone.service;


import com.example.twitterclone.entity.Role;
import com.example.twitterclone.entity.User;
import com.example.twitterclone.registration.RegistrationRequest;
import com.example.twitterclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registrationUser(RegistrationRequest registrationRequest) {
        User user = new User(
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                Role.ROLE_USER
        );
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName()).
                orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void update(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (Map.Entry<String, String> value : form.entrySet()) {
            System.out.println(value.getKey());
            if (value.getKey().equals("firstName")) user.setFirstName(value.getValue());
            if (value.getKey().equals("lastName")) user.setLastName(value.getValue());

        }
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }

        }
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
