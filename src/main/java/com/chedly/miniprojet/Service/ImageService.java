package com.chedly.miniprojet.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.chedly.miniprojet.Entyties.Image;

public interface ImageService {

	Image uplaodImage(MultipartFile file) throws IOException;

	Image getImageDetails(Long id) throws IOException;

	ResponseEntity<byte[]> getImage(Long id) throws IOException;

	void deleteImage(Long id);

	Image uplaodImageemployee(MultipartFile file, Long id) throws IOException;

	public List<Image> getImagesParEmployee(Long Id);
}