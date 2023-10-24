package com.chedly.miniprojet.Service;

import java.util.List;

import com.chedly.miniprojet.Entyties.Department;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Department createDepartment(Department department);

    Department updateDepartment(Long id, Department department);

    void deleteDepartment(Long id);
}
