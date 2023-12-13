package com.chedly.miniprojet.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chedly.miniprojet.Entyties.Formation;
import com.chedly.miniprojet.Repository.FormationRepostory;

@Service
public class FormationServiceImp implements FormationService {

	
	private final FormationRepostory formationRepo;
	
	@Autowired
	public FormationServiceImp(FormationRepostory fr) {
		this.formationRepo=fr;
	}
	
	
	@Override
	public void saveFormation(Formation f) {
		this.formationRepo.save(f);
		
	}

	@Override
	public void deleteForamtion(Long id) {
		this.formationRepo.deleteById(id);	
	}

	@Override
	public Formation getFormationById(Long id) {
		Formation f= this.formationRepo.findById(id).get();
		return f;
	}

	@Override
	public List<Formation> getAllForamtion() {
		List<Formation> listFormation=this.formationRepo.findAll();
		return listFormation;
	}

	@Override
	public Formation updateFormation(Formation f) {
		return this.formationRepo.save(f);
	}

}
