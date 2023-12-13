package com.chedly.miniprojet.Entyties;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Entity
@Builder

public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idImage;

	private String name;
	private String type;

	// @Column( name = "IMAGE" , length = 4048576 )
	@Column(name = "IMAGE", columnDefinition = "LONGBLOB", length = 10485760) // Set an appropriate length
	@Lob
	private byte[] image;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	@JsonIgnore
	private Employee employee;

	public Image() {
	}

	public Image(Long idImage, String name, String type, byte[] image, Employee employee) {
		this.idImage = idImage;
		this.name = name;
		this.type = type;
		this.image = image;
		this.employee = employee;
	}

	public Long getIdImage() {
		return idImage;
	}

	public void setIdImage(Long idImage) {
		this.idImage = idImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}