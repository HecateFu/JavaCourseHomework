package learn.spring.componentscan.entity;

import learn.spring.service.ISchool;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Data
@Component("school")
public class School2 implements ISchool {

    @Autowired
    Klass2 class1;

    @Autowired
    @Qualifier("student1")
    Student2 student100;

    @Override
    public void ding(){

        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);

    }
    
}
