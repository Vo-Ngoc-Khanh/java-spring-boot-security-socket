package com.project.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;

    private String description;

    private String content;

    private boolean active;
    
    @Column(name = "delete_post")
    private boolean deletePost;

	public Post(Category category, String title, String description, String content, boolean active, boolean deletePost) {
		super();
		this.category = category;
		this.title = title;
		this.description = description;
		this.content = content;
		this.active = active;
		this.deletePost = deletePost;
	}



   
}

