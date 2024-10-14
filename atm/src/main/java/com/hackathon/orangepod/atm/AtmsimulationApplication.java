package com.hackathon.orangepod.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hackathon.orangepod.atm")
public class AtmsimulationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmsimulationApplication.class, args);
	}

}
