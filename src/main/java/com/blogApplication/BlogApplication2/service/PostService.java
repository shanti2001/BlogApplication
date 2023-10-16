package com.blogApplication.BlogApplication2.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.PostRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postsRepository;
	@Autowired
	private UserRepository userRepository;
	
	public Set<Post> findPostsByTitleOrContentOrAuthor(String searchText) {
		Set<Post> results = new HashSet<>();
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
