package com.example.springcore.testdata;


import com.example.springcore.itemsearch.ItemSearchService;
import com.example.springcore.product.Product;
import com.example.springcore.product.ProductRepository;
import com.example.springcore.product.dto.ItemDto;
import com.example.springcore.user.User;
import com.example.springcore.user.UserRepository;
import com.example.springcore.user.UserRoleEnum;
import com.example.springcore.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.springcore.product.ProductService.MIN_MY_PRICE;

@Component
public class TestDataRunner implements ApplicationRunner {  // 스프링이 기동이 될때 실행을 한다 !

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ItemSearchService itemSearchService;

    @Override
    public void run(ApplicationArguments args) throws Exception {  // 스프링이 기동이 될때 실행을 한다 !
// 테스트 User 생성
        User testUser = new User("슈가", passwordEncoder.encode("123"), "sugar@sparta.com", UserRoleEnum.USER);
        User testUser2 = new User("준식", passwordEncoder.encode("123"), "sugar1@sparta.com", UserRoleEnum.USER);
        User testUser3 = new User("찬기", passwordEncoder.encode("123"), "sugar2@sparta.com", UserRoleEnum.ADMIN);
        testUser = userRepository.save(testUser);
        testUser2 = userRepository.save(testUser2);
        testUser3 = userRepository.save(testUser3);

// 테스트 User 의 관심상품 등록
// 검색어 당 관심상품 10개 등록
        createTestData(testUser, "비키니");
        createTestData(testUser, "모노키니");
        createTestData(testUser, "여자수영복");
        createTestData(testUser, "여자래쉬가드");
        createTestData(testUser, "신발");
        createTestData(testUser, "과자");
        createTestData(testUser, "키보드");
        createTestData(testUser, "휴지");
        createTestData(testUser, "휴대폰");
        createTestData(testUser, "앨범");
        createTestData(testUser, "헤드폰");
        createTestData(testUser, "이어폰");
        createTestData(testUser, "노트북");
        createTestData(testUser, "무선 이어폰");
        createTestData(testUser, "모니터");

    }

    private void createTestData(User user, String searchWord) throws IOException {
// 네이버 쇼핑 API 통해 상품 검색
        List<ItemDto> itemDtoList = itemSearchService.getItems(searchWord);

        List<Product> productList = new ArrayList<>();

        for (ItemDto itemDto : itemDtoList) {
            Product product = new Product();
// 관심상품 저장 사용자
            product.setUserId(user.getId());
// 관심상품 정보
            product.setTitle(itemDto.getTitle());
            product.setLink(itemDto.getLink());
            product.setImage(itemDto.getImage());
            product.setLprice(itemDto.getLprice());

// 희망 최저가 랜덤값 생성
// 최저 (100원) ~ 최대 (상품의 현재 최저가 + 10000원)
            int myPrice = getRandomNumber(MIN_MY_PRICE, itemDto.getLprice() + 10000);
            product.setMyprice(myPrice);

            productList.add(product);
        }

        productRepository.saveAll(productList);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}