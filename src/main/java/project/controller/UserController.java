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
import project.service.ProjectService;
import project.persistence.entities.User;
import project.persistence.entities.Project;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/home")
public class UserController {
	// Instance Variables
	UserService userService;
	ProjectService projectService;

	// Dependency Injection
	@Autowired
	public UserController(UserService userService, ProjectService projectService) {
		this.userService = userService;
		this.projectService = projectService;
	}

	// User home page
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String userHome(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			model.addAttribute("role", user.getRole());
			if (user.getRole().equals("admin")) {
				// Add projects if user is admin
				model.addAttribute("projects", projectService.findByAdmin(user.getUsername()));
			}
			else {
				// Add projects worker is assigned to if user is worker
				model.addAttribute("projects", projectService.findByWorker(user.getUsername()));
			}
			return "home";
		}
		else return "redirect:/";
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
