package com.category.repo;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.category.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	/*Set<Product> findByCategoryId(Integer categoryId);
	 Optional<Product> findByProductIdAndCategoryId(Integer productId, Integer categoryId);*/
	
	Page<Product> findByCategoryId(Long postId, Pageable pageable);
    Optional<Product> findByIdAndCategoryId(Long id, Long categoryId);
	
}
