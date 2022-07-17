package org.learn.redis.controller;

import org.learn.redis.service.Counter;
import org.learn.redis.service.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {
    @Autowired
    private Lock lock;

    @Autowired
    private Counter counter;

    @GetMapping("/lock")
    public void lock(){
        String uuid = UUID.randomUUID().toString();
        lock.getLock(uuid);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            lock.unlock(uuid);
        }
        lock.unlock(uuid);
    }

    @GetMapping("/counter")
    public void counter(){
        counter.decrease();
    }
}
