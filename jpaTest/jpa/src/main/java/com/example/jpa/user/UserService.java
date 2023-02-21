package com.example.jpa.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser() {
// 테스트 회원 "user1" 객체 추가
        User beforeSavedUser = new User("user1", "정국", "불족발");
// 회원 "user1" 객체를 영속화
        User savedUser = userRepository.save(beforeSavedUser);

// beforeSavedUser: 영속화되기 전 상태의 자바 일반객체
// savedUser:영속성 컨텍스트 1차 캐시에 저장된 객체
        assert(beforeSavedUser != savedUser);

// 회원 "user1" 을 조회      -> 여기서 보면 영속성 컨테스트 캐시된 user가 조회.
        User foundUser1 = userRepository.findById("user1").orElse(null);
        assert(foundUser1 == savedUser);

// 회원 "user1" 을 또 조회-> 여기서 보면 영속성 컨테스트 캐시된 user가 조회.
        User foundUser2 = userRepository.findById("user1").orElse(null);
        assert(foundUser2 == savedUser);

// 회원 "user1" 을 또또 조회-> 여기서 보면 영속성 컨테스트 캐시된 user가 조회.
        User foundUser3 = userRepository.findById("user1").orElse(null);
        assert(foundUser3 == savedUser);

        return foundUser3;
    }

    public User deleteUser() {
// 테스트 회원 "user1" 객체 추가
        User firstUser = new User("user1", "지민", "엄마는 외계인");
// 회원 "user1" 객체를 영속화
        User savedFirstUser = userRepository.save(firstUser);

// 회원 "user1" 삭제
        userRepository.delete(savedFirstUser);

// 회원 "user1" 조회
        User deletedUser1 = userRepository.findById("user1").orElse(null);
        assert(deletedUser1 == null);

// -------------------
// 테스트 회원 "user1" 객체를 다시 추가
// 회원 "user1" 객체 추가
        User secondUser = new User("user1", "지민", "엄마는 외계인");

// 회원 "user1" 객체를 영속화
        User savedSecondUser = userRepository.save(secondUser);
        assert(savedFirstUser != savedSecondUser);
        assert(savedFirstUser.getUsername().equals(savedSecondUser.getUsername()));
        assert(savedFirstUser.getNickname().equals(savedSecondUser.getNickname()));
        assert(savedFirstUser.getFavoriteFood().equals(savedSecondUser.getFavoriteFood()));

// 회원 "user1" 조회
        User foundUser = userRepository.findById("user1").orElse(null);
        assert(foundUser == savedSecondUser);

        return foundUser;
    }

    public User updateUserFail() {
// 회원 "user1" 객체 추가
        User user = new User("user1", "뷔", "콜라");
// 회원 "user1" 객체를 영속화
        User savedUser = userRepository.save(user);

// 회원의 nickname 변경
        savedUser.setNickname("얼굴천재");
// 회원의 favoriteFood 변경
        savedUser.setFavoriteFood("버거킹");

// 회원 "user1" 을 조회
        User foundUser = userRepository.findById("user1").orElse(null);
// 중요!) foundUser 는 DB 값이 아닌 1차 캐시에서 가져오는 값
        assert(foundUser == savedUser);
        assert(foundUser.getUsername().equals(savedUser.getUsername()));
        assert(foundUser.getNickname().equals(savedUser.getNickname()));
        assert(foundUser.getFavoriteFood().equals(savedUser.getFavoriteFood()));

        return foundUser;
    }

    public User updateUser1() {
// 테스트 회원 "user1" 생성
        User user = new User("user1", "RM", "고기");
// 회원 "user1" 객체를 영속화
        User savedUser1 = userRepository.save(user);

// 회원의 nickname 변경
        savedUser1.setNickname("남준이");
// 회원의 favoriteFood 변경
        savedUser1.setFavoriteFood("육회");

// user1 을 저장
        User savedUser2 = userRepository.save(savedUser1);
        assert(savedUser1 == savedUser2);

        return savedUser2;
    }

    @Transactional
    public User updateUser2() {
// 테스트 회원 "user1" 생성
// 회원 "user1" 객체 추가
        User user = new User("user1", "진", "꽃등심");
// 회원 "user1" 객체를 영속화
        User savedUser = userRepository.save(user);

// 회원의 nickname 변경
        savedUser.setNickname("월드와이드핸섬 진");
// 회원의 favoriteFood 변경
        savedUser.setFavoriteFood("까르보나라");

        return savedUser;
    }
}