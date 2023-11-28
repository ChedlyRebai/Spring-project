package com.chedly.miniprojet.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

import com.chedly.miniprojet.Entyties.Employee;
import com.chedly.miniprojet.Entyties.EmployeeDTO;
import com.chedly.miniprojet.Entyties.EmployeePicture;
import com.chedly.miniprojet.Repository.EmployeeRepository;
import com.chedly.miniprojet.Service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepo;
    
    
    
    @Autowired
    public EmployeeController(EmployeeService employeeService,EmployeeRepository emprepo) {
        this.employeeService = employeeService;
		this.employeeRepo = emprepo;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    
    
    //@GetMapping("getbyid/{id}")
    @RequestMapping(path="/getemp/{id}",method  = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    @RequestMapping(path="/getname/{name}",method  = RequestMethod.GET)
    public List<Employee> getEmployeeByName(@PathVariable String name ) {
    	return employeeRepo.findEmployeesByPartialFirstName(name);
    }

    //@PostMapping
    @RequestMapping(path="/addemp",method  = RequestMethod.POST)
    public ResponseEntity<Employee>  createEmployee(@RequestBody Employee employee) {
    	Employee savedEmployee = employeeRepo.save(employee);
    	return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        //return employeeService.createEmployee(employee);
    }
    
    
    /*@RequestMapping(path = "/addemp", method = RequestMethod.POST)
    public ResponseEntity<Employee> createEmployeeWithImage(
            @RequestBody Employee employee,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        // Create the employee
        Employee savedEmployee = employeeRepo.save(employee);

        // Check if a file is provided for image upload
        if (file != null && !file.isEmpty()) {
            // Handle image upload logic
            EmployeePicture picture = new EmployeePicture();
            picture.setImageName(file.getOriginalFilename());
            picture.setImageType(file.getContentType());

            try {
                picture.setImageData(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            savedEmployee.getPictures().add(picture);
            employeeRepo.save(savedEmployee);
        }

        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }*/

    
    
    @RequestMapping(path="/updateemp",method = RequestMethod.PUT)
    public Employee updateEmployee( @RequestBody Employee employee) {
        return employeeService.updateEmployee( employee);
    }
    
 // EmployeeController.java (continued)
   /* @PostMapping("/{employeeId}/upload")
    public ResponseEntity<String> handleImageUpload(
            @PathVariable Long employeeId,
            @RequestParam("file") MultipartFile file) {
        
        Optional<Employee> optionalEmployee = employeeRepo.findById(employeeId);
        
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            
            // Handle image upload logic
            EmployeePicture picture = new EmployeePicture();
            picture.setImageName(file.getOriginalFilename());
            picture.setImageType(file.getContentType());
            
            try {
                picture.setImageData(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            employee.getPictures().add(picture);
            employeeRepo.save(employee);

            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }
    }*/

  
    
    
    @RequestMapping(path="/deleteemp/{id}",method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

}
