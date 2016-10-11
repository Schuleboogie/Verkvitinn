package project.persistence.entities;

import javax.persistence.*;
import project.persistence.entities.User;

import java.util.Collections;
import java.util.List;

/**
 * The class for the Postit Note itself.
 * The system generates a table schema based on this class for this entity.
 * Be sure to annotate any entities you have with the @Entity annotation.
 */
@Entity
@Table(name = "projects") // If you want to specify a table name, you can do so here
public class Project {
	// Declare that this attribute is the id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String admin;
	private String description;
	private String location;
	private String tools;
	private String estTime;
	private String[] workers;

	public Project() {

	}
	public Project(String name, String admin, String description, String location, String tools, String estTime, String[] workers) {
		this.name = name;
		this.admin = admin;
		this.description = description;
		this.location = location;
		this.tools = tools;
		this.estTime = estTime;
		this.workers = workers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdmin() {
		return this.admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTools() {
		return this.tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	public String getEstTime() {
		return this.estTime;
	}

	public void setEstTime(String estTime) {
		this.estTime = estTime;
	}

	public String[] getWorkers() {
		return this.workers;
	}

	public void setWorkers(String[] workers) {
		this.workers = workers;
	}
}
