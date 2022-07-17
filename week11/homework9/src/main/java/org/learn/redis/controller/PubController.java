package org.learn.redis.controller;

import org.learn.redis.pubsub.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PubController {
    @Autowired
    private MessagePublisher messagePublisher;

    @GetMapping("/send")
    public String sendMessage(String message){
        messagePublisher.publish(message);
        return "success";
    }
}
