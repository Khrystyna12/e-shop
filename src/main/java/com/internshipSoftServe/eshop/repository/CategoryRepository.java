package com.internshipSoftServe.eshop.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.internshipSoftServe.eshop.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findById(Long id);

	void deleteById(long categoryId);
}
