package com.covid.auth.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.covid.auth.service.UserService;
import com.covid.auth.validator.UserForm;

/**
 * User request controller for different request.
 *
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public UserForm getUserForm() {
		return new UserForm();
	}

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new UserForm());
		return "registration";
	}

	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") @Valid UserForm user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(user);
		return "redirect:/registration?success";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("error", "Your email and password is invalid.");
		}

		if (logout != null) {
			model.addAttribute("message", "You have been logged out successfully.");
		}

		return "login";
	}

	@GetMapping({ "/", "/home" })
	public String home(Model model) {
		return "home";
	}
}