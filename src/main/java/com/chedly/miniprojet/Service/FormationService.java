package com.chedly.miniprojet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chedly.miniprojet.Entyties.Formation;


public interface FormationService {

	public void saveFormation(Formation f);
	public void deleteForamtion(Long id);
	public Formation getFormationById(Long id);
	public List<Formation> getAllForamtion();
	public Formation updateFormation(Formation f );
	
	
}
