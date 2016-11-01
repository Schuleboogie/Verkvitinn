package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.User;
import project.persistence.entities.Project;
import project.persistence.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserService {
	// User repository
	UserRepository users;

	// Dependency Injection
	@Autowired
	public UserService(UserRepository users) {
		this.users = users;
	}

	// Authenticate user
	public boolean auth(User user) {
		User fetchedUser = users.findByUsername(user.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (fetchedUser != null) {
			return encoder.matches(user.getPassword(), fetchedUser.getPassword());
		}
		else return false;
	}

	// Register user
	public boolean register(User newUser) {
		String password = newUser.getPassword();
		// Encode password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode(password);
		newUser.setPassword(result);
		// Save user
		if (users.save(newUser) != null)
			return true;
		else return false;
	}

	// Find user by username
	public User findByUsername(String username) {
		return users.findByUsername(username);
	}

	// Find users by role
	public List<User> findByRole(String role) {
		return users.findByRole(role);
	}

	// Find workers not in project
	public List<User> findWorkersNotInProject(Project project) {
		String[] projectWorkers = project.getWorkers();
		List<User> allWorkers = this.findByRole("worker");
		// Return all workers if no workers on project
		if (projectWorkers == null)
			return allWorkers;

		List<User> availableWorkers = new ArrayList<User>();
		for (User worker: allWorkers) {
			// Check if worker is in project
			boolean inProject = false;
			for (int i = 0; i < projectWorkers.length; i++) {
				if (worker.getUsername().equals(projectWorkers[i]))
					inProject = true;
			}
			// Add worker to project if worker not in project
			if (!inProject)
				availableWorkers.add(worker);
		}
		return availableWorkers;
	}
}
