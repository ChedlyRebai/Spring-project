package com.chedly.miniprojet.Entyties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Formation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titreFormation;
	
	private int durreEnJour;

	

	
	public Formation() {
		super();
	}

	public Formation(Long id, String titreFormation, int durreEnJour) {
		super();
		this.id = id;
		this.titreFormation = titreFormation;
		this.durreEnJour = durreEnJour;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitreFormation() {
		return titreFormation;
	}

	public void setTitreFormation(String titreFormation) {
		this.titreFormation = titreFormation;
	}

	public int getDurreEnJour() {
		return durreEnJour;
	}

	public void setDurreEnJour(int durreEnJour) {
		this.durreEnJour = durreEnJour;
	}
	
	
}
