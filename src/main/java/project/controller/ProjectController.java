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
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/projects", method = RequestMethod.GET)
public class ProjectController {
	// Instance Variables
	UserService userService;
	ProjectService projectService;

	// Dependency Injection
	@Autowired
	public ProjectController(UserService userService, ProjectService projectService) {
		this.userService = userService;
		this.projectService = projectService;
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String projects() {
		return "redirect:/";
	}
	// User profile
	@RequestMapping(value = "{projectId}", method = RequestMethod.GET)
	public String project(@PathVariable Long projectId, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				boolean isWorker = false;
				// Find further information about workers
				String[] workerList = foundProject.getWorkers();
				List<User> workers = new ArrayList<User>();
				for (int i = 0; i < workerList.length; i++) {
					User worker = userService.findByUsername(workerList[i]);
					// Check if user is worker on project
					if (user.getUsername().equals(worker.getUsername()))
						isWorker = true;
					workers.add(worker);
				}
				model.addAttribute("aworkers", workers);
				// Add project info
				model.addAttribute("project", foundProject);

				// Identify if logged in user is the same as owner of project OR
				// logged in user is a worker on project
				if (user.getUsername().equals(foundProject.getAdmin())) {
					// User is admin of project
				}
				else if (isWorker) {
					// User is a worker on project
				}
				else return "redirect:/"; // User is not a member of project
				// Return project if user is member of project
				return "project";
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}
}
