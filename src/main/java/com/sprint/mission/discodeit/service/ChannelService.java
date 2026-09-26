package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Transactional
    public Channel createChannel(Channel.ChannelType type, String name, String description) {
        Channel channel = Channel.create(type, name, description);
        return channelRepository.save(channel);
    }

    @Transactional
    public void updateChannel(UUID channelId, String newName, String newDescription) {
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(() -> new IllegalArgumentException("채널을 찾을 수 없습니다."));

        channel.update(newName, newDescription);
    }
}
