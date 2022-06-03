package learn.spring.javabased.config;

import learn.spring.javabased.entity.Student3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("learn.spring.javabased")
public class StudentConfig {
    @Bean
    public Student3 student1(){
        return new Student3(1,"one");
    }

    @Bean
    public Student3 student2(){
        return new Student3(2,"two");
    }
}
