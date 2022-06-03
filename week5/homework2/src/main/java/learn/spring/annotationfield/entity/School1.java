package learn.spring.annotationfield.entity;

import learn.spring.service.ISchool;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Data
public class School1 implements ISchool {

    @Autowired
    Klass1 class1;

    @Autowired
    @Qualifier("student1")
    Student1 student100;

    @Override
    public void ding(){

        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);

    }
    
}
