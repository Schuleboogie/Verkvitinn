package project.persistence.entities;

import javax.persistence.*;
import java.util.Date;

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
	private String author;
	// Is owner admin?
	private boolean admin;
	// Is owner head worker?
	private boolean headWorker;
	// The message's contents
	private String message;

	public Message() {
	}
	public Message(Long projectId, Date timestamp, String author, boolean admin, boolean headWorker, String message) {
		this.projectId = projectId;
		this.timestamp = timestamp;
		this.author = author;
		this.admin = admin;
		this.headWorker = headWorker;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean getAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean getHeadWorker() {
		return this.headWorker;
	}

	public void setHeadWorker(boolean headWorker) {
		this.headWorker = headWorker;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
