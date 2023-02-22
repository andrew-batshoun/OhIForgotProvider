package com.org.OhIForgotProvider.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.org.OhIForgotProvider.model.Task;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;

	private String email;

	private String username;

	private String password;

	private List<Task> tasks;
}
