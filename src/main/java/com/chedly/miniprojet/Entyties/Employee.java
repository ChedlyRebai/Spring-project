package com.chedly.miniprojet.Entyties;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @NotBlank(message = "First name is mandatory")
	@Size(max = 50, message = "First name must be less than 50 characters")
	private String firstName;

	// @NotBlank(message = "Last name is mandatory")
	@Size(max = 50, message = "Last name must be less than 50 characters")
	private String lastName;

	private Double salary;

	// @NotBlank(message = "Position is mandatory")
	private String position;

	// @NotBlank(message = "Country is mandatory")
	private String country;

	// @NotBlank(message = "Phone number is mandatory")
	// @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15
	// characters")
	private String phoneNumber;

	// @Email(message = "Email should be valid")
	private String email;

	// @NotBlank(message = "Password is mandatory")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private Department department;

	// @OneToOne
	// private Image image;

	@OneToMany(mappedBy = "employee")
	private List<Image> images;

	private String imagePath;

	@ElementCollection
	@CollectionTable(name = "employee_avatars", joinColumns = @JoinColumn(name = "employee_id"))
	@Column(name = "employee_url")
	private List<String> avatarUrls = new ArrayList<>();

	public Employee(Long id, String firstName,
			String lastName, Double salary,
			String position, String country, String phoneNumber, String email,
			String password, Department department,
			Image image) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.position = position;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.department = department;

	}

	public Employee() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public String getPosition() {
		return position;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	// public Employee() {
	// super();
	// }

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSalary() {
		return salary;
	}

	public void addAvatarUrl(String avatarUrl) {
		this.avatarUrls.add(avatarUrl);
	}

	public List<String> getAvatarUrls() {
		return avatarUrls;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public void setAvatarUrls(List<String> avatarUrls) {
		this.avatarUrls = avatarUrls;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
}
