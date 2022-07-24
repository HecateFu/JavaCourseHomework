package learn.mq.activemq.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Consumer {
    @JmsListener(destination = "mqMsg")
    public void receiveMessage(Map<String,String> message) {
        System.out.println(message.toString());
    }
}
