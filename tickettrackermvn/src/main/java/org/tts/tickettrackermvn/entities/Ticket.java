package org.tts.tickettrackermvn.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Short description is mandatory")
    @Column(name = "short_description")
    private String shortDescription;

    @NotBlank(message = "Content is mandatory")
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;

    @Transient
    private boolean isNew = true;

	public void setNew(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void setId(long id2) {
		// TODO Auto-generated method stub
		
	}

}