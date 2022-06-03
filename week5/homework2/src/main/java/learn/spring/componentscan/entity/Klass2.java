package learn.spring.componentscan.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component("class1")
public class Klass2 {
    @Autowired
    List<Student2> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
