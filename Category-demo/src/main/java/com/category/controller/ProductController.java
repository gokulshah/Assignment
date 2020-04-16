package com.category.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.category.entity.Product;
import com.category.excepation.ResourceNotFoundException;
import com.category.repo.CategoryRepository;
import com.category.repo.ProductRepository;

@RestController
@RequestMapping("/categorys")
public class ProductController {

	@Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @GetMapping("/categorys/{categoryId}/products")
    public Page<Product> getAllProductByCategoryId(@PathVariable (value = "categoryId") Long categoryId,
                                                Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    @PostMapping("/categorys/{categoryId}/products")
    public Product createProduct(@PathVariable (value = "categoryId") Long categoryId,
                                 @Valid @RequestBody Product product) throws ResourceNotFoundException {
    	return categoryRepository.findById(categoryId).map(category ->{
    		product.setCategory(category);
    	
    	return productRepository.save(product);
    	}).orElseThrow(()-> new ResourceNotFoundException("CategoryId" +categoryId +"not found"));
    }

    
    @PutMapping("/categorys/{categoryid}/products/{productId}")
    public Product updateProduct(@PathVariable(value = "categoryId") Long categoryId,
        @PathVariable(value = "productId") Integer productId, @Valid @RequestBody Product productRequest)
    throws ResourceNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("categoryId not found");
        }
        return productRepository.findById(productId).map(product -> {product.setName(productRequest.getName());
        return productRepository.save(product);
        }).orElseThrow(() -> new ResourceNotFoundException("product not foun"));

    }

    @DeleteMapping("/categorys/{categoryid}/products/{productId}")
    public ResponseEntity < ? > deleteProduct(@PathVariable(value = "categoryId") Long categoryId,
        @PathVariable(value = "productId") Long productId) throws ResourceNotFoundException {
    	return productRepository.findByIdAndCategoryId(productId, categoryId).map(product -> {
    		productRepository.delete(product);
    		return ResponseEntity.ok().build();
    	}).orElseThrow(() -> new ResourceNotFoundException("product not found with id" + productId + " and categoryId " + categoryId));}
    
       }
    
        

