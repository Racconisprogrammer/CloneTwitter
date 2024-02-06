package com.example.twitterclone.controller;

import com.example.twitterclone.entity.Message;
import com.example.twitterclone.repository.IMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final IMessageRepository messageRepository;

    @GetMapping
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World")
                           String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/greeting")
    public String main(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("/greeting")
    public String add(@RequestParam String text,
                      @RequestParam String tag,
                      Model model) {
        Message message = new Message(text, tag);
        model.addAttribute("messages", messageRepository.findAll());
        return "redirect:/";
    }
    @PostMapping("/greeting/filter")
    public String filter(@RequestParam String tagFilter,
                      Model model) {
        if (tagFilter != null && !tagFilter.isEmpty()) {
            model.addAttribute("messages", messageRepository.findByTag(tagFilter));
        } else {
            model.addAttribute("messages", messageRepository.findAll());
        }
        return "main";
    }

}
