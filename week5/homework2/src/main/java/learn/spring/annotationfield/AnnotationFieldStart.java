package learn.spring.annotationfield;

import learn.spring.annotationfield.entity.Klass1;
import learn.spring.annotationfield.entity.School1;
import learn.spring.annotationfield.entity.Student1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml和注解混合配置，注解进行依赖注入，xml定义bean
 */
public class AnnotationFieldStart {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("annotation-field.xml");
        School1 school = context.getBean("school", School1.class);
        System.out.println(school);
        school.ding();

        System.out.println("------------------------------------");
        Klass1 class1 = context.getBean("class1", Klass1.class);
        System.out.println(class1);
        class1.dong();

        System.out.println("------------------------------------");
        Student1 student1 = context.getBean("student1", Student1.class);
        student1.print();
    }
}
