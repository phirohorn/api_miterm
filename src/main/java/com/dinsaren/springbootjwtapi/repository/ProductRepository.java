package com.dinsaren.springbootjwtapi.repository;

import com.dinsaren.springbootjwtapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByOrderByIdAsc();

    List<Product> findByNameContainingIgnoreCaseOrderByIdAsc(String name);
}
