package com.org.OhIForgotProvider.service;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.org.OhIForgotProvider.model.Task;
import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.respository.UserRepository;

@Test
public class UserServiceTest  extends AbstractTestNGSpringContextTests{
	@Mock
	UserRepository mockDao; 
	
	@InjectMocks
	UserServiceImpl userService; 
	
	@BeforeMethod 
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void whenListIsCalled_ShowsListOfUsers() {
		User user1 = new User();
		user1.setEmail("user1@email.com");
		user1.setUsername("user1");
		user1.setPassword("password1");
		
		User user2 = new User();
		user2.setEmail("user2@email.com");
		user2.setUsername("user2");
		user2.setPassword("password2");
		
		User user3 = new User();
		user3.setEmail("user3@email.com");
		user3.setUsername("user3");
		user3.setPassword("password3");
		
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		when(mockDao.findAll()).thenReturn(users);
		
		List<User> mockUsers = userService.findAllUsers();
		
		assertEquals(mockUsers, users);
		
		verify(mockDao, times(1)).findAll();
		
	}
	
	@Test 
	public void whenRetrievingList_ShowsNoUsers() {
		List<User> emptyUsers = new ArrayList<>();
		
		when(mockDao.findAll()).thenReturn(emptyUsers);
		
		List<User> users = userService.findAllUsers();
		
		assertEquals(users, emptyUsers);
		
		verify(mockDao, atMost(2)).findAll();
	}
	
	@Test
	public void whenGivenId_returnsUser() {
		User user1 = new User();
		user1.setId(1L);
		user1.setEmail("user1@email.com");
		user1.setUsername("user1");
		user1.setPassword("password1");
		
		User user2 = new User();
		user2.setId(3L);
		user2.setEmail("user2@email.com");
		user2.setUsername("user2");
		user2.setPassword("password2");
		
		User user3 = new User();
		user3.setId(3L);
		user3.setEmail("user3@email.com");
		user3.setUsername("user3");
		user3.setPassword("password3");
		
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		when(mockDao.getById(2L)).thenReturn(user2);
		
		assertEquals(userService.findById(2L), user2);
		
		verify(mockDao).getById(2L);
		
	}
	
	@Test
	public void whenGivenId_returnsNull() {
		User user1 = new User();
		user1.setId(1L);
		user1.setEmail("user1@email.com");
		user1.setUsername("user1");
		user1.setPassword("password1");
		
		User user2 = new User();
		user2.setId(3L);
		user2.setEmail("user2@email.com");
		user2.setUsername("user2");
		user2.setPassword("password2");
		
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		
		
		when(mockDao.getById(3L)).thenThrow(new NullPointerException());
		
		assertThrows(NullPointerException.class ,() -> userService.findById(3L));
		
		
	}
	
	@Test
	public void whenSaveUser_returnUser() {
		User user1 = new User();
		user1.setId(1L);
		user1.setEmail("user1@email.com");
		user1.setUsername("user1");
		user1.setPassword("password1");
		
		when(mockDao.save(Mockito.any())).thenReturn(user1);
		
		User savedUser = userService.saveUser(user1);
		
		assertEquals(savedUser, user1);
		
		verify(mockDao).save(user1);
	}
	
	@Test
	public void emailIsEmpty_CannotSaveUser() {
		User user1 = new User();
		user1.setId(1L);
		user1.setEmail("user1@email.com");
		user1.setUsername("user1");
		user1.setPassword("password1");
		
		when(mockDao.save(Mockito.any())).thenThrow(new NullPointerException());
		
		
		assertThrows(NullPointerException.class, () -> userService.saveUser(user1));
		
	}
	
	@Test
	public void whenUpdatingUser_ReturnsUpdatedUser() {
		User user = new User();
		user.setId(1L);
		user.setEmail("user1@email.com");
		user.setUsername("user1");
		user.setPassword("password1");
		
		User updatedUser = new User();
		updatedUser.setId(1L);
		updatedUser.setEmail("updateuser@email.com");
		updatedUser.setUsername("updateUser");
		updatedUser.setPassword("updatePass");
		
		when(mockDao.save(Mockito.any())).thenReturn(user, updatedUser);
		when(mockDao.getById(Mockito.anyLong())).thenReturn(user);
		
		User savedUser = userService.saveUser(user);
		savedUser.setUsername(updatedUser.getUsername());
		savedUser.setEmail(updatedUser.getEmail());
		savedUser.setPassword(updatedUser.getPassword());
		
		User savedUserUpdate = userService.updateUser(user.getId(), savedUser);
		
		assertNotNull(savedUserUpdate);
		assertEquals(savedUser.getPassword(), "updatePass");
		
		
	}
	
	@Test
	public void whenUpdating_PasswordIsBlank_ReturnsNull() {
		User user = new User();
		user.setId(1L);
		user.setEmail("user1@email.com");
		user.setUsername("user1");
		user.setPassword("password1");
		
		User updatedUser = new User();
		updatedUser.setId(1L);
		updatedUser.setEmail("updateuser@email.com");
		updatedUser.setUsername("updateUser");
		updatedUser.setPassword("updatePass");
		
		when(mockDao.save(Mockito.any())).thenThrow(new NullPointerException());
		when(mockDao.getById(Mockito.anyLong())).thenReturn(user);
		
		User savedUser = new User();
		savedUser.setEmail(updatedUser.getEmail());
		savedUser.setUsername(updatedUser.getUsername());
		
		
		assertThrows(NullPointerException.class, () -> userService.updateUser(1L, savedUser));
		
	}
	
	@Test
	public void whenGivenId_DeleteUser(){
		User user = new User();
		user.setId(1L);
		user.setEmail("user1@email.com");
		user.setUsername("user1");
		user.setPassword("password1");
		
		when(mockDao.getById(user.getId())).thenReturn(user); 
		userService.deleteUser(user.getId());
		verify(mockDao).deleteById(user.getId());
		}
	
	@Test
	public void whenGivenUsername_ReturnsUser() {
		User user1 = new User();
		user1.setId(1L);
		user1.setEmail("user1@email.com");
		user1.setUsername("user1");
		user1.setPassword("password1");
		
		User user2 = new User();
		user2.setId(3L);
		user2.setEmail("user2@email.com");
		user2.setUsername("user2");
		user2.setPassword("password2");
		
		User user3 = new User();
		user3.setId(3L);
		user3.setEmail("user3@email.com");
		user3.setUsername("user3");
		user3.setPassword("password3");
		
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		when(mockDao.findByUsername("user2")).thenReturn(user2);
		
		assertEquals(userService.findbyName("user2"), user2);
		
		verify(mockDao).findByUsername("user2");
	}
	 
	@Test
	public void whenUsernameDoesnotExist_ReturnsNull() {
		
		User user1 = new User();
		user1.setId(1L);
		user1.setEmail("user1@email.com");
		user1.setUsername("user1");
		user1.setPassword("password1");
		
		User user2 = new User();
		user2.setId(3L);
		user2.setEmail("user2@email.com");
		user2.setUsername("user2");
		user2.setPassword("password2");
		
		when(mockDao.findByUsername("user3")).thenThrow(new NullPointerException());
		
		assertThrows(NullPointerException.class, () -> userService.findbyName("user3"));
		
		verify(mockDao).findByUsername("user3");
	}
	
}

