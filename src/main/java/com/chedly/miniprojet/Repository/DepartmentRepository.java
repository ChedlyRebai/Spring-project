package com.chedly.miniprojet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chedly.miniprojet.Entyties.Department;

@RepositoryRestResource(path = "rest")
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByNameContaining(String keyword);

    @Query("SELECT d FROM Department d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :partialName, '%'))")
    List<Department> findDepartmentsByPartialName(@Param("partialName") String partialName);

}
