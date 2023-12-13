package com.chedly.miniprojet.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chedly.miniprojet.Entyties.Employee;
import com.chedly.miniprojet.Entyties.Formation;
import com.chedly.miniprojet.Repository.EmployeeRepository;
import com.chedly.miniprojet.Service.EmployeeService;
import com.chedly.miniprojet.Service.FormationService;

@RestController
@RequestMapping("/formation")
@CrossOrigin("*")
public class FormationController {
	private  FormationService formationService;
	
	@Autowired
    public FormationController(FormationService f) {
        this.formationService= f;
    }
	
	@GetMapping
    public List<Formation> getAllEmployees() {
        return this.formationService.getAllForamtion();
    }
	
	
}
