package learn.spring.annotationfield.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student1 implements Serializable, BeanNameAware, ApplicationContextAware {


    private int id;
    private String name;

    private String beanName;
    private ApplicationContext applicationContext;

    public Student1(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void init(){
        System.out.println("hello...........");
    }
    
    public static Student1 create(){
        return new Student1(102,"KK102",null, null);
    }

    public void print() {
        System.out.println(this.beanName);
        System.out.println("   context.getBeanDefinitionNames() ===>> "
                + String.join(",", applicationContext.getBeanDefinitionNames()));

    }


}
