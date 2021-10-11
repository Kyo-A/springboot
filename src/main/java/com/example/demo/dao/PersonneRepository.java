/*
 * 
 */
package com.example.demo.dao;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.example.demo.model.Personne;
// TODO: Auto-generated Javadoc

/**
 * The Interface PersonneRepository.
 *
 * @author Damien Monchaty
 */
public interface PersonneRepository extends JpaRepository<Personne, Long> {
	
	
	/**
	 * Find by num.
	 *
	 * @param num the num
	 * @return the personne
	 */
	Personne findByNum(Long num);
	
	

	
	
}