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
import project.persistence.entities.Message;
import project.persistence.entities.Milestone;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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
				// Find further information about workers
				boolean isWorker = false;
				String[] workerList = foundProject.getWorkers();
				String[] headWorkerList = foundProject.getHeadWorkers();
				if (workerList != null) {
					List<User> workers = new ArrayList<User>();
					for (int i = 0; i < workerList.length; i++) {
						User worker = userService.findByUsername(workerList[i]);
						// Check if user is worker on project
						if (user.getUsername().equals(worker.getUsername()))
							isWorker = true;
						// Check if user is head worker
						if (headWorkerList != null) {
							for (int j = 0; j < headWorkerList.length; j++) {
								// Check if worker is head worker on project
								if (worker.getUsername().equals(headWorkerList[j]))
									worker.setHeadWorker(true);
							}
						}
						workers.add(worker);
					}
					model.addAttribute("aworkers", workers);
				}
				// Check if user is head worker
				if (headWorkerList != null) {
					for (int a = 0; a < headWorkerList.length; a++) {
						// Check if user is head worker on project
						if (user.getUsername().equals(headWorkerList[a]))
							model.addAttribute("isHeadWorker", true);
					}
				}
				// Add project info
				// Find admin name
				model.addAttribute("projectAdmin", userService.findByUsername(foundProject.getAdmin()).getName());
				model.addAttribute("project", foundProject);

				//Add message info
				model.addAttribute("messages", messageService.findByProjectId(projectId));
				// Add milestones
				model.addAttribute("milestones", projectService.findMilestones(projectId));
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

	// Add new message to the project
	@RequestMapping(value = "{projectId}", method = RequestMethod.POST)
	public String project(@PathVariable Long projectId, @ModelAttribute("newMessage") Message newMessage, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				User user = (User) session.getAttribute("user");
				model.addAttribute("user", user);
				// Add additional message information
				// Check if user is head worker
				newMessage.setHeadWorker(false);
				String[] headWorkerList = foundProject.getHeadWorkers();
				if (headWorkerList != null) {
					for (int a = 0; a < headWorkerList.length; a++) {
						// Check if user is head worker on project
						if (user.getUsername().equals(headWorkerList[a]))
							newMessage.setHeadWorker(true);
					}
				}
				newMessage.setProjectId(projectId);
				newMessage.setTimestamp(new Date());
				newMessage.setAuthor(user.getUsername());
				newMessage.setAdmin(user.getRole().equals("admin"));
				Message createMessage = messageService.create(newMessage);
				if (createMessage != null) {
					return "redirect:/projects/" + projectId;
				}
				else {
					model.addAttribute("error", "Error submitting message");
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
							// Check if user is head worker
							if (headWorkerList != null) {
								for (int j = 0; j < headWorkerList.length; j++) {
									// Check if worker is head worker on project
									if (worker.getUsername().equals(headWorkerList[i]))
										worker.setHeadWorker(true);
								}
							}
							workers.add(worker);
						}
						model.addAttribute("aworkers", workers);
					}
					// Check if user is head worker
					if (headWorkerList != null) {
						for (int a = 0; a < headWorkerList.length; a++) {
							// Check if user is head worker on project
							if (user.getUsername().equals(headWorkerList[a]))
								model.addAttribute("isHeadWorker", true);
						}
					}
					// Add project info
					// Find admin name
					model.addAttribute("projectAdmin", userService.findByUsername(foundProject.getAdmin()).getName());
					model.addAttribute("project", foundProject);

					//Add message info
					model.addAttribute("messages", messageService.findByProjectId(projectId));
					// Add milestones
					model.addAttribute("milestones", projectService.findMilestones(projectId));
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
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}

	// Delete message from the project
	@RequestMapping(value = "{projectId}/messages/delete/{messageId}", method = RequestMethod.GET)
	public String project(@PathVariable Long projectId, @PathVariable Long messageId, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			// Find project and message
			Message message = messageService.findOne(messageId);
			Project project = projectService.findOne(projectId);
			if (message != null && project != null) {
				// Check if user is author of message and message belongs to project
				if (user.getUsername().equals(message.getAuthor()) && project.getId().equals(projectId)) {
					messageService.delete(message);
					return "redirect:/projects/" + projectId;
				}
				else return "redirect:/";
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
				// Set status of all new projects to not-started
				newProject.setStatus("not-started");
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
					boolean isWorker = false;
					String[] workerList = foundProject.getWorkers();
					String[] headWorkerList = foundProject.getHeadWorkers();
					if (workerList != null) {
						List<User> workers = new ArrayList<User>();
						for (int i = 0; i < workerList.length; i++) {
							User worker = userService.findByUsername(workerList[i]);
							// Check if user is worker on project
							if (user.getUsername().equals(worker.getUsername()))
								isWorker = true;
							// Check if user is head worker
							if (headWorkerList != null) {
								for (int j = 0; j < headWorkerList.length; j++) {
									// Check if worker is head worker on project
									if (worker.getUsername().equals(headWorkerList[i]))
										worker.setHeadWorker(true);
								}
							}
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
				// Transform information from previous project to edited project
				Project previousProject = projectService.findOne(projectId);
				editedProject.setStartTime(previousProject.getStartTime());
				editedProject.setFinishTime(previousProject.getFinishTime());
				editedProject.setStatus(previousProject.getStatus());

				// Save project
				Project newlyEditedProject = projectService.create(editedProject);

				if (newlyEditedProject != null) {
					return "redirect:/projects/" + newlyEditedProject.getId();
				}
				else {
					model.addAttribute("user", user);
					Project foundProject = projectService.findOne(projectId);
					if (foundProject != null) {
						// Find further information about workers
						boolean isWorker = false;
						String[] workerList = foundProject.getWorkers();
						String[] headWorkerList = foundProject.getHeadWorkers();
						if (workerList != null) {
							List<User> workers = new ArrayList<User>();
							for (int i = 0; i < workerList.length; i++) {
								User worker = userService.findByUsername(workerList[i]);
								// Check if user is worker on project
								if (user.getUsername().equals(worker.getUsername()))
									isWorker = true;
								// Check if user is head worker
								if (headWorkerList != null) {
									for (int j = 0; j < headWorkerList.length; j++) {
										// Check if worker is head worker on project
										if (worker.getUsername().equals(headWorkerList[i]))
											worker.setHeadWorker(true);
									}
								}
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

	// Set milestone page
	@RequestMapping(value = "{projectId}/milestone", method = RequestMethod.GET)
	public String setMilestone(@PathVariable Long projectId, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				// Set project info
				model.addAttribute("project", foundProject);
				// Check if user is head worker on project
				String[] headWorkerList = foundProject.getHeadWorkers();
				for (int i = 0; i < headWorkerList.length; i++) {
					if (user.getUsername().equals(headWorkerList[i])) {
						return "milestone";
					}
				}
				return "redirect:/";
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}
	// Post milestone
	@RequestMapping(value = "{projectId}/milestone", method = RequestMethod.POST)
	public String postMilestone(@PathVariable Long projectId, @ModelAttribute("newMilestone") Milestone newMilestone, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				// Set project info
				model.addAttribute("project", foundProject);
				// Check if user is head worker on project
				String[] headWorkerList = foundProject.getHeadWorkers();
				for (int i = 0; i < headWorkerList.length; i++) {
					if (user.getUsername().equals(headWorkerList[i])) {
						// Set timestamp and project id
						newMilestone.setTimestamp(new Date());
						newMilestone.setProjectId(projectId);
						// Create milestone
						Milestone createdMilestone = projectService.setMilestone(newMilestone);
						if (createdMilestone != null) {
							return "redirect:/projects/" + projectId;
						}
						else {
							model.addAttribute("error", "Error submitting milestone");
							return "milestone";
						}
					}
				}
				return "redirect:/";
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}

	// Start project
	@RequestMapping(value = "{projectId}/start", method = RequestMethod.GET)
	public String startProject(@PathVariable Long projectId, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				// Set project info
				model.addAttribute("project", foundProject);
				// Check if user is head worker on project
				String[] headWorkerList = foundProject.getHeadWorkers();
				for (int i = 0; i < headWorkerList.length; i++) {
					if (user.getUsername().equals(headWorkerList[i])) {
						// Check if project has status
						if (foundProject.getStatus() != null) {
							// Check if project is not already started
							if (foundProject.getStatus().equals("not-started")) {
								boolean started = projectService.startProject(projectId);
							}
						}
						else {
							boolean started = projectService.startProject(projectId);
						}
						return "redirect:/projects/" + projectId;
					}
				}
				return "redirect:/";
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}
	// Finish project
	@RequestMapping(value = "{projectId}/finish", method = RequestMethod.GET)
	public String finishProject(@PathVariable Long projectId, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			Project foundProject = projectService.findOne(projectId);
			if (foundProject != null) {
				// Set project info
				model.addAttribute("project", foundProject);
				// Check if user is head worker on project
				String[] headWorkerList = foundProject.getHeadWorkers();
				for (int i = 0; i < headWorkerList.length; i++) {
					if (user.getUsername().equals(headWorkerList[i])) {
						boolean finished = projectService.finishProject(projectId);
					}
				}
				return "redirect:/projects/" + projectId;
			}
			else return "redirect:/";
		}
		else return "redirect:/";
	}
}
