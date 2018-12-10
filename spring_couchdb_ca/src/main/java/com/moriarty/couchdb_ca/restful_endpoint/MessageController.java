package com.moriarty.couchdb_ca.restful_endpoint;

import com.moriarty.couchdb_ca.db_repository.MessageRepository;
import com.moriarty.couchdb_ca.entity.Message;
import com.moriarty.couchdb_ca.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("")
    public List<Message> getAllPosts() {
        return messageRepository.getAll();
    }

    @PostMapping("")
    public Message addPost(@RequestBody Message message) {
        messageRepository.add(message);
        return message;
    }

}
