package com.example.demo.service;

import java.util.Collection;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.PersonneRepository;
import com.example.demo.model.Personne;

// TODO: Auto-generated Javadoc
/**
 * The Class PersonneServiceImpl.
 *
 * @author Damien Monchaty
 */
@Service(value = "personneService")
public class PersonneServiceImpl implements PersonneService {
	
    /** The personne repository. */
    @Autowired
    private PersonneRepository personneRepository;

	/**
	 * Gets the all personne.
	 *
	 * @return the all personne
	 */
	@Override
	public Collection<Personne> getAllPersonne() {
		return IteratorUtils.toList(personneRepository.findAll().iterator());
	}

	/**
	 * Gets the personne by id.
	 *
	 * @param id the id
	 * @return the personne by id
	 */
	@Override
	public Personne getPersonneById(Long id) {
		return personneRepository.findByNum(id);
	}

	/**
	 * Save or update personne.
	 *
	 * @param personne the personne
	 * @return the personne
	 */
	@Override
	@Transactional(readOnly=false)
	public Personne saveOrUpdatePersonne(Personne personne) {
		return personneRepository.save(personne);
	}

	/**
	 * Delete personne.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional(readOnly=false)
	public void deletePersonne(Long id) {
		personneRepository.deleteById(id);
		
	}

	

}
