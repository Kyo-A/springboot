package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito.BDDMyOngoingStubbing;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.dao.PersonneRepository;
import com.example.demo.model.Personne;
import com.example.demo.service.PersonneService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class PersonneRestControllerTest.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonneRestController.class)
class PersonneRestControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The personne service. */
	@MockBean
	private PersonneService personneService;

	/** The personne. */
	Personne personne = new Personne(1L, "admin", "admin");

	/**
	 * Test get personnes.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testGetPersonnes() throws Exception {
		
		Personne mockPersonne1 = new Personne(1L, "admin", "admin");
		Personne mockPersonne2 = new Personne(2L, "admin", "admin");		
		List<Personne> personneList = new ArrayList<>();
		personneList.add(mockPersonne1);
		personneList.add(mockPersonne2);
		
		Mockito.when(personneService.getAllPersonne()).thenReturn(personneList);
		
		String URI = "/api/v1/personnes";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(personneList);
		String outputInJson = result.getResponse().getContentAsString();
		
		assertThat(outputInJson).isEqualTo(expectedJson);

	}

	/**
	 * Test get personne by id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testGetPersonneById() throws Exception {

		Personne mockPersonne = new Personne(1L, "admin", "admin");

		Mockito.when(personneService.getPersonneById(Mockito.anyLong())).thenReturn(mockPersonne);

		String URI = "/api/v1/personnes/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockPersonne);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	/**
	 * Test save.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testSave() throws Exception {

		Personne mockPersonne = new Personne(1L, "admin", "admin");

		String inputInJson = this.mapToJson(mockPersonne);

		String URI = "/api/v1/personnes";

		Mockito.when(personneService.saveOrUpdatePersonne(Mockito.any(Personne.class))).thenReturn(mockPersonne);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);

	}

	/**
	 * Test update personne.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testUpdatePersonne() throws Exception {
		
		Personne mockPersonne = new Personne(1L, "admin", "admin");
		
		Mockito.when(personneService.getPersonneById(Mockito.anyLong())).thenReturn(mockPersonne);
					      
		Personne personneFromDB = personneService.getPersonneById(mockPersonne.getNum());
		
		personneFromDB.setNom("admino");	

		String inputInJson = this.mapToJson(personneFromDB);

		String URI = "/api/v1/personnes/1";

		Mockito.when(personneService.saveOrUpdatePersonne(Mockito.any(Personne.class))).thenReturn(mockPersonne);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
	}

	/**
	 * Test delete personne.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testDeletePersonne() throws Exception {
		Personne  mockPersonne = new Personne(1L, "admin", "admin");
		Mockito.when(personneService.getPersonneById(Mockito.anyLong())).thenReturn(mockPersonne);
		
		doNothing().when(personneService).deletePersonne(mockPersonne.getNum());
		
		
		String URI = "/api/v1/personnes/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andReturn();
		
		
		verify(personneService, times(1)).getPersonneById(mockPersonne.getNum());
		verify(personneService, times(1)).deletePersonne(mockPersonne.getNum());

		

	}

	/**
	 * Map to json.
	 *
	 * @param object the object
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
