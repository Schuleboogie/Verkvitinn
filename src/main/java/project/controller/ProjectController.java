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
import project.service.MessageService;
import project.persistence.entities.User;
import project.persistence.entities.Project;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	// Instance Variables
	UserService userService;
	ProjectService projectService;
	MessageService messageService;

	// Dependency Injection
	@Autowired
	public ProjectController(UserService userService, ProjectService projectService, MessageService messageService) {
		this.userService = userService;
		this.projectService = projectService;
		this.messageService = messageService;
	}

	// Project info page
	@RequestMapping(value = "{projectId}", method = RequestMethod.GET)
	public String project(@PathVariable Long projectId, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				boolean isWorker = false;
				// Find further information about workers
				String[] workerList = foundProject.getWorkers();
				if (workerList != null) {
					List<User> workers = new ArrayList<User>();
					for (int i = 0; i < workerList.length; i++) {
						User worker = userService.findByUsername(workerList[i]);
						// Check if user is worker on project
						if (user.getUsername().equals(worker.getUsername()))
							isWorker = true;
						workers.add(worker);
					}
					model.addAttribute("aworkers", workers);
				}
				// Add project info
				// Find admin name
				model.addAttribute("projectAdmin", userService.findByUsername(foundProject.getAdmin()).getName());
				model.addAttribute("project", foundProject);

				//Add message info
				model.addAttribute("messages", messageService.findByProjectId(projectId));
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

	// Create new project
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createProject(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			// Identify if admin
			User user = (User) session.getAttribute("user");
			if (user.getRole().equals("admin")) {
				// Load project form
				model.addAttribute("user", user);
				// Add available workers
				model.addAttribute("workers", userService.findByRole("worker"));
				// Generate form
				return "create";
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}

	// Posting new project
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String postProject(@ModelAttribute("project") Project newProject, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			// Identify if admin and if admin is owner of project
			if (user.getRole().equals("admin") && user.getUsername().equals(newProject.getAdmin())) {
				Project createdProject = projectService.create(newProject);
				if (createdProject != null) {
					return "redirect:/projects/" + createdProject.getId();
				}
				else {
					model.addAttribute("user", user);
					// Add available workers
					model.addAttribute("workers", userService.findByRole("worker"));
					// Generate form again with error report
					model.addAttribute("error", "Error creating project");
					return "create";
				}
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}

	// Edit project
	@RequestMapping(value = "{projectId}/edit", method = RequestMethod.GET)
	public String editProject(@PathVariable Long projectId, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				// Identify if logged in user is owner of project
				if (user.getUsername().equals(foundProject.getAdmin())) {
					// Find further information about workers
					String[] workerList = foundProject.getWorkers();
					if (workerList != null) {
						List<User> workers = new ArrayList<User>();
						for (int i = 0; i < workerList.length; i++) {
							User worker = userService.findByUsername(workerList[i]);
							workers.add(worker);
						}
						model.addAttribute("aworkers", workers);
					}
					model.addAttribute("user", user);
					// Add available workers
					model.addAttribute("workers", userService.findWorkersNotInProject(foundProject));
					// Add project info
					model.addAttribute("nproject", foundProject);
					// Generate form
					return "create";
				}
				else return "redirect:/";
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}

	// Post edited project
	@RequestMapping(value = "{projectId}/edit", method = RequestMethod.POST)
	public String postEditedProject(@PathVariable Long projectId, @ModelAttribute("nproject") Project editedProject, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			// Identify if admin and if admin is owner of project
			if (user.getRole().equals("admin") && user.getUsername().equals(editedProject.getAdmin()) && projectId.equals(editedProject.getId())) {
				Project newlyEditedProject = projectService.create(editedProject);
				if (newlyEditedProject != null) {
					return "redirect:/projects/" + newlyEditedProject.getId();
				}
				else {
					model.addAttribute("user", user);
					Project foundProject = projectService.findOne(projectId);
					if (foundProject != null) {
						// Find further information about workers
						String[] workerList = foundProject.getWorkers();
						if (workerList != null) {
							List<User> workers = new ArrayList<User>();
							for (int i = 0; i < workerList.length; i++) {
								User worker = userService.findByUsername(workerList[i]);
								workers.add(worker);
							}
							model.addAttribute("aworkers", workers);
						}
						model.addAttribute("user", user);
						// Add available workers
						model.addAttribute("workers", userService.findWorkersNotInProject(foundProject));
						// Add project info
						model.addAttribute("nproject", foundProject);
						// Generate form again with error report
						model.addAttribute("error", "Error creating project");
						return "create";
					}
					else return "redirect:/";
				}
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}

	// Delete project
	@RequestMapping(value = "{projectId}/delete", method = RequestMethod.GET)
	public String deleteProject(@PathVariable Long projectId, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				// Identify if logged in user is the same as owner of project
				if (user.getUsername().equals(foundProject.getAdmin())) {
					projectService.delete(foundProject);
					return "redirect:/";
				}
				else return "redirect:/";
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}
}
