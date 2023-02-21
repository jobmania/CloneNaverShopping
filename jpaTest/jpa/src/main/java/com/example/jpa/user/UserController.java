package com.example.jpa.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/create")
    public void createUser() {
        User user = userService.createUser();

// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }

    @GetMapping("/user/delete")
    public void deleteUser() {
        User user = userService.deleteUser();

// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }

    @GetMapping("/user/update/fail")
    public void updateUserFail() {
        User user = userService.updateUserFail();

// 중요!) DB 에 변경 내용이 적용되었는지 확인!
// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }

    @GetMapping("/user/update/1")
    public void updateUser1() {
        User user = userService.updateUser1();

// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }

    @GetMapping("/user/update/2")
    public void updateUse2() {
        User user = userService.updateUser2();

// 중요!) DB 에 변경 내용이 적용되었는지 확인!
// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }
}