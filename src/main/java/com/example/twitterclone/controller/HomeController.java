package com.example.twitterclone.controller;

import com.example.twitterclone.entity.Message;
import com.example.twitterclone.entity.User;
import com.example.twitterclone.registration.RegistrationRequest;
import com.example.twitterclone.security.CustomUserDetails;
import com.example.twitterclone.service.IMessageService;
import com.example.twitterclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final IMessageService messageService;
    private final UserService userService;

    private String uploadPath;

    @GetMapping
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/greeting")
    public String main(Model model) {
        model.addAttribute("messages", messageService.getAllMessages());
        return "main";
    }

    @PostMapping("/greeting")
    public String add(
                      Principal principal,
                      @RequestParam String text,
                      @RequestParam String tag,
                      @RequestParam("file") MultipartFile file,
                      Model model) throws IOException {

        User user = userService.getUserByPrincipal(principal);
        Message message = new Message(text, tag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {




            message.setFilename(file);
        }


        messageService.save(message);
        model.addAttribute("messages", messageService.getAllMessages());
        return "redirect:/greeting";
    }


    @PostMapping("/greeting/filter")
    public String filter(@RequestParam String tagFilter,
                      Model model) {
        if (tagFilter != null && !tagFilter.isEmpty()) {
            model.addAttribute("messages", messageService.findByTag(tagFilter));
        } else {
            model.addAttribute("messages", messageService.getAllMessages());
        }
        return "main";
    }

}
