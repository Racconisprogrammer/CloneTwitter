package com.example.twitterclone.controller;


import com.example.twitterclone.entity.Role;
import com.example.twitterclone.entity.User;
import com.example.twitterclone.registration.RegistrationRequest;
import com.example.twitterclone.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "userList";
    }


    @GetMapping("/{user}")
    public String userEditPage(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/update/{id}")
    public String userUpdate(
            @PathVariable("id") Long userId,
            @RequestParam Map<String, String> form
    ) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userService.update(user, form);
        }
        return "redirect:/users";
    }


    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }


    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("user") RegistrationRequest registrationRequest) {
        userService.registrationUser(registrationRequest);
        return "redirect:/users/login";

    }
}
