package learn.spring;

import learn.spring.entity.Student3;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 允许配置的属性值
 */
@Getter
@Setter
@ConfigurationProperties("learn.spring.school")
public class SchoolProperties {
    private boolean enable;
    private Student3 student1;
    private Student3 student2;
}
