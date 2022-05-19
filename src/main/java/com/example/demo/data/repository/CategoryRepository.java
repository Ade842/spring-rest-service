package com.example.demo.data.repository;

import com.example.demo.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query(value = "SELECT * FROM category", nativeQuery = true)
  List<Category> getAllCategory();

  @Query(value = "SELECT * FROM  category u WHERE u.id = :id ", nativeQuery = true)
  Category getCategoryById(@Param("id") long id);
}
