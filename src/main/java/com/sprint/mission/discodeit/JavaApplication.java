package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import java.util.UUID;

public class JavaApplication {
    static User setupUser(UserService userService) {
        return userService.create("woody", "woody@codeit.com", "woody1234");
    }

    static Channel setupChannel(ChannelService channelService) {
        return channelService.create(UUID.randomUUID(), "채널1");
    }

    static void messageCreateTest(User user, Channel channel, MessageService messageService) {
        Message message = messageService.create(user.getId(), channel.getId(), "채널1 공지");
        System.out.println("메시지 생성: " + message);
    }

    public static void main(String[] args) {
        // 레포지토리 객체 생성
        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();

        // 서비스 주입
        UserService userService = new BasicUserService(userRepository);
        ChannelService channelService = new BasicChannelService(channelRepository);
        MessageService messageService = new BasicMessageService(messageRepository);

        // JCF 방법
//        UserService userService = new JCFUserService();
//        ChannelService channelService = new JCFChannelService();
//        MessageService messageService = new JCFMessageService(userService, channelService);

        // sprint 2-2 테스트
        System.out.println("=== 과제 템플릿 기본 테스트 시작 ===");
        // [3] 과제 템플릿 셋업 및 테스트 실행
        User user = setupUser(userService);
        Channel channel = setupChannel(channelService);
        // 테스트
        messageCreateTest(user, channel, messageService);
        System.out.println("=== 과제 템플릿 기본 테스트 완료 ===\n");


//        // sprint 2-1 테스트
//        System.out.println("=== 생성(create) 테스트 ===");
//        User user1 = userService.create("가나다", "ga@codeit.com", "나다");
//        User user2 = userService.create("라마바", "la@codeit.com", "마바");
//
//        System.out.println("user1 = " + user1);
//        System.out.println("user2 = " + user2);
//
//        Channel channel1 = channelService.create(user1.getId(), "한글 초급");
//        messageService.create(user1.getId(), channel1.getId(), "세종대왕 굿");
//        System.out.println("channel1 = " + channel1);
//
//        System.out.println("=== 수정(update) 테스트 ===");
//        userService.update(user2.getId(), "랑망방", "rang@codeit.com", "망방");
//
//        System.out.println("user2 = " + user2);
//
//        System.out.println("=== 조회(read) 테스트 ===");
//        User read1 = userService.read(user1.getId());
//        System.out.println("read1 = " + read1);
//
//        System.out.println("=== 삭제(delete) 테스트 ===");
//        userService.delete(user2.getId());
//        List<User> users = userService.readAll();
//        System.out.println("users = " + users);
//
//        System.out.println("=== 예외 처리(검증) 테스트 ===");
//        try {
//            // 일부러 존재하지 않는 랜덤 유저 ID를 넣어서 메시지 생성 시도.
//            messageService.create(UUID.randomUUID(), channel1.getId(), "유령 유저의 메시지");
//        } catch (IllegalArgumentException e) {
//            System.out.println("예외 발생 성공: " + e.getMessage());
//        }
//
//        try {
//            // 일부러 존재하지 않는 랜덤 채널 ID를 넣어서 메시지 생성을 시도해 봅니다.
//            messageService.create(user1.getId(), UUID.randomUUID(), "유령 유저의 메시지");
//        } catch (IllegalArgumentException e) {
//            // 우리가 JCFMessageService에 적어둔 에러 메시지가 잘 출력되는지 확인합니다!
//            System.out.println("예외 발생 성공: " + e.getMessage());
//        }
    }
}
