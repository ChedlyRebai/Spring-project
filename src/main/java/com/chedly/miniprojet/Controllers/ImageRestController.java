package com.chedly.miniprojet.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chedly.miniprojet.Entyties.Employee;
import com.chedly.miniprojet.Entyties.Image;
import com.chedly.miniprojet.Service.EmployeeService;
import com.chedly.miniprojet.Service.ImageService;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class ImageRestController {
	@Autowired
	ImageService imageService;

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Image uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
		return imageService.uplaodImage(file);
	}

	@RequestMapping(value = "/get/info/{id}", method = RequestMethod.GET)
	public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
		return imageService.getImageDetails(id);
	}

	@RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
		return imageService.getImage(id);
	}

	@PostMapping(value = "/uplaodImageemployee/{id}")
	public Image uploadMultiImages(@RequestParam("image") MultipartFile file,
			@PathVariable("id") Long id)
			throws IOException {
		return imageService.uplaodImageemployee(file, id);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteImage(@PathVariable("id") Long id) {
		imageService.deleteImage(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Image UpdateImage(@RequestParam("image") MultipartFile file) throws IOException {
		return imageService.uplaodImage(file);
	}

	@RequestMapping(value = "/getImages/{id}", method = RequestMethod.GET)
	public List<Image> getImagesLivre(@PathVariable("id") Long id)
			throws IOException {
		return imageService.getImagesParEmployee(id);
	}

	@RequestMapping(value = "/uploadFS/{id}", method = RequestMethod.POST)
	public void uploadImageFS(@RequestParam("image") MultipartFile file, @PathVariable("id") Long id)
			throws IOException {
		Employee l = employeeService.getEmployeeById(id);
		l.setImagePath(id + ".jpg");
		Files.write(Paths.get(System.getProperty("user.home") + "/images/" + l.getImagePath()), file.getBytes());
		// livreService.saveLivre(l);
		employeeService.saveEmployee(l);
	}

	@RequestMapping(value = "/loadfromFS/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImageFS(@PathVariable("id") Long id) throws IOException {
		Employee e = employeeService.getEmployeeById(id);
		// Livre l = livreService.getLivre(id);
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/images/" + e.getImagePath()));
	}

}