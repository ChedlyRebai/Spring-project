package com.chedly.miniprojet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chedly.miniprojet.Entyties.Employee;
import com.chedly.miniprojet.Entyties.EmployeeDTO;

@RepositoryRestResource(path = "rest")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :partialFirstName, '%'))")
	List<Employee> findEmployeesByPartialFirstName(@Param("partialFirstName") String partialFirstName);
}
