package com.chedly.miniprojet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chedly.miniprojet.Entyties.Department;



@RepositoryRestResource(path = "rest")
public interface DepartmentRepository extends JpaRepository<Department, Long> {
   
}

