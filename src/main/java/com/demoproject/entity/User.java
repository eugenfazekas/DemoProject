package com.demoproject.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity				
@Table(name="users",uniqueConstraints=@UniqueConstraint(columnNames={"email"}))
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min=1,max=16,message="firstName length shoud betwenn 1 to 10")
	@Pattern(regexp="\\b([^'\"/\\\\]+[A-Za-z0-9_.-])\\b")
	private String firstName;
	
	@NotNull
	@Size(min=1,max=16,message="LastName length shoud betwenn 1 to 10")
	@Pattern(regexp="\\b([^'\"/\\\\]+[A-Za-z0-9_.-])\\b")
	private String lastName;
	
	@NotEmpty(message="Email field should not be empty")
	@Email(regexp="\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b",message="Invalid email address")
	@Column(unique=true)
	@Size(min=1,max=40,message="Email length shoud betwenn 1 to 10")
	private String email;
	
	@Length(min = 2, message = "The field must be at least 5 characters")
	@Column(nullable=false)
	private String password;
	
	private String activation;
	
	private Boolean enabled;

	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinTable( 
		name = "users_roles", 
		joinColumns = {@JoinColumn(name="user_id")}, 
		inverseJoinColumns = {@JoinColumn(name="role_id")}  
			)
	
	private Set<Role> roles = new HashSet<Role>();
	
	public User() {
			}

//	public User(String firstName, String lastName, String email, String password) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void addRoles(String roleName) {
		if (this.roles == null || this.roles.isEmpty()) 
			this.roles = new HashSet<>();
		this.roles.add(new Role(roleName));
	}
	

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
	}
	
}
