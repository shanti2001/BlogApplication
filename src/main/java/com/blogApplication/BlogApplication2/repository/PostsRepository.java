package com.blogApplication.BlogApplication2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blogApplication.BlogApplication2.entity.Posts;


public interface PostsRepository extends JpaRepository<Posts, Integer>{

    List<Posts> findByTitleContaining(String searchText);
    List<Posts> findByContentContaining(String searchText);
//    List<Posts> findByAuthorContaining(String searchText);


}
