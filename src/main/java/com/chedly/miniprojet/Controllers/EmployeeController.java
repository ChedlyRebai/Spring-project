package com.chedly.miniprojet.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chedly.miniprojet.Entyties.Employee;
import com.chedly.miniprojet.Repository.EmployeeRepository;
import com.chedly.miniprojet.Service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepo;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository emprepo) {
        this.employeeService = employeeService;
        this.employeeRepo = emprepo;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // @GetMapping("getbyid/{id}")
    @RequestMapping(path = "/getemp/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/getname/{name}", method = RequestMethod.GET)
    public List<Employee> getEmployeeByName(@PathVariable String name) {
        return employeeRepo.findEmployeesByPartialFirstName(name);
    }

    // @PostMapping
    @RequestMapping(path = "/addemp", method = RequestMethod.POST)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepo.save(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        // return employeeService.createEmployee(employee);
    }

    /*
     * @RequestMapping(path = "/addemp", method = RequestMethod.POST)
     * public ResponseEntity<Employee> createEmployeeWithImage(
     * 
     * @RequestBody Employee employee,
     * 
     * @RequestParam(value = "file", required = false) MultipartFile file) {
     * 
     * // Create the employee
     * Employee savedEmployee = employeeRepo.save(employee);
     * 
     * // Check if a file is provided for image upload
     * if (file != null && !file.isEmpty()) {
     * // Handle image upload logic
     * EmployeePicture picture = new EmployeePicture();
     * picture.setImageName(file.getOriginalFilename());
     * picture.setImageType(file.getContentType());
     * 
     * try {
     * picture.setImageData(file.getBytes());
     * } catch (IOException e) {
     * e.printStackTrace();
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * 
     * savedEmployee.getPictures().add(picture);
     * employeeRepo.save(savedEmployee);
     * }
     * 
     * return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
     * }
     */

    @DeleteMapping("/{id}/delete-avatar")
    public ResponseEntity<Map<String, Object>> deleteAvatar(
            @PathVariable Long id,
            @RequestParam String avatarUrl) {
        try {

            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createResponse(false, "employee not found"));
            }

            // Check if the avatarUrl exists in the list of avatarUrls
            if (employee.getAvatarUrls().contains(avatarUrl)) {
                employee.getAvatarUrls().remove(avatarUrl);
                employeeRepo.save(employee);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(createResponse(true, "Avatar deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createResponse(false, "Avatar URL not found for the given employee"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createResponse(false, "Error deleting avatar"));
        }
    }

    private Map<String, Object> createResponse(boolean success, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", message);
        return response;
    }

    @PostMapping("/{id}/upload-avatar")
    public ResponseEntity<Map<String, Object>> handleAvatarUpload(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            // Validate if the employee exists
            Optional<Employee> optionalEmployee = employeeRepo.findById(id);

            if (optionalEmployee.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createResponse(false, optionalEmployee.toString()));
            }
            Employee employee = optionalEmployee.get();
            // Use CompletableFuture to asynchronously save the image
            CompletableFuture<String> saveImageFuture = CompletableFuture.supplyAsync(() -> {
                try {

                    return employeeService.saveEmployeeAvatar(employee, file);
                } catch (IOException e) {
                    // Handle the exception if needed
                    return null;
                }
            });

            String avatarUrl;
            try {
                avatarUrl = saveImageFuture.get(); // Wait for completion without using Thread.sleep
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(createResponse(true, avatarUrl.toString()));
            } catch (InterruptedException | ExecutionException e) {
                avatarUrl = null;
            }

            if (avatarUrl != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(createResponse(true, "Avatar added successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(createResponse(false, "Error uploading avatar"));
            }

        } catch (Exception e) {
            // Handle other exceptions if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createResponse(false, "Error handling request"));
        }
    }

    // @PostMapping("/{id}/uploa-avatar")
    // public ResponseEntity<Map<String, Object>> handleAvatarUpload(
    // @PathVariable Long id,
    // @RequestParam("file") MultipartFile file) {
    // try {
    // // Validate if the employee exists
    // // Author author = authorService.getAuthorById(id);
    // Employee employee = employeeRepo.findById(id).get();
    // // employeeService.getEmployeeById(id);
    // if (employee == null) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createResponse(false,
    // "employee not found"));
    // }

    // System.out.println(employee);
    // // Use CompletableFuture to asynchronously save the image
    // CompletableFuture<String> saveImageFuture = CompletableFuture.supplyAsync(()
    // -> {
    // try {
    // return employeeService.saveEmployeeAvatar(employee, file);
    // } catch (IOException e) {
    // // Handle the exception if needed
    // return null;
    // }
    // });

    // // Wait for the saveImageFuture to complete before responding
    // String avatarUrl = saveImageFuture.join();

    // // hack to wait image saving algorithm for now :/
    // Thread.sleep(3000);

    // // if (avatarUrl != null) {
    // // return ResponseEntity.status(HttpStatus.CREATED)
    // // .body(createResponse(true, "Avatar added successfully"));
    // // } else {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(createResponse(false, "Error uploading avatar"));
    // // }

    // } catch (Exception e) {
    // // Handle other exceptions if needed
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(createResponse(false, "Error handling request"));
    // }
    // }

    @RequestMapping(path = "/updateemp", method = RequestMethod.PUT)
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    // EmployeeController.java (continued)
    /*
     * @PostMapping("/{employeeId}/upload")
     * public ResponseEntity<String> handleImageUpload(
     * 
     * @PathVariable Long employeeId,
     * 
     * @RequestParam("file") MultipartFile file) {
     * 
     * Optional<Employee> optionalEmployee = employeeRepo.findById(employeeId);
     * 
     * if (optionalEmployee.isPresent()) {
     * Employee employee = optionalEmployee.get();
     * 
     * // Handle image upload logic
     * EmployeePicture picture = new EmployeePicture();
     * picture.setImageName(file.getOriginalFilename());
     * picture.setImageType(file.getContentType());
     * 
     * try {
     * picture.setImageData(file.getBytes());
     * } catch (IOException e) {
     * e.printStackTrace();
     * return new ResponseEntity<>("Failed to upload image",
     * HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * 
     * employee.getPictures().add(picture);
     * employeeRepo.save(employee);
     * 
     * return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
     * } else {
     * return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
     * }
     * }
     */

    @RequestMapping(path = "/deleteemp/{id}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

}
