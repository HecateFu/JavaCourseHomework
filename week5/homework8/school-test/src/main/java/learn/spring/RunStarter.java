package learn.spring;

import learn.spring.entity.Klass3;
import learn.spring.entity.School3;
import learn.spring.entity.Student3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 测试自定义 starter
 */
@SpringBootApplication
public class RunStarter {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RunStarter.class,args);
        School3 school = context.getBean("school", School3.class);
        System.out.println(school);
        school.ding();

        System.out.println("------------------------------------");
        Klass3 class1 = context.getBean("class1", Klass3.class);
        System.out.println(class1);
        class1.dong();

        System.out.println("------------------------------------");
        Student3 student1 = context.getBean("student1", Student3.class);
        student1.print();
    }
}
