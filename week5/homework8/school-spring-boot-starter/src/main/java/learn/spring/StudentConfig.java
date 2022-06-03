package learn.spring;

import learn.spring.entity.Student3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * 根据配置属性创建bean,如果无配置按默认值创建
 */
@Configuration
@ConditionalOnProperty(prefix = "learn.spring.school",name = "enable",
        havingValue = "true",matchIfMissing = true)
@EnableConfigurationProperties(SchoolProperties.class)
@ComponentScan("learn.spring")
public class StudentConfig {
    @Autowired
    private SchoolProperties props;

    @Bean
    public Student3 student2(){
        if(Objects.isNull(props.getStudent2())){
            return new Student3(2,"two");
        } else {
            return props.getStudent2();
        }
    }

    @Bean
    public Student3 student1(){
        if (Objects.isNull(props.getStudent1())) {
            return new Student3(1,"one");
        } else {
            return props.getStudent1();
        }
    }
}
