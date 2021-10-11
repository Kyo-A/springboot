package com.example.demo.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.PersonneRepository;
import com.example.demo.dto.PersonneDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Personne;
import com.example.demo.service.PersonneService;

/**
 * @author Damien Monchaty
 *
 */
@CrossOrigin(origins = "*")
@RestController
public class PersonneRestController {

	@Autowired
	private PersonneService personneService;

	/**
	 * Retourne la liste de personnes de l'entité mappé dans la bdd MySql.
	 * 
	 * @return Retourne la liste de personnes.
	 * 
	 */
	@GetMapping(value = "/personnes")
	public Collection<Personne> getPersonnes() {
		Collection<Personne> personnes = personneService.getAllPersonne();
		return personnes;
	}

	/**
	 * Retourne une personne selon son id de la bdd.
	 * 
	 * @return Retourne le corps d'une personne selon son id.
	 * 
	 * @throws ResourceNotFoundException Si jamais la personne n'est pas trouvée
	 * 
	 * @param id
	 */
	@GetMapping(value = "/personnes/{id}")
	public ResponseEntity<Personne> getPersonneById(@PathVariable Long id) {

		Personne personne = personneService.getPersonneById(id);
		if (personne == null) {

			throw new ResourceNotFoundException("Personne not found : " + id);
		}

		return ResponseEntity.ok().body(personne);

	}

	/**
	 * Enregistre une personne dans la bdd.
	 * 
	 * @return Retourne l'instance de l'objet Personne créé.
	 * 
	 * @param Personne
	 * 
	 */

	@PostMapping(value = "/personnes", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public Personne save(@Valid @RequestBody PersonneDto personneDto) {
		
		Personne personne = new Personne();
		personne.setNom(personneDto.getNom());
		personne.setPrenom(personneDto.getPrenom());

		return personneService.saveOrUpdatePersonne(personne);

	}

	/**
	 * Retourne une personne modifiée selon son id de la bdd.
	 * 
	 * @return Retourne le corps et le httpStatus d'une personne modifiée selon son
	 *         id.
	 * 
	 * @throws ResourceNotFoundException Si jamais la personne à modifier n'est pas
	 *                                   trouvée
	 *                                   
     * @param Id
     * @param Personne
	 */
	@PutMapping("/personnes/{id}")
	@Transactional
	public ResponseEntity<Personne> updatePersonne(@PathVariable(value = "id") Long id,
			@RequestBody PersonneDto personneDto) {
		Personne personneToUpdate = personneService.getPersonneById(id);
		if (personneToUpdate == null) {
			throw new ResourceNotFoundException("Personne not found : " + id);
		}
		personneToUpdate.setNom(personneDto.getNom());
		personneToUpdate.setPrenom(personneDto.getPrenom());
		Personne personneUpdated = personneService.saveOrUpdatePersonne(personneToUpdate);
		return new ResponseEntity<Personne>(personneUpdated, HttpStatus.OK);
	}

	/**
	 * Retourne une personne supprimée selon son id dans la bdd.
	 * 
	 * @return Retourne le corps d'une personne supprimée selon son id.
	 * 
	 * @throws ResourceNotFoundException Si jamais la personne à modifier n'est pas
	 *                                   trouvée
	 * @param Id
	 */
	@DeleteMapping("/personnes/{id}")
	@Transactional
	public void deletePersonne(@PathVariable(value = "id") Long id) {
		Personne personne = personneService.getPersonneById(id);
		if (personne == null) {
			throw new ResourceNotFoundException("Personne not found : " + id);
		}

		personneService.deletePersonne(id);
	}

}
