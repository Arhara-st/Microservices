package mentorship.roadmap.microservices.service_a.repository;

import mentorship.roadmap.microservices.service_a.model.entity.KafkaMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends MongoRepository<KafkaMessage, String> {

}
