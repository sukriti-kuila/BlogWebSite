package com.blogsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blogsite.dao.UserRepository;
import com.blogsite.entity.*;
import com.blogsite.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title","Home | Sukriti Kuila Blog");
		return "home";
	}
	@GetMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","About | Sukriti Kuila Blog");
		return "about";
	}
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title","Register | Sukriti Kuila Blog");
		model.addAttribute("user", new User());
		
		return "signup";
	}
	
	// user registration
	@RequestMapping(value="/do_register", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult result1, 
			@RequestParam(value="agreement", defaultValue = "false") boolean agreement,
			Model model,
			HttpSession session) {
			try {
				if(!agreement) {
					System.out.println("You haven't agreed T&C");
					throw new Exception("You haven't agreed T&C");
				}
				if(result1.hasErrors()) {
					System.out.println("ERROR "+result1.toString());
					model.addAttribute("user",user);
					return "signup";
				}
				user.setEnabled(true);
				user.setRole("ROLE_USER");
				user.setImage("deafult.png");
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				
				User result = this.userRepository.save(user);
				
				model.addAttribute("user",new User());
				session.setAttribute("message", new Message("Successfully Registered","alert-success"));
				
				System.out.println("Agree "+agreement);
				System.out.println(user);
				
			}
			catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("user",user);
				session.setAttribute("message", new Message("Something Went Wrong"+e.getMessage(), "alert-danger"));
				return "signup";
			}
		return "signup";
	}
	
	//handler for custom login
	@GetMapping("/signin")
	public String customLogIn(Model model) {
		model.addAttribute("title", "Log In");
		return "login";
	}
}
