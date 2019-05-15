package com.bridgelabz.fundoo.note.model;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.bridgelabz.fundoo.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.hash.HashCode;


@Entity
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id ;
	@NotNull
	private String title;
	private String discription;
	private String color;
	private boolean isPinned;
	private boolean isArchive;
	private boolean isTrash;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	private Date reminder;

	@ManyToOne
	@JoinColumn(name="User_Id")
	private User user;
	private boolean active;

    //@JsonIgnore
	@ManyToMany(mappedBy="Notes")
	private Set<Label> labels;
    
	//@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "note_user", joinColumns = @JoinColumn(name = "noteId"), 
	inverseJoinColumns = @JoinColumn(name = "userId"))
	private List<User> collaboratedUsers;
	//
	/**
	 * default constructor
	 */
	public Note() {
		super();
	}
	
	public List<User> getCollaboratedUsers() {
		return collaboratedUsers;
	}
	public void setCollaboratedUsers(List<User> collaboratedUsers) {
		this.collaboratedUsers = collaboratedUsers;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isInPinned() {
		return isPinned;
	}
	public void setInPinned(boolean inPinned) {
		this.isPinned = inPinned;
	}
	public boolean isArchive() {
		return isArchive;
	}
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}
	public boolean isTrash() {
		return isTrash;
	}
	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public LocalDateTime getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public boolean isPinned() {
		return isPinned;
	}
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean addLabel(Label label) {
		if(labels == null)
			labels = new HashSet<>();
		return labels.add(label);
	}
	public boolean removeLabel(Label label) {
		return labels.remove(label);
	}
	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", discription=" + discription + ", color=" + color
				+ ", inPinned=" + isPinned + ", isArchive=" + isArchive + ", isTrash=" + isTrash + ", createDate="
				+ createDate + ", modifyDate=" + modifyDate + "]";
	}
	@Override
	public int hashCode() {
		return (title + discription).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Note) {
			Note note = (Note) obj;
			if (this.title.equals(note.title) && this.discription.equals(note.discription))
				return true;
			else
				return false;
		}
		throw new IllegalArgumentException("Can't compare non-Note objects");
	}
	public Date getReminder() {
		return reminder;
	}
	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}
	public Set<Label> getLabels() {
		return labels;
	}
	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}
	

  }
