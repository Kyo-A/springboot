package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Damien Monchaty
 *
 */

@SpringBootApplication
public class FirstSpringBoot3Application {
	/**
	 * La méthode main est statique et est chargée en mémoire
	 * au démarrage de l'application.
	 * Méthode utilisée pour démarrer et lancer notre application
	 *
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		SpringApplication.run(FirstSpringBoot3Application.class, args);
	}

}
