package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentStorage binaryContentStorage;

    @Transactional
    public BinaryContent uploadFile(String fileName, Long size, String contentType, byte[] bytes) {
        BinaryContent fileMetadata = BinaryContent.create(fileName, size, contentType);

        BinaryContent savedMetadata = binaryContentRepository.save(fileMetadata);

        binaryContentStorage.put(savedMetadata.getId(), bytes);
        
        return savedMetadata;
    }

    public BinaryContent getFile(UUID fileId) {
        return binaryContentRepository.findById(fileId)
            .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));
    }
}
