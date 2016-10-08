package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;
import project.service.UserService;
import project.persistence.entities.User;

@Controller
@RequestMapping(value = "/users", method = RequestMethod.GET)
public class ProfileController {
	// Instance Variables
	UserService userService;

	// Dependency Injection
	@Autowired
	public ProfileController(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String profile() {
		return "redirect:/";
	}
	// User profile
	@RequestMapping(value = "{username}", method = RequestMethod.GET)
	public String profile(@PathVariable String username, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			// If requested user is same as logged in user, redirect to home page
			if (user.getUsername() == username)
				return "redirect:/home";
			else {
				model.addAttribute("user", userService.findByUsername(username));
				return "Profile";
			}
		}
		else return "redirect:/";
	}
}
