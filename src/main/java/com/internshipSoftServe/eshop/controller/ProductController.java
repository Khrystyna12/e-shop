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
import com.internshipSoftServe.eshop.model.Product;
import com.internshipSoftServe.eshop.repository.CategoryRepository;
import com.internshipSoftServe.eshop.repository.ProductRepository;

@RestController
@Transactional
@RequestMapping(path="/shop")
public class ProductController {

	private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
	
	public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping("/products")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
	
	@GetMapping("/products/{productId}")
	public Product getOneProduct(@PathVariable("productId") long productId) {
	    return productRepository.findById(productId)
	    		.orElseThrow(RuntimeException::new);
	}
	
	@GetMapping("/categories/{categoryId}/products")
	public @ResponseBody Iterable<Product> getAllProductsOfCategory(@PathVariable("categoryId") long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(RuntimeException::new);
		return category.getProducts();
    }
	
	@PostMapping("/products")
    public ResponseEntity<Product> createNewProduct(@Valid @RequestBody Product product){
		Category category = categoryRepository.findById(product.getCategoryId())
				.orElseThrow(RuntimeException::new);
        product.setCategory(category);
		return ResponseEntity.ok(productRepository.save(product));
    }
    
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") long productId, @RequestBody Product product2) {
      return productRepository.findById(productId)
          .map(product1 -> {
        	  product1.setName(product2.getName());
        	  product1.setPrice(product2.getPrice());
        	  product1.setQuantity(product2.getQuantity());
        	  product1.setDescription(product2.getDescription());
        	  product1.setPhoto(product2.getPhoto());
        	  product1.setCategory(product2.getCategory());
              Product updated = productRepository.save(product1);
              return ResponseEntity.ok().body(updated);
          }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("productId") long productId){
    	if (!productRepository.findById(productId).isPresent()) {
            ResponseEntity.badRequest().build();
        }
    	productRepository.deleteById(productId);
    	return ResponseEntity.ok().build();
    }
	
}
