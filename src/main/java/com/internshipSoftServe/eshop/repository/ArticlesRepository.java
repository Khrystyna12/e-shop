package com.internshipSoftServe.eshop.repository;

import com.internshipSoftServe.eshop.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    Optional<Articles> findById(Long id);
    void deleteById (long articleId);
}
