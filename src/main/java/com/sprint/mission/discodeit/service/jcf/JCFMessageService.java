package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data = new HashMap<>();

    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message create(UUID senderId, UUID channelId, String content) {
        if (userService.read(senderId) == null) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        if (channelService.read(channelId) == null) {
            throw new IllegalArgumentException("존재하지 않는 채널입니다.");
        }

        Message message = Message.create(senderId, channelId, content);
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Message read(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(UUID id, String content) {
        Message message = data.get(id);
        if (message != null){
            message.changeContent(content);
        }
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
