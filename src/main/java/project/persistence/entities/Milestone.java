package project.persistence.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "milestones") // If you want to specify a table name, you can do so here
public class Milestone {
	// Declare that this attribute is the id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long projectId;
	private Date timestamp;
	// The milestone title
	private String title;

	public Milestone() {
	}
	public Milestone(Long projectId, Date timestamp, String title) {
		this.projectId = projectId;
		this.timestamp = timestamp;
		this.title = title;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
