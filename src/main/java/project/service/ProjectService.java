package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.Project;
import project.persistence.entities.Milestone;
import project.persistence.repositories.ProjectRepository;
import project.persistence.repositories.MilestoneRepository;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@Service
public class ProjectService {
	// Project repository
	ProjectRepository projects;
	// Milestone repository
	MilestoneRepository milestones;

	// Dependency Injection
	@Autowired
	public ProjectService(ProjectRepository projects, MilestoneRepository milestones) {
		this.projects = projects;
		this.milestones = milestones;
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
			if (workers == null)
				continue;
			for (int i = 0; i < workers.length; i++) {
				if (workers[i].equals(worker))
					workerProjects.add(project);
			}
		}
		return workerProjects;
	}

	/* Milestones */
	// Set milestone
	public Milestone setMilestone(Milestone newMilestone) {
		return milestones.save(newMilestone);
	}
	// Find milestones associated with project with projectId
	public List<Milestone> findMilestones(Long projectId) {
		List<Milestone> foundMilestones = milestones.findByProjectId(projectId);
		// Sort milestones by timestamp
		Collections.sort(foundMilestones, new Comparator<Milestone>() {
			public int compare(Milestone m1, Milestone m2) {
				if (m1.getTimestamp() == null || m2.getTimestamp() == null)
					return 0;
				return m2.getTimestamp().compareTo(m1.getTimestamp());
			}
		});
		return foundMilestones;
	}

	// Start and finish project with id projectId
	public boolean startProject(Long projectId) {
		Project project = projects.findOne(projectId);
		if (project != null) {
			project.setStatus("in-progress");
			// Set start time
			project.setStartTime(new Date());
			Project updatedProject = projects.save(project);
			if (updatedProject != null)
				return true;
			else return false;
		}
		else return false;
	}
	public boolean finishProject(Long projectId) {
		Project project = projects.findOne(projectId);
		if (project != null) {
			project.setStatus("finished");
			// Set finish time
			project.setFinishTime(new Date());
			Project updatedProject = projects.save(project);
			if (updatedProject != null)
				return true;
			else return false;
		}
		else return false;
	}
}
