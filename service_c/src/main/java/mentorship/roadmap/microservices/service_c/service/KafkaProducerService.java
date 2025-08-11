package mentorship.roadmap.microservices.service_c.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_c.model.dto.MessageDto;
import mentorship.roadmap.microservices.service_c.model.dto.MessageToKafkaDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String key, MessageDto message) {
        MessageToKafkaDto messageToKafkaDto = new MessageToKafkaDto();
        messageToKafkaDto.setContent(message.getContent());
        messageToKafkaDto.setType(message.getType());
        try {
            kafkaTemplate.send(topic, key ,messageToKafkaDto);
            log.debug("Сообщение отправлено в топик {}", topic);
        } catch (Exception e) {
            log.error("Не удалось отправить", e);
        }
    }



}
