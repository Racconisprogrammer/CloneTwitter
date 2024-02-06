package com.example.twitterclone.repository;

import com.example.twitterclone.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByTag(String tag);
}
