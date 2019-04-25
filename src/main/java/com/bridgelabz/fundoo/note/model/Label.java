package com.bridgelabz.fundoo.note.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.bridgelabz.fundoo.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Label {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long lableId;
	private String name;
	private LocalDateTime createStamp;

	private LocalDateTime modifiedStamp;
	@ManyToOne
	@JoinColumn(name="User_Id")
	private User user;

	
	//@ManyToMany(mappedBy="labels")//,cascade=CascadeType.ALL)
	@JsonIgnore
	@ManyToMany()
	@JoinTable(name = "note_label", joinColumns = @JoinColumn(name = "labelId"), 
	inverseJoinColumns = @JoinColumn(name = "id"))
	private Set<Note> Notes;


	public Label() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getLableId() {
		return lableId;
	}
	public void setLableId(Long lableId) {
		this.lableId = lableId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getCreateStamp() {
		return createStamp;
	}
	public void setCreateStamp(LocalDateTime createStamp) {
		this.createStamp = createStamp;
	}
	public LocalDateTime getModifiedStamp() {
		return modifiedStamp;
	}
	public void setModifiedStamp(LocalDateTime modifiedStamp) {
		this.modifiedStamp = modifiedStamp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Label [lableId=" + lableId + ", name=" + name + ", createStamp=" + createStamp + ", modifiedStamp="
				+ modifiedStamp + ", user=" + user + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Label) {
			Label label = (Label) obj;
			if (this.name.equals(label.getName()))
				return true;
			else
				return false;
		}
		throw new IllegalArgumentException("Can't compare non-Label objects");
	}

	@Override
	public int hashCode() {
		return (name).hashCode();
	}
	public Set<Note> getNotes() {
		return Notes;
	}
	public void setNotes(Set<Note> notes) {
		Notes = notes;
	}
	public void setLableId(long lableId) {
		this.lableId = lableId;
	}
	
}
