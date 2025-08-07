package mentorship.roadmap.microservices.service_c.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "messages_from_service_b")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;
}
