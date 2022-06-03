package learn.spring.componentscan.config;

import learn.spring.componentscan.entity.Student2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    @Bean
    public Student2 student1(){
        return new Student2(1,"one");
    }

    @Bean
    public Student2 student2(){
        return new Student2(2,"two");
    }
}
