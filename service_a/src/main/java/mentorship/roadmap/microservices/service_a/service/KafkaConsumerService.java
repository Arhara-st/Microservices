package mentorship.roadmap.microservices.service_a.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_a.model.entity.KafkaMessage;
import mentorship.roadmap.microservices.service_a.model.dto.MessageDto;
import mentorship.roadmap.microservices.service_a.repository.MessageRepo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final MessageRepo repository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "in")
    public void listen(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        try {
            MessageDto dto = objectMapper.readValue(value, MessageDto.class);

            KafkaMessage kafkaMessage = new KafkaMessage();
            kafkaMessage.setType(dto.getType());
            kafkaMessage.setKey(key);
            kafkaMessage.setContent(dto.getContent());
            repository.save(kafkaMessage);
            log.info("Сообщение сохранено в MongoDB");

            sendMessageToServiceB(kafkaMessage);

            log.debug("Сообщение ушло в сервис B");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMessageToServiceB(KafkaMessage message) {
        webClient.post()
                .uri("/api/process")
                .bodyValue(message)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("Ошибка при отправлении", response.statusCode());
                    return Mono.error(new RuntimeException("Ошибка HTTP: " + response.statusCode()));
                })
                .bodyToMono(Void.class)
                .subscribe();
    }
}

