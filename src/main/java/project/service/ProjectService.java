package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.Project;
import project.persistence.repositories.ProjectRepository;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProjectService {
	// Project repository
	ProjectRepository projects;

	// Dependency Injection
	@Autowired
	public ProjectService(ProjectRepository projects) {
		this.projects = projects;
	}

	// Create project
	public Project create(Project newProject) {
		// Save project
		return projects.save(newProject);
	}

	// Find projects by admin
	public List<Project> findByAdmin(String admin) {
		return projects.findByAdmin(admin);
	}

	// Find project by id
	public Project findOne(Long id) {
		return projects.findOne(id);
	}

	// Delete project by id
	public void delete(Project project) {
		projects.delete(project);
	}

	// Find projects that worker is assigned to
	public List<Project> findByWorker(String worker) {
		List<Project> allProjects = projects.findAll();
		List<Project> workerProjects = new ArrayList<Project>();

		for (Project project : allProjects) {
			// Check if worker is assigned to project
			String[] workers = project.getWorkers();
			for (int i = 0; i < workers.length; i++) {
				if (workers[i].equals(worker))
					workerProjects.add(project);
			}
		}
		return workerProjects;
	}
}
