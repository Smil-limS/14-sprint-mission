package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private static final String ERROR_USER_NOT_FOUND = "존재하지 않는 유저입니다. ID: ";
    private static final String ERROR_CHANNEL_NOT_FOUND = "존재하지 않는 채널입니다. ID: ";

    private final UserService userService;
    private final ChannelService channelService;
    private final MessageRepository messageRepository;

    public BasicMessageService(MessageRepository messageRepository, UserService userService, ChannelService channelService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message create(UUID senderId, UUID channelId, String content) {
        if (userService.read(senderId) == null) {
            throw new IllegalArgumentException(ERROR_USER_NOT_FOUND + senderId);
        }

        if (channelService.read(channelId) == null) {
            throw new IllegalArgumentException(ERROR_CHANNEL_NOT_FOUND + channelId);
        }
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
