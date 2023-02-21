package com.example.jpa.repo;

import com.example.jpa.user.User;
import com.example.jpa.user.UserRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Order(1)
    @Test
    public void create() {
// 회원 "user1" 객체 생성
        User instance1 = new User("user1", "정국", "불족발");
// 회원 "user1" 객체 또 생성
        User instance2 = new User("user1", "정국", "불족발");
        assert(instance2 != instance1);
// 회원 "user1" 객체 또또 생성
        User instance3 = new User("user1", "정국", "불족발");
        assert(instance3 != instance2);

// 회원 "user1" 객체 추가
        User user1 = new User("user1", "정국", "불족발");

// 회원 "user1" 객체의 ID 값이 없다가..
        userRepository.save(user1);

// 테스트 회원 데이터 삭제
        userRepository.delete(user1);
    }

    @Order(2)
    @Test
    public void findUser() {
// ------------------------------------
// 회원 "user1" 객체 추가
        User beforeSavedUser = new User("user1", "정국", "불족발");

// 회원 "user1" 객체를 영속화
        User savedUser = userRepository.save(beforeSavedUser);

// 회원 "user1" 을 조회
        User foundUser1 = userRepository.findById("user1").orElse(null);
        assert(foundUser1 != savedUser);

// 회원 "user1" 을 또 조회
        User foundUser2 = userRepository.findById("user1").orElse(null);
        assert(foundUser2 != savedUser);

// 회원 "user1" 을 또또 조회  ->
        User foundUser3 = userRepository.findById("user1").orElse(null);
        assert(foundUser3 != savedUser);

// ------------------------------------
// 테스트 회원 데이터 삭제
        userRepository.delete(beforeSavedUser);
    }
}