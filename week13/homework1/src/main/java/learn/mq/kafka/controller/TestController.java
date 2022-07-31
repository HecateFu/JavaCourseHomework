package learn.mq.kafka.controller;

import learn.mq.kafka.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private Producer producer;

    @GetMapping("test")
    public String test(String msg){
        producer.sendMessage(msg);
        return "finish";
    }
}
