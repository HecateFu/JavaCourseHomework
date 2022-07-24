package learn.mq.activemq;

import learn.mq.activemq.service.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ActivemqAppStart {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ActivemqAppStart.class, args);
    }
}
