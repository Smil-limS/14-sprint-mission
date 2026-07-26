package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.util.List;
import java.util.UUID;

public class BasicUserService implements UserService {
    private final UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String name, String email, String nickname) {
        User user = User.create(name, email, nickname);
        return userRepository.save(user);
    }

    @Override
    public User read(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(UUID id, String name, String email, String nickname) {
        User user = userRepository.findById(id);
        if (user != null){
            user.changeName(name);
            user.changeEmail(email);
            user.changeNickname(nickname);
            userRepository.save(user);
        }
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
