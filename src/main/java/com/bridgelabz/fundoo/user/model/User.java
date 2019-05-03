package com.bridgelabz.fundoo.user.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	@NotNull(message="please fill the firstname")
	private String firstName;
	
	@NotNull(message="please fill the lastname")
    private String lastName;
	@Column(unique = true, nullable=false)
	@NotNull(message="please fill the email")
	private String email;
	
	@Pattern(regexp = "[0-9]{10}", message = "Number Should Only Be Digit And 10 digit only")
	@NotNull(message="please fill the mobilenumber")
	private String mobileNumber;
	
	@NotNull(message="please fill the password")
	private String password;
	
    private LocalDate registeredDate;
    private LocalDate modifiedDate;
    private boolean isVerification;

	private String Image;
    
    @JsonIgnore
	@ManyToMany(mappedBy="collaboratedUsers")
	private List<Note> collaboratedNotes;

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

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
	public List<Note> getCollaboratedNotes() {
		return collaboratedNotes;
	}

	public void setCollaboratedNotes(List<Note> collaboratedNotes) {
		this.collaboratedNotes = collaboratedNotes;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", password=" + password + ", registeredDate=" + registeredDate
				+ ", modifiedDate=" + modifiedDate + ", isVerification=" + isVerification + "]";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

	public boolean isVerification() {
		return isVerification;
	}

	public void setVerification(boolean isVerification) {
		this.isVerification = isVerification;
	}

	
    
}
