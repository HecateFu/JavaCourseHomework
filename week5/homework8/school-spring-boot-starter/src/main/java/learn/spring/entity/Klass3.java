package learn.spring.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component("class1")
public class Klass3 {
    @Autowired
    List<Student3> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
