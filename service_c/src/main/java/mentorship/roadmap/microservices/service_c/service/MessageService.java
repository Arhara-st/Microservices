package mentorship.roadmap.microservices.service_c.service;

import lombok.RequiredArgsConstructor;
import mentorship.roadmap.microservices.service_c.model.MessageMapper;
import mentorship.roadmap.microservices.service_c.model.dto.MessageDto;
import mentorship.roadmap.microservices.service_c.model.entity.Message;
import mentorship.roadmap.microservices.service_c.repository.MessageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepo messageRepo;
    private final MessageMapper messageMapper;
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Transactional
    public void save(MessageDto messageDto) {
        Message message = messageMapper.messageDtoToMessage(messageDto);
        messageRepo.save(message);
        log.info("Сообщение сохранено в postgresql");
    }


}
