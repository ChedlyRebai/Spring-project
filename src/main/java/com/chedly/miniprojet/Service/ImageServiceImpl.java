package com.chedly.miniprojet.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chedly.miniprojet.Entyties.Employee;
import com.chedly.miniprojet.Entyties.Image;
import com.chedly.miniprojet.Repository.EmployeeRepository;
import com.chedly.miniprojet.Repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Image uplaodImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImage(file.getBytes());
        return imageRepository.save(image);
    }

    @Override
    public Image getImageDetails(Long id) throws IOException {
        Image dbImage = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));

        Image image = new Image();
        image.setIdImage(dbImage.getIdImage());
        image.setName(dbImage.getName());
        image.setType(dbImage.getType());
        image.setImage(dbImage.getImage());

        return image;
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        final Optional<Image> dbImage = imageRepository.findById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(dbImage.get().getImage());
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image uplaodImageemployee(MultipartFile file, Long idLivre)
            throws IOException {
        Employee employee = new Employee();
        employee.setId(idLivre);

        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImage(file.getBytes());
        image.setEmployee(employee);

        return imageRepository.save(image);
    }

    @Override
    public List<Image> getImagesParEmployee(Long Id) {
        Employee l = employeeRepository.findById(Id).get();
        System.out.println(l.getImages());
        return l.getImages();
    }

}
