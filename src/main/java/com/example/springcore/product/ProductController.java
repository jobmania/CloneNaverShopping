package com.example.springcore.product;

import com.example.springcore.Product;
import com.example.springcore.ProductMypriceRequestDto;
import com.example.springcore.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
@RequiredArgsConstructor
public class ProductController {


    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) throws SQLException {
        ProductService productService = new ProductService();
        Product product = productService.createProduct(requestDto);

        return product;
    }


    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
        ProductService productService = new ProductService();

        Product product = productService.updateProduct(id,requestDto);

        return product.getId();
    }
}
