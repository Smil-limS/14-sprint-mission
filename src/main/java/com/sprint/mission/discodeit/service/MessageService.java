package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @Transactional
    public Message createMessage(UUID channelId, UUID authorId, String content,
        List<BinaryContent> attachments) {
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(() -> new IllegalArgumentException("채널을 찾을 수 없습니다."));
        User user = userRepository.findById(authorId)
            .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Message message = Message.create(content, channel, user);

        if (attachments != null && !attachments.isEmpty()) {
            for (BinaryContent attachment : attachments) {
                message.addAttachment(attachment);
            }
        }

        return messageRepository.save(message);
    }

    @Transactional
    public void updateMessageContent(UUID messageId, String newContent) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new IllegalArgumentException("메세지를 찾을 수 없습니다."));

        message.updateContent(newContent);
    }

    @Transactional
    public List<Message> getMessagesByChannel(UUID channelId) {
        return messageRepository.findByChannelId(channelId);
    }
}
