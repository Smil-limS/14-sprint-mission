package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;

    public BasicMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message create(UUID senderId, UUID channelId, String content) {
        Message message = Message.create(senderId, channelId, content);
        return messageRepository.save(message);
    }

    @Override
    public Message read(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> readAll() {
        return messageRepository.findAll();
    }

    @Override
    public void update(UUID id, String content) {
        Message message = messageRepository.findById(id);
        if (message != null){
            message.changeContent(content);
            messageRepository.save(message);
        }
    }

    @Override
    public void delete(UUID id) {
        messageRepository.deleteById(id);
    }
}
