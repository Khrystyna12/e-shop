package com.internshipSoftServe.eshop.controller;

import com.internshipSoftServe.eshop.model.Articles;
import com.internshipSoftServe.eshop.model.Product;
import com.internshipSoftServe.eshop.repository.ArticlesRepository;
import com.internshipSoftServe.eshop.repository.ProductRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Articles", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping(value = "/shop")
public class ArticlesController {

    @Autowired
    private ArticlesRepository articlesRepository;
    @Autowired
    private ProductRepository productRepository;

    public ArticlesController(ArticlesRepository articlesRepository, ProductRepository productRepository) {
        this.articlesRepository = articlesRepository;
        this.productRepository = productRepository;
    }

    @ApiOperation(value = "Get all articles", response = Articles.class)
    @GetMapping("/articles")
    public @ResponseBody Iterable<Articles> getAllArticles(){
        return articlesRepository.findAll();
    }

    @ApiOperation(value = "Get articles by id", response = Articles.class)
    @GetMapping("/articles/{articleId}")
    public @ResponseBody Articles getOneArticle(@PathVariable("articleId") long articleId){
        return articlesRepository.findById(articleId).orElseThrow(RuntimeException::new);
    }

    @ApiOperation(value = "Get articles by product id", response = Articles.class)
    @GetMapping("products/{productId}/articles")
    public @ResponseBody Iterable<Articles> getArticleByProduct (@ApiParam(value = "Product param id", example = "1")@PathVariable ("productId") long productId){
        Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new);
        return product.getArticles();
    }

    @ApiOperation(value = "Create article to product by param: id")
    @PostMapping("/articles")
    public ResponseEntity<Articles> createNewArticleToProduct(@ApiParam(value = "Request body Article",required = true)@Valid @RequestBody Articles article){
        Product product = productRepository.findById(article.getProductId()).orElseThrow(RuntimeException::new);
        article.setProduct(product);
        return ResponseEntity.ok(articlesRepository.save(article));
    }

    @ApiOperation(value = "Update article")
    @PutMapping("/articles/{articleId}")
    public ResponseEntity<Articles> updateArticle(@ApiParam(value = "Article param id", example = "1")@PathVariable("articleId") long articleId,
                                                  @ApiParam(value = "Request body Article",required = true)@RequestBody Articles article){
        return articlesRepository.findById(articleId).
                map(articles -> {articles.setName(article.getName());
                articles.setText(article.getText());
                articles.setCreated_at(article.getCreated_at());
                articles.setProduct(article.getProduct());
                Articles updatedArticle = articlesRepository.save(articles);
                return ResponseEntity.ok().body(updatedArticle);
                }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Delete Article by id")
    @DeleteMapping("/articles/{articleId}")
    public void deleteArticle(@ApiParam(value = "Article param id", example = "1")@PathVariable long articleId){
         articlesRepository.deleteById(articleId);
    }

}
