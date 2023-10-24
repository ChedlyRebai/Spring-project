package com.chedly.miniprojet.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.chedly.miniprojet.entities.Employee;



public interface EmployeRepository extends JpaRepository<Employee, Long> {

}
	