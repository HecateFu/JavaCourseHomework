package learn.spring.onlyxml;

import learn.spring.onlyxml.entity.Klass;
import learn.spring.onlyxml.entity.School;
import learn.spring.onlyxml.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 仅使用xml配置
 */
public class XmlOnlyStart {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("xml-only.xml");
        School school = context.getBean("school", School.class);
        System.out.println(school);
        school.ding();

        System.out.println("------------------------------------");
        Klass class1 = context.getBean("class1",Klass.class);
        System.out.println(class1);
        class1.dong();

        System.out.println("------------------------------------");
        Student student1 = context.getBean("student1",Student.class);
        student1.print();
    }
}
