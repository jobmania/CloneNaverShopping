package com.example.springcore.product;


import com.example.springcore.folder.Folder;
import com.example.springcore.product.dto.ProductRequestDto;
import com.example.springcore.validator.ProductValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Product {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "product")
    private List<ProductFolder> myProductFolders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Folder> folderList ;


    // 관심 상품 생성 시 이용합니다.
    public Product(ProductRequestDto requestDto, Long userId) {
// 입력값 Validation
        ProductValidator.validateProductInput(requestDto, userId);

// 관심상품을 등록한 회원 Id 저장
        this.userId = userId;
        this.title = requestDto.getTitle();
        this.image = requestDto.getImage();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.myprice = 0;
    }


    public void addFolder(Folder folder, Product product) {
        ProductFolder productFolder = new ProductFolder(folder,product);
        this.myProductFolders.add(productFolder);
    }

    public void addFolder(Folder folder) {
        this.folderList.add(folder);
    }
}