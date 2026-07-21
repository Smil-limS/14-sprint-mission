package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFUserService implements UserService {

    private final Map<UUID, User> data = new HashMap<>();

    @Override
    public User create(String name, String email, String nickname) {
        User user = User.create(name, email, nickname);
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public User read(UUID id) {
        return data.get(id);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(UUID id, String name, String email, String nickname) {
        User user = data.get(id);
        if (user != null){
            user.changeName(name);
            user.changeEmail(email);
            user.changeNickname(nickname);
        }
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
