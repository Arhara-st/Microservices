package mentorship.roadmap.microservices.service_a.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mentorship.roadmap.microservices.service_a.model.entity.KafkaMessage;
import mentorship.roadmap.microservices.service_a.model.dto.MessageDto;
import mentorship.roadmap.microservices.service_a.repository.MessageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final MessageRepo repository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "in")
    public void listen(String message) {
        try {
            MessageDto dto = objectMapper.readValue(message, MessageDto.class);

            KafkaMessage kafkaMessage = new KafkaMessage();
            kafkaMessage.setType(dto.getType());
            kafkaMessage.setKey(dto.getKey());
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

