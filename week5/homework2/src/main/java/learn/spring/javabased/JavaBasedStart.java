package learn.spring.javabased;

import learn.spring.javabased.config.StudentConfig;
import learn.spring.javabased.entity.Klass3;
import learn.spring.javabased.entity.School3;
import learn.spring.javabased.entity.Student3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用xml开启组件扫描，使用注解定义bean和注入依赖
 */
public class JavaBasedStart {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(StudentConfig.class);
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
