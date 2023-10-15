package com.blogApplication.BlogApplication2.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.BlogApplication2.entity.Posts;
import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.PostsRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;

@Service
public class PostService {
	@Autowired
	private PostsRepository postsRepository;
	@Autowired
	private UserRepository userRepository;
	
	public List<Posts> findPostsByTitleOrContentOrAuthor(String searchText) {
		List<Posts> results = new ArrayList<>();
	    results.addAll(postsRepository.findByTitleContaining(searchText));
	    results.addAll(postsRepository.findByContentContaining(searchText));
	    List<User> users = userRepository.findAll();
	    for(User user:users) {
	    	if(user.getName().equals(searchText)) {
	    		results.addAll(user.getPosts());
	    	}
	    }
//	    results.addAll(postsRepository.findByAuthorContaining(searchText));
	    
	    return results;
	}
}
