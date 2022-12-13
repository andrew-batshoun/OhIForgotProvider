package com.org.OhIForgotProvider;

//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

//import com.org.OhIForgotProvider.service.UserDetailsLoader;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
//	private UserDetailsLoader usersLoader;
//	
//	 public SecurityConfiguration(UserDetailsLoader usersLoader){
//	        this.usersLoader = usersLoader;
//	    }
//	 @Bean
//	    public PasswordEncoder passwordEncoder(){
//	        return new BCryptPasswordEncoder();
//	    }
//	 
//	 
//	 
//	 @Override
//	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	        auth
//	                .userDetailsService(usersLoader) //How to find users by their username
//	                .passwordEncoder(passwordEncoder()) //How to encode and verify passwords
//	                ;
//	    }
//	 
//	 @Bean
//	  @Override
//	  public AuthenticationManager authenticationManagerBean() throws Exception {
//	    return super.authenticationManagerBean();
//	  }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception{
//	        http.
	        
//	            /* Login Configuration*/
//	            .formLogin()
//	                .loginPage("/login")
//	                .defaultSuccessUrl("/tasks") // user's home page, it can be any URL
//	                .permitAll() //Anyone can go to the login page
//	            /*Logout configuration*/
//	            .and()
//	                .logout()
//	                .logoutSuccessUrl("/login?logout") //append a query string value
//	            /* Pages that can be view without having to log in */
//	            .and()
//	                .authorizeRequests()
//	                .antMatchers("/console", "/signup", "/" ) //anyone can see the home 
//	                .permitAll()
//	            /* Pages that require authentication */
//	            .and()
//	                .authorizeRequests()
//	                .antMatchers(
//	                		
//	                		 "/tasks/{id}", // only authenticated users can edit tasks
//	                		 "/profile/{id}", // only authenticated users can edit user profile
//	                         "/tasks" // only authenticated users can create tasks
//	                          
//	                )
//	                .authenticated();
	    	http.cors().disable();
	        http.csrf().disable();
	        http.headers().frameOptions().sameOrigin();
	    }
//
//	    
}
