package com.blogsite.entity;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="BLOG")
public class Blog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
    private String title;
    private String image;
    @Column(length=1000)
    private String description;
    private String privacy;
    private String category;
    private String paid;
   
	@ManyToOne
    private User user;
	
	/*
	 * @CreationTimestamp
	 * 
	 * @Temporal(TemporalType.TIMESTAMP)
	 * 
	 * @Column(name = "created", nullable = false) private Date createDate;
	 * 
	 * @UpdateTimestamp
	 * 
	 * @Temporal(TemporalType.TIMESTAMP)
	 * 
	 * @Column(name = "modify_date", nullable = false) private Date modifyDate;
	 */
	
	// Constructor
    public Blog(int id, String title, String image, String description, String privacy, String category, String paid) {
		this.id = id;
		this.title = title; // d
		this.image = image;
		this.description = description; //d
		this.privacy = privacy; //d
		this.category = category;//d
		this.paid = paid;//d
	}
	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", image=" + image + ", description=" + description
				+ ", privacy=" + privacy + ", category=" + category + ", paid=" + paid + ", user=" + user + "]";
	}
	public Blog() {
    	super();
    }
  
 
    
    // Getters and setters
	 public String getPaid() {
			return paid;
	}
	public void setPaid(String paid) {
			this.paid = paid;
	}
		
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPrivacy() {
        return privacy;
    }
    
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
