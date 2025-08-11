package mentorship.roadmap.microservices.service_c.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_c.model.dto.MessageDto;
import mentorship.roadmap.microservices.service_c.service.KafkaProducerService;
import mentorship.roadmap.microservices.service_c.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;
    private final KafkaProducerService kafkaProducerService;


    @PostMapping("/api/save")
    public ResponseEntity<?> save(@RequestBody MessageDto messageDto) {
        log.debug("Сообщение пришло в сервис C");
        messageService.save(messageDto);
        kafkaProducerService.send("out", messageDto.getKey() ,messageDto);
        return ResponseEntity.ok(200);
    }
}
