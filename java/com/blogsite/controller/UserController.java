package com.blogsite.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blogsite.dao.BlogRepository;
import com.blogsite.dao.UserRepository;
import com.blogsite.entity.Blog;
import com.blogsite.entity.User;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlogRepository blogRepository;
	
	// This method will run everytime
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println("USERNAME "+username);
//		get the user using user name(in this project email is user name)
		User user = userRepository.getUserByUserName(username);
//		System.out.println("User "+user);
		model.addAttribute("user",user);
		
	}
	@RequestMapping("/index")
	public String dashboard(Model model) {
		model.addAttribute("title","Home | User Dashboard");
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add-blog")
	public String openAddBlogForm(Model model)
	{
		model.addAttribute("title","Add Blog | User Dashboard");
		model.addAttribute("blog",new Blog());
		
		return "normal/add_blog_form";
	}
	
	@PostMapping("/process-blog")
	public String processBlog(@ModelAttribute("blog") @Valid Blog blog, 
			BindingResult bindingResult, 
			@RequestParam("image") MultipartFile file,
			Principal principal, Model model) {
//		System.out.println(blog);
		model.addAttribute("title","Add Blog | User Dashboard");
		try {
			
		String name = principal.getName();
		User user = this.userRepository.getUserByUserName(name);
		
//		processing and uploading file
		if(file.isEmpty())
		{
			System.out.println("Empty Image File");
		}
		else
		{
//			 update the file to folder and update the name to blog
			blog.setImage(file.getOriginalFilename());
			File saveFile = new ClassPathResource("static/img").getFile();
			
			Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("Image is Uploaded");
		}
		
		blog.setUser(user);
		user.getBlogs().add(blog);
		
		this.userRepository.save(user);
		System.out.println("Added to DB");
		}
		catch(Exception e)
		{
			System.out.println("ERROR "+e.getMessage());
		}
		
		
		return "normal/add_blog_form";
	}
	
// 	show contact handler
	@GetMapping("/show-blogs/{page}")
	public String showBlogs(@PathVariable("page") Integer page, Model model, Principal principal)
	{
		model.addAttribute("title","View Blogs");
		String username = principal.getName(); // email will be stored in username
		User user = this.userRepository.getUserByUserName(username);
//		List<Blog> blogs = user.getBlogs();
		PageRequest pageable = PageRequest.of(page, 8); //(currentPage, content per page)
		Page<Blog> blogs = this.blogRepository.findBlogsByUser(user.getId(), pageable);
		model.addAttribute("blogs",blogs);
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpage",blogs.getTotalPages()); // if we have 16 contents then getTotalPages() will return 10/8 = 2 pages
		return "normal/show_blogs";
	}
}
