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
import org.springframework.boot.autoconfigure.web.ErrorController;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@Controller
public class IndexController implements ErrorController{
	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public String error(Model model, HttpServletResponse response) {
		String s= String.valueOf(response.getStatus());
		if(s.matches("5..")){
			return "errors";
		}
		else return "redirect:/";
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
