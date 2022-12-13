package com.org.OhIForgotProvider.model;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name ="tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name="description")
	private String description;
	
	@Temporal(TemporalType.DATE)
	@Column(name="due_date")
	private Date dueDate;
	
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private User user;

	public Task() {
	}

	public Task(Long id, String description) {
		this.id = id;
		this.description = description;
//		this.user = user; 
		

	}

	public Task(Long id, String description, Date dueDate) {
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
//		this.user = user; 
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	

}
