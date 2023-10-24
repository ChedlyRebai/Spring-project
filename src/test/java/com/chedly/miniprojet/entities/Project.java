package com.chedly.miniprojet.entities;

import java.time.LocalDate;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Project {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private LocalDate startDate; // Start date of the project
    private LocalDate endDate;   // End date of the project
    private String location;      // Project location or office
    private boolean active;       // Indicates if the project is currently active
    private String clientName;    // Name of the client or customer
    private double budget;        // Project budget
    private String status;  
    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees ;
    
    
}
