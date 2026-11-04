package com.dinsaren.springbootjwtapi.controllers;

import com.dinsaren.springbootjwtapi.models.Product;
import com.dinsaren.springbootjwtapi.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product list")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "List products, optionally filtered by name")
    @GetMapping
    public List<Product> findAll(@RequestParam(required = false) String search) {
        return productService.findAll(search);
    }

    @Operation(summary = "Get one product")
    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id) {
        return productService.findById(id);
    }
}
