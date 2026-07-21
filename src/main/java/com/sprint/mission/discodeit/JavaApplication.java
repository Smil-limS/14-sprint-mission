package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import java.util.List;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {
        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService(userService, channelService);

        System.out.println("=== 1. 생성(Create) 테스트 ===");
        // 유저 2명 생성
        User user1 = userService.create("가나다", "ga@codeit.com", "나다");
        User user2 = userService.create("라마바", "la@codeit.com", "마바");
        System.out.println("user1 = " + user1);
        System.out.println("user2 = " + user2);

        // 유저1의 채널 생성
        Channel channel1 = channelService.create(user1.getId(), "한글 초급");
        System.out.println("channel1 = " + channel1);

        // 유저 1의 채널에서 메세지 생성
        Message message1 = messageService.create(user1.getId(), channel1.getId(), "세종대왕 굿");
        System.out.println("message1 = " + message1);


        System.out.println("\n=== 2. 수정(Update) 테스트 ===");
        // 유저2의 정보 수정
        userService.update(user2.getId(), "랑망방", "rang@codeit.com", "망방");
        System.out.println("수정된 user2 = " + userService.read(user2.getId()));


        System.out.println("\n=== 3. 조회(Read) 테스트 ===");
        // 유저1 조회
        User readUser = userService.read(user1.getId());
        System.out.println("조회된 user1 = " + readUser);


        System.out.println("\n=== 4. 삭제(Delete) 테스트 ===");
        // 유저2 삭제
        userService.delete(user2.getId());

        // 전체 유저 목록 조회
        List<User> users = userService.readAll();
        System.out.println("남은 유저 목록 = " + users);

        System.out.println("\n=== 5. 예외 처리(검증) 테스트 ===");
        try {
            // 유령 회원 ID 생성 후 메세지 생성 시도
            messageService.create(UUID.randomUUID(), channel1.getId(), "유령 유저의 메시지");
        } catch (IllegalArgumentException e) {
            System.out.println("예외 발생 성공 (방어 완료!): " + e.getMessage());
        }

        try {
            // 유령 채널 ID 생성 후 메세지 생성 시도
            messageService.create(user1.getId(), UUID.randomUUID(), "유령 유저의 메시지");
        } catch (IllegalArgumentException e) {
            System.out.println("예외 발생 성공 (방어 완료!): " + e.getMessage());
        }
    }
}
