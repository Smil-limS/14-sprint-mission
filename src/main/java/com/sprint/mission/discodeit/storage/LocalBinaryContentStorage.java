package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

@Component
public class LocalBinaryContentStorage implements BinaryContentStorage {
    private final Path root;

    public LocalBinaryContentStorage(){
        this.root = Paths.get("uploads");
    }

    @PostConstruct
    public void init(){
        try{
            if (!Files.exists(root)){
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("저장소 폴더를 생설할 수 없습니다", e);
        }
    }

    @Override
    public UUID put(UUID id, byte[] data) {
        try{
            Path filePath = resolvePath(id);
            Files.write(filePath, data);
            return id;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }
    }

    @Override
    public InputStream get(UUID id) {
        try{
            Path filePath = resolvePath(id);
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽어올 수 없습니다.", e);
        }
    }

    @Override
    public ResponseEntity<Resource> download(BinaryContentDto dto) {
        try {
            Path filePath = resolvePath(dto.id());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()){
                throw new RuntimeException("다운로드할 파일을 찾을 수 없습니다.");
            }

            String encodedFileName = UriUtils.encode(dto.fileName(), StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dto.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("파일 다운로드 처리 중 오류가 발생했습니다.", e);
        }
    }

    private Path resolvePath(UUID id) {
        return root.resolve(id.toString());
    }
}
