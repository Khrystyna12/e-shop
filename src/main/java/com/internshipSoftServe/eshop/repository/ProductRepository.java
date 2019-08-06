package com.internshipSoftServe.eshop.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.internshipSoftServe.eshop.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	Optional<Product> findById(long productId);

	void deleteById(long productId);

}
