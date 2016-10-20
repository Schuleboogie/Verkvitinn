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
	// User profile
	@RequestMapping(value = "{username}", method = RequestMethod.GET)
	public String profile(@PathVariable String username, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			model.addAttribute("useri", userService.findByUsername(username));
			return "profile";
		}
		else return "redirect:/";
	}
}
