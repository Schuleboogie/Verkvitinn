package project.persistence.entities;

import javax.persistence.*;

@Entity
@Table(name = "messages") // If you want to specify a table name, you can do so here
public class Message {
	// Declare that this attribute is the id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long projectId;
	private Date timestamp;
	// The message's author
	private User author;
	// Is owner admin?
	private boolean admin;
	// Is owner head worker?
	private boolean headWorker;
	// The message's contents
	private String message;

	public Message() {
	}
	public Message(Long projectId, String password, String role, String name) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.name = name;
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
}
