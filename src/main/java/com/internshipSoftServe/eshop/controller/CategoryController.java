package com.internshipSoftServe.eshop.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.internshipSoftServe.eshop.model.Category;
import com.internshipSoftServe.eshop.repository.CategoryRepository;

@RestController
@Transactional
@RequestMapping(path="/shop")
public class CategoryController {

	private CategoryRepository categoryRepository;
	
	public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
	
	@GetMapping("/categories")
    public @ResponseBody Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
	
	@GetMapping("/categories/{categoryId}")
	public Category getOneCategory(@PathVariable("categoryId") long categoryId) {
	    return categoryRepository.findById(categoryId)
	    		.orElseThrow(RuntimeException::new);
	}
	
	@PostMapping("/categories")
    public ResponseEntity<Category> createNewCategory(@Valid @RequestBody Category category){
		return ResponseEntity.ok(categoryRepository.save(category));
    }
    
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") long categoryId, @RequestBody Category category2) {
      return categoryRepository.findById(categoryId)
          .map(category1 -> {
              category1.setName(category2.getName());
              category1.setDescription(category2.getDescription());
              Category updated = categoryRepository.save(category1);
              return ResponseEntity.ok().body(updated);
          }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("categoryId") long categoryId){
    	if (!categoryRepository.findById(categoryId).isPresent()) {
            ResponseEntity.badRequest().build();
        }
    	categoryRepository.deleteById(categoryId);
    	return ResponseEntity.ok().build();
    }
}
