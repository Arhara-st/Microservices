package mentorship.roadmap.microservices.service_c.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_c.model.MessageMapper;
import mentorship.roadmap.microservices.service_c.model.dto.MessageDto;
import mentorship.roadmap.microservices.service_c.model.entity.Message;
import mentorship.roadmap.microservices.service_c.repository.MessageRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepo messageRepo;
    private final MessageMapper messageMapper;

    @Transactional
    public void save(MessageDto messageDto) {
        Message message = messageMapper.messageDtoToMessage(messageDto);
        messageRepo.save(message);
        log.info("Сообщение сохранено в postgresql");
    }


}
