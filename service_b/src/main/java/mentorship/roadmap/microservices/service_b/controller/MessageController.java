package mentorship.roadmap.microservices.service_b.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_b.model.Message;
import mentorship.roadmap.microservices.service_b.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;


    @PostMapping("/api/process")
    public ResponseEntity<?> process(@RequestBody Message message) {
        log.debug("Сообщение пришло в сервис B");
        messageService.saveIfImportant(message);
        return ResponseEntity.ok().body(message);
    }
}
