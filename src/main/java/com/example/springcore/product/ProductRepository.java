package com.example.springcore.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByUserId(Long userId, Pageable pageable);

    Page<Product> findAllByUserIdAndFolderList_Id(Long userId, Long folderId, Pageable pageable); // _ 언더바를 하면 해당 객체의 속성들고오기
}