package com.blogApplication.BlogApplication2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.BlogApplication2.entity.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Integer>{

}
