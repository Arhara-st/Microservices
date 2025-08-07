package mentorship.roadmap.microservices.service_c.model;

import mentorship.roadmap.microservices.service_c.model.dto.MessageDto;
import mentorship.roadmap.microservices.service_c.model.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDto messageToMessageDto(Message message);
    Message messageDtoToMessage(MessageDto messageDto);
}
