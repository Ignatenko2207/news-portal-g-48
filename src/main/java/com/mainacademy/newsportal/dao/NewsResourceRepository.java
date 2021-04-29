package com.mainacademy.newsportal.dao;

import com.mainacademy.newsportal.model.NewsResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsResourceRepository extends JpaRepository<NewsResource, Integer> {
}
