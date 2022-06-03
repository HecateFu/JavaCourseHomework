package learn.spring.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Data
@Component("school")
public class School3 {

    @Autowired
    Klass3 class1;

    @Autowired
    @Qualifier("student1")
    Student3 student100;

    public void ding(){

        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);

    }
    
}
