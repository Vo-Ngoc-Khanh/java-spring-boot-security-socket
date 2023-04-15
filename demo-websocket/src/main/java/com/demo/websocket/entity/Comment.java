package com.demo.websocket.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "from_user")
    private String fromUser;

    private String text;

    @Column(name = "created_at")
    private Date createdAt;

	public Comment(String fromUser, String text, Date createdAt) {
		super();
		this.fromUser = fromUser;
		this.text = text;
		this.createdAt = createdAt;
	}

    
}
