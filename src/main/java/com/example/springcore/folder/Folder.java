package com.example.springcore.folder;

import com.example.springcore.product.Product;
import com.example.springcore.product.ProductFolder;
import com.example.springcore.user.User;
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
public class Folder {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "folder")
    private List<ProductFolder> folderList = new ArrayList<>();

//    @ManyToMany
//    private List<Product> products = new ArrayList<>();


    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }
}