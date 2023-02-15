package com.example.springcore.product;

import com.example.springcore.product.dto.ProductMypriceRequestDto;
import com.example.springcore.product.dto.ProductRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Autowired // 수동 빈등록
//    public ProductService(ApplicationContext context){
//        ProductRepository productRepository = (ProductRepository) context.getBean("productRepository");
//            ProductRepository productRepository = (ProductRepository) context.getBean(ProductRepository.class);
//        this.productRepository = productRepository;
//    }



    public Product createProduct(ProductRequestDto requestDto){
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto);
        productRepository.save(product);

// 응답 보내기
        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto){
        if ( requestDto.getMyprice() <= 0 ){
            throw new RuntimeException("최저가는 0값보다 이상 되어야 함");
        }

        Product product = productRepository.findById(id).orElseThrow(
                ()  ->  new NullPointerException("해당 아이디가 존재하지 않습니다."));

        product.setMyprice(requestDto.getMyprice());
        productRepository.save(product);
        return product;
    }

    // db에 관한 SQLException 등ㅇ등 본인이 처리한당~
    public List<Product> getProducts() {
        List<Product> products =  productRepository.findAll();
        return products;
    }
}
