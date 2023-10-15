package com.blogApplication.BlogApplication2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogApplication.BlogApplication2.entity.*;
import com.blogApplication.BlogApplication2.repository.PostsRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;
import com.blogApplication.BlogApplication2.service.PostService;
import com.blogApplication.BlogApplication2.service.UserService;

import java.util.*;

@Controller
public class Controllers {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostsRepository postsRepository;
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/")
	public String home(Model model) {
		List<Posts> list = postsRepository.findAll();
		model.addAttribute("posts",list);
		return "home";
	}
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	@RequestMapping(value = "/register")
	public String register() {
		return "register";
	}
	@PostMapping("/register")
	public String check(@ModelAttribute User user) {
		userRepository.save(user);
		return "redirect:/login";
	}
	
//	@GetMapping("/userpage")
//	public String getUserPgae() {
//		return "userPage";
//	}
	
//	@RequestMapping("/userpage")
//	public String userPage(@RequestParam String email, String password,  Model model) {
////		User user = userService.findByEmail(email);
////		if(user!=null && user.getPassword().equals(password)) {
////			model.addAttribute("user",user);
////		}
//		
//		List<Posts> list = postsRepository.findAll();
//		model.addAttribute("posts",list);
//		return "userPage";
//	}
//	
	
	@RequestMapping("/userpage")
	public String userPage(Model model) {
		List<Posts> list = postsRepository.findAll();
//		System.out.println(list.get(0).getAuthor().getName());
		model.addAttribute("posts",list);
		return "userPage";
	}
	@RequestMapping("/userblog")
	public String userBlog( Model model) {
		List<Posts> list = postsRepository.findAll();
		model.addAttribute("posts",list);
		return "userBlog";
	}
	
	@GetMapping("/update/{id}")
	public String updatePostForm(@PathVariable int id, Model model) {
		Posts post = postsRepository.findById(id).orElse(null);
		model.addAttribute("post",post);
		return "updateBlog";
	}
	@PostMapping("/update")
	public String updateForm(@ModelAttribute("post") Posts post) {
		int postId = post.getId();
		Posts exitPost = postsRepository.findById(postId).orElse(null);
		if(exitPost != null) {
			exitPost.setContent(post.getContent());
			exitPost.setExcerpt(post.getExcerpt());
			exitPost.setTitle(post.getTitle());
			exitPost.setUpdated_at(new Date());
		}
		postsRepository.save(exitPost);
		return "redirect:/userpage"; 
	}
	
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable int id) {
		postsRepository.deleteById(id);
		return "redirect:/userpage";
	}

	@RequestMapping("/post")
	public String post() {
		return "newPost";
	}
	@PostMapping("/post")
	public String submit(@ModelAttribute Posts post) {
		if(post.getPublished_at()==null) {
			post.setPublished_at(new Date());
		}
		User author = new User();
		author.setEmail("shanti2001samanta@gmail.com");
		author.setPassword("1234");
		author.setName("shanti");
		
		post.setAuthor(author);
		
		post.setCreated_at(new Date());
		post.setUpdated_at(new Date());
		post.setIs_published(true);
		
		List<Posts> posts = new ArrayList();
		posts.add(post);
		author.setPosts(posts);
		
		userRepository.save(author);
		
		postsRepository.save(post);
		
		
		return "redirect:/userpage";
	}

	@GetMapping("/blogpost/{id}")
	public String blogPost(@PathVariable int id, Model model) {
		Posts post = postsRepository.findById(id).orElse(null);
		if(post==null) {
			return "redirect:/";
		}
		model.addAttribute("post",post);
		return "blogPost";
	}
	@GetMapping("/search")
	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query, Model model) {
		List<Posts> searchResults =	postService.findPostsByTitleOrContentOrAuthor(query);
		model.addAttribute("searchBlogs", searchResults);
		return "searchBlog";
	}
	
	
	@RequestMapping("/sortbypublisheddate")
	public String sortByPublishedDateTome(@RequestParam(name = "q", required = false) String sortBy, Model model) {
		List<Posts> list = new ArrayList();
		if(sortBy.equals("newest")) {
			list = postsRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedAt"));
		}
		if(sortBy.equals("oldest")) {
			list = postsRepository.findAll(Sort.by(Sort.Direction.ASC, "publishedAt"));
		}
		
		model.addAttribute("posts",list);
		return "home";
	}

}
