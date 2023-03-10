package com.org.OhIForgotProvider.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name ="tasks")
public class Task { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name="description")
	private String description;
	
	
	@Column(name="due_date")
	private LocalDate dueDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	

}
