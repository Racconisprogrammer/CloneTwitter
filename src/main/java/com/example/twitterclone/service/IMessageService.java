package com.example.twitterclone.service;


import com.example.twitterclone.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;



public interface IMessageService {

    List<Message> getAllMessages();

    List<Message> findByTag(String tag);

    void save(Message message);

}
