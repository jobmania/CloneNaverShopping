package com.example.springcore.product;

import com.example.springcore.Product;
import com.example.springcore.ProductMypriceRequestDto;
import com.example.springcore.ProductRequestDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public Product createProduct(ProductRequestDto requestDto) throws SQLException {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto);
        productRepository.createProduct(product);

// 응답 보내기
        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {
        if ( requestDto.getMyprice() <= 0 ){
            throw new RuntimeException("최저가는 0값보다 이상 되어야 함");
        }

        Product product = productRepository.getProduct(id);
        if( product == null ){
            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
        }

        productRepository.updateMyprice(id, requestDto.getMyprice());
        return product;
    }

    public List<Product> getProducts() throws SQLException {
        List<Product> products =  productRepository.getProducts();
        return products;
    }
}
