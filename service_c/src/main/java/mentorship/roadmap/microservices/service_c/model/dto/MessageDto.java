package mentorship.roadmap.microservices.service_c.model.dto;

import lombok.Data;


@Data
public class MessageDto {
    private String key;
    private String type;
    private String content;
}
