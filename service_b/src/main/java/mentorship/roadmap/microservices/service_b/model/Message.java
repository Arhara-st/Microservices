package mentorship.roadmap.microservices.service_b.model;

import lombok.Data;

@Data
public class Message {
    private String key;
    private String type;
    private String content;
}
