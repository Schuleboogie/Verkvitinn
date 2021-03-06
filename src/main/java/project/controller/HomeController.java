package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;
import project.service.UserService;
import project.persistence.entities.User;

@Controller
@ControllerAdvice
public class HomeController {
	// Instance Variables
	UserService userService;

	// Dependency Injection
	@Autowired
	public HomeController(UserService userService) {
		this.userService = userService;
	}

	// Request mapping is the path that you want to map this method to
	// In this case, the mapping is the root "/", so when the project
	// is running and you enter "localhost:8080" into a browser, this
	// method is called
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpSession session, Model model) {
		// The string "Index" that is returned here is the name of the view
		// (the Index.jsp file) that is in the path /main/webapp/WEB-INF/jsp/
		// If you change "Index" to something else, be sure you have a .jsp
		// file that has the same name
		if (session.getAttribute("user") != null) {
			return "redirect:/home";
		}
		else {
			return "index";
		}
	}

	// Login
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, HttpSession session, Model model) {
		if (userService.auth(user)) {
			session.setAttribute("user", userService.findByUsername(user.getUsername()));
			return "redirect:/home";
		}
		else {
			model.addAttribute("error", "Error authenticating user");
			return "index";
		}
	}

	// Register page
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		return "register";
	}

	// Register post
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(@ModelAttribute("user") User newUser) {
		if (userService.register(newUser))
			return "redirect:/home";
		else return "register";
	}
}
