package mentorship.roadmap.microservices.service_c.repository;

import mentorship.roadmap.microservices.service_c.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {

}
