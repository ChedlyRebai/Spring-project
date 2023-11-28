package com.chedly.miniprojet.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chedly.miniprojet.Entyties.Department;
import com.chedly.miniprojet.Repository.DepartmentRepository;
import com.chedly.miniprojet.Service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @RequestMapping(path="/getdep/{id}",method = RequestMethod.GET)
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }
    
    
    @RequestMapping(path="/getname/{partialName}", method = RequestMethod.GET)
    public List<Department> searchDepartmentsByPartialName(@PathVariable String partialName) {
        System.out.println("aaa:" + partialName);
        return departmentRepository.findDepartmentsByPartialName(partialName);
    }

    
    @GetMapping("/search")
    public ResponseEntity<List<Department>> searchDepartments(@RequestParam String keyword) {
        List<Department> departments = departmentRepository.findByNameContaining(keyword);
        return ResponseEntity.ok(departments);
    }

    @RequestMapping(path="/adddep",method = RequestMethod.POST)
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @RequestMapping(path="/updatedep/{id}",method = RequestMethod.PUT)
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @RequestMapping(path="/deletedep/{id}",method = RequestMethod.DELETE)
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

}
