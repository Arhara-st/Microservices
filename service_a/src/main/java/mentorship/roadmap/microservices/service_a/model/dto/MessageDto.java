package mentorship.roadmap.microservices.service_a.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private String key;
    private String type;
    private String content;
}
