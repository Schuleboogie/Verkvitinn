package project.persistence.entities;

import javax.persistence.*;

@Entity
@Table(name = "users") // If you want to specify a table name, you can do so here
public class User {
	// Declare that this attribute is the id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	// The user's role, Admin or Worker
	private String role;
	// The user's name
	private String name;
	// Boolean for storing information about whether user is head worker on certain project
	private boolean headWorker;

	public User() {
	}
	public User(String username, String password, String role, String name, boolean headWorker) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.name = name;
		this.headWorker = headWorker;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getHeadWorker() {
		return this.headWorker;
	}

	public void setHeadWorker(boolean headWorker) {
		this.headWorker = headWorker;
	}
}
