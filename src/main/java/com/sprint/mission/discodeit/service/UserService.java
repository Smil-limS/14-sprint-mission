package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(String username, String email, String password) {
        User user = User.create(username, email, password);

        UserStatus userStatus = UserStatus.create(user);
        user.updateStatus(userStatus);

        return userRepository.save(user);
    }

    @Transactional
    public void updateUserInfo(UUID userId, String username, String email, BinaryContent profile) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        user.update(username, email, profile);
    }

    @Transactional
    public void changePassword(UUID userId, String newPassword) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        user.updatePassword(newPassword);
    }

    public String getUserProfileFileName(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (user.getProfile() == null) {
            return "기본프로필.png";
        }

        return user.getProfile().getFileName();
    }


    @Transactional
    public void updateUserActivity(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (user.getStatus() != null) {
            user.getStatus().updateLastActiveAt();
        }
    }
}