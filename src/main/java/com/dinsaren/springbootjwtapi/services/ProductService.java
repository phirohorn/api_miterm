package com.dinsaren.springbootjwtapi.services;

import com.dinsaren.springbootjwtapi.exception.NotFoundException;
import com.dinsaren.springbootjwtapi.models.Product;
import com.dinsaren.springbootjwtapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll(String search) {
        if (StringUtils.hasText(search)) {
            return productRepository.findByNameContainingIgnoreCaseOrderByIdAsc(search.trim());
        }
        return productRepository.findAllByOrderByIdAsc();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));
    }
}
