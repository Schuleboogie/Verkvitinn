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
@RequestMapping(value = "/home", method = RequestMethod.GET)
public class UserController {
	// Instance Variables
	UserService userService;

	// Dependency Injection
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// User home page
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String userHome(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			return "Home";
		}
		else return "redirect:/";
	}

	// Create new project
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createProject(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			return "Create";
		}
		return "redirect:/";
	}

	// Logout
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		return "redirect:/";
	}
}
