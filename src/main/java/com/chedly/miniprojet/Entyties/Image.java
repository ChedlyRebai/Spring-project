package com.chedly.miniprojet.Entyties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idImage;
	private String name;
	private String type;
	@Column(name = "IMAGE", length = 4048576)
	@Lob
	private byte[] image;
	@OneToOne
	private Employee employee;
	
	
	
	
	public Image() {
		super();
	}
	
	public Image(Long idImage, String name, String type, byte[] image, Employee employee) {
		super();
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
	/*public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	
}