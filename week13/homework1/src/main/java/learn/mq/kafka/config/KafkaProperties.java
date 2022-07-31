package learn.mq.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String bootstrapAddress;
    private String topic;
    private String consumerGroupId;
}
