package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

public class FileChannelRepository extends AbstractFileRepository<Channel> implements ChannelRepository {
    private static final String FILE_PATH = "channels.dat";

    public FileChannelRepository() {
        super(FILE_PATH);
    }
}
