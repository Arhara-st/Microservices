package mentorship.roadmap.microservices.service_b.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_b.model.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final WebClient webClient;

    public void saveIfImportant(Message message) {
        send(message);
        log.debug("Сообщение отправлено в сервис C");
        if ("important".equalsIgnoreCase(message.getType())) {
            String key = "important_message:" + message.getKey();

            redisTemplate.opsForList().rightPush(key, message);
            redisTemplate.expire("important_messages_list", Duration.ofMinutes(5));

            log.info("Сообщение сохранено в redis: " + message.getKey());
        }
    }
    private void send(Message message) {
        webClient.post()
                .uri("/api/save")
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
