package com.chedly.miniprojet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chedly.miniprojet.Entyties.Image;

public interface ImageRepository extends JpaRepository<Image , Long> {
	
}
