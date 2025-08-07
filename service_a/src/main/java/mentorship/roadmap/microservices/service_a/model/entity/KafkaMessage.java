package mentorship.roadmap.microservices.service_a.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "kafka")
public class KafkaMessage {
    String key;
    String type;
    String content;

}
