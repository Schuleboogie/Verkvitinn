package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.User;
import project.persistence.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;

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
		System.out.println(newUser);
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
}
