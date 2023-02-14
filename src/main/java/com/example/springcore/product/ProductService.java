package com.example.springcore.product;

import com.example.springcore.Product;
import com.example.springcore.ProductMypriceRequestDto;
import com.example.springcore.ProductRequestDto;

import java.sql.*;

public class ProductService {

    public Product createProduct(ProductRequestDto requestDto) throws SQLException {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto);

        ProductRepository productRepository = new ProductRepository();
        productRepository.createProduct(product);

// 응답 보내기
        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        ProductRepository productRepository = new ProductRepository();
        Product product = productRepository.getProduct(id);
        if( product == null ){
            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
        }

        return product;
    }
}
