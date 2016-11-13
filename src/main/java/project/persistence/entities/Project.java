package project.persistence.entities;

import javax.persistence.*;
import project.persistence.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Date;

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
	// Project start and finish time
	private Date startTime;
	private Date finishTime;
	private String[] workers;
	// Project status, either not-started, in-progress or finished
	private String status;

	public Project() {
	}
	public Project(String name, String admin, String description, String location, String tools, String estTime, Date startTime, Date finishTime, String[] workers, String status) {
		this.name = name;
		this.admin = admin;
		this.description = description;
		this.location = location;
		this.tools = tools;
		this.estTime = estTime;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.workers = workers;
		this.status = status;
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

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String[] getWorkers() {
		return this.workers;
	}

	public void setWorkers(String[] workers) {
		this.workers = workers;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
