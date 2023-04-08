package com.blogsite.entity;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	@NotBlank(message="Name is required!")
	@Size(min=2,max=35,message="min 2 and max 35 chars are required!")
    private String name;
    @Column(unique=true) // email is unique
    private String email;
    private String password;
    private String image;
    private String description;
    private boolean enabled;
    private String role;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")
    private List<Blog> blogs = new ArrayList<Blog>();

    public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public User(int id, String name, String email, String password, String image, String description, boolean enabled, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.description = description;
        this.enabled = enabled;
        this.role = role;
    }

    @Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", image=" + image
				+ ", description=" + description + ", enabled=" + enabled + ", role=" + role + ", blogs=" + blogs + "]";
	}

	public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getRole() {
        return role;
    }

	public User() {
		super();
	}
}

