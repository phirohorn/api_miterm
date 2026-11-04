package com.dinsaren.springbootjwtapi.repository;

import com.dinsaren.springbootjwtapi.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
