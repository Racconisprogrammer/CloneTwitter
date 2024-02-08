package com.example.twitterclone.service;


import com.example.twitterclone.entity.Message;
import com.example.twitterclone.entity.User;
import com.example.twitterclone.registration.RegistrationRequest;
import com.example.twitterclone.repository.IMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {

    private final IMessageRepository messageRepository;


    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findByTag(String tag) {
        return messageRepository.findByTag(tag);
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}
