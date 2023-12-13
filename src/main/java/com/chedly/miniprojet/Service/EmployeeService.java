package com.chedly.miniprojet.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chedly.miniprojet.Entyties.Employee;
import com.chedly.miniprojet.Entyties.EmployeeDTO;

@Service
public interface EmployeeService {

    EmployeeDTO convertEntityToDto(Employee employee);

    public String saveEmployeeAvatar(Employee employee, MultipartFile file) throws IOException;

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    public void writeEmployeesToCsv(Writer writer);

    void deleteEmployee(Long id);

    void saveEmployee(Employee employee);

    void addAvatarUrl(Employee employee, String avatarUrl);
}
