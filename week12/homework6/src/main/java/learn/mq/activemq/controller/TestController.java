package learn.mq.activemq.controller;

import learn.mq.activemq.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private Producer producer;

    @GetMapping("/msg")
    public String sendMsg(String content){
        Map<String,String> map = new HashMap<>();
        map.put("msg",content);
        producer.sendMessage("mqMsg",map);
        return "success";
    }
}
