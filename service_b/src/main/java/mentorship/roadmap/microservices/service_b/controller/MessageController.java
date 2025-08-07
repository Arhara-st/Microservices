package mentorship.roadmap.microservices.service_b.controller;

import lombok.RequiredArgsConstructor;
import mentorship.roadmap.microservices.service_b.model.Message;
import mentorship.roadmap.microservices.service_b.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);


    @PostMapping("/api/process")
    public ResponseEntity<?> process(@RequestBody Message message) {
        log.debug("Сообщение пришло в сервис B");
        messageService.saveIfImportant(message);
        return ResponseEntity.ok().body(message);
    }
}
