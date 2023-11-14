package com.chedly.miniprojet.Service;

import java.util.List;

import com.chedly.miniprojet.Entyties.Department;
import com.chedly.miniprojet.Entyties.Employee;

public interface DepartmentService {
    List<Department> getAllDepartments();

    
    Department getDepartmentById(Long id);

    Department createDepartment(Department department);

    Department updateDepartment(Long id, Department department);

    void deleteDepartment(Long id);

   
}
