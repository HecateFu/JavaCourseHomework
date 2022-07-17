package org.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Counter {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void init(){
        redisTemplate.opsForValue().setIfAbsent("counter",20);
    }

    public boolean decrease(){
        Long count = redisTemplate.opsForValue().decrement("counter");
        if(count==null){
            System.out.println("扣减失败");
            return false;
        }
        if(count<0){
            System.out.println("余额不足");
            return false;
        }
        System.out.println("扣减成功，余额"+count);
        return false;
    }
}
