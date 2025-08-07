package mentorship.roadmap.microservices.service_c.service;

import lombok.RequiredArgsConstructor;
import mentorship.roadmap.microservices.service_c.model.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    public void send(String topic, MessageDto message) {
        try {
            kafkaTemplate.send(topic, message);
            log.debug("Сообщение отправлено в топик {}", topic);
        } catch (Exception e) {
            log.error("Не удалось отправить", e);
        }
    }
}
