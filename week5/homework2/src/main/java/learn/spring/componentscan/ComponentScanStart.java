package learn.spring.componentscan;

import learn.spring.componentscan.entity.Klass2;
import learn.spring.componentscan.entity.School2;
import learn.spring.componentscan.entity.Student2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用xml开启组件扫描，使用注解定义bean和注入依赖
 */
public class ComponentScanStart {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("component-scan.xml");
        School2 school = context.getBean("school", School2.class);
        System.out.println(school);
        school.ding();

        System.out.println("------------------------------------");
        Klass2 class1 = context.getBean("class1", Klass2.class);
        System.out.println(class1);
        class1.dong();

        System.out.println("------------------------------------");
        Student2 student1 = context.getBean("student1", Student2.class);
        student1.print();
    }
}
