package com.chedly.miniprojet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.chedly.miniprojet.Entyties.Formation;
import com.chedly.miniprojet.Service.FormationService;

@SpringBootApplication
public class MiniprojetApplication implements CommandLineRunner  {

	@Autowired
	FormationService f;
	
	public static void main(String[] args) {
		SpringApplication.run(MiniprojetApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		f.saveFormation(new Formation(null,"f3",2));
		f.saveFormation(new Formation(null,"f2",3));
	}
}
