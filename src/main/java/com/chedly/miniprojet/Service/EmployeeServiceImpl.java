package com.chedly.miniprojet.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.chedly.miniprojet.Entyties.Employee;
import com.chedly.miniprojet.Entyties.EmployeeDTO;
import com.chedly.miniprojet.Repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    @Override
    public List<Employee> getAllEmployees() {
        /*
         * return this.employeeRepository.findAll().stream()
         * .map(this::convertEntityToDto)
         * .collect(Collectors.toList());
         */
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO convertEntityToDto(Employee employee) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return employeeDTO;
    }

    public void writeEmployeesToCsv(Writer writer) {
        List<Employee> employees = employeeRepository.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("ID", "First Name", "Last Name", "Email", "Department");
            for (Employee employee : employees) {
                csvPrinter.printRecord(employee.getId(), employee.getFirstName(), employee.getLastName(),
                        employee.getEmail(), employee.getDepartment());
            }
        } catch (IOException e) {
            // log.error("Error While writing CSV ", e);
        }
    }

    // @Override
    public String saveEmployeeAvata(Employee employee, MultipartFile file) throws IOException {
        // Validate file type, size, etc. if needed
        // Extract original file extension
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);

        // Save the file to the static/images directory
        String fileName = "avatar_" + employee.getId() + "_" + UUID.randomUUID() + "." + fileExtension;
        String filePath = Paths.get("src/main/resources/static/images", fileName).toString();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // Update the employee's avatarUrl
        String avatarUrl = "/images/" + fileName;

        // delete default image if found
        if (employee.getAvatarUrls().contains("/images/default.png")) {
            employee.getAvatarUrls().remove("/images/default.png");
            employeeRepository.save(employee);
        }
        employee.getAvatarUrls().add(avatarUrl);
        employeeRepository.save(employee);
        return avatarUrl;
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void addAvatarUrl(Employee employee, String avatarUrl) {
        employee.addAvatarUrl(avatarUrl);
        saveEmployee(employee);
    }

    @Override
    public String saveEmployeeAvatar(Employee employee, MultipartFile file) throws IOException {
        // Implement the logic to save the image and return the avatar URL
        // This could involve storing the file, generating a URL, etc.
        // Example:
        String avatarUrl = saveImageToFileSystem(employee.getId(), file);
        employee.addAvatarUrl(avatarUrl);
        employeeRepository.save(employee);
        return avatarUrl;
    }

    private String saveImageToFileSystem(Long employeeId, MultipartFile file) throws IOException {
        // Implement logic to save the file to the file system or any storage system
        // Generate a unique file name based on employeeId, timestamp, etc.
        // Example:
        String fileName = "avatar_" + employeeId + "_" + System.currentTimeMillis() + ".jpg";
        // Save the file to the desired location
        // Example:
        Files.copy(file.getInputStream(), Paths.get("/path/to/your/avatar/folder", fileName),
                StandardCopyOption.REPLACE_EXISTING);
        // Return the URL of the saved file
        return "/avatars/" + fileName;
    }
}
