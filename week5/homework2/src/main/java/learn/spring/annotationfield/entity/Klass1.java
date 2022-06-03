package learn.spring.annotationfield.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class Klass1 {
    @Autowired
    List<Student1> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
