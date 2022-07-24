package learn.mq.activemq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(final String topic, Map<String,String> message) {
        jmsTemplate.convertAndSend(topic, message);
    }
}
