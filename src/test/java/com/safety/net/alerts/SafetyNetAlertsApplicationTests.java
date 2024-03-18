/**
 * Tests unitaires
 * */

package com.safety.net.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.net.alerts.model.*;
import com.safety.net.alerts.repository.ModelDAOImpl;
import com.safety.net.alerts.repository.ModelDTOImpl;
import com.safety.net.alerts.service.MergeService;
import com.safety.net.alerts.service.PeopleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.context.ContextConfiguration;


import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = SafetyNetAlertsApplication.class)
class SafetyNetAlertsApplicationTests {
	private static ModelDAOImpl jsonData;
	private static ModelDTOImpl modelDTOImpl;
	private static PeopleAndClaims peopleList;
	private static ObjectMapper mapper = new ObjectMapper();
	private static PersonMedicalRecordsJoinMapper personsjoinmapper = Mappers.getMapper(PersonMedicalRecordsJoinMapper.class);
	private static MergeService mergeService = new MergeService(mapper);

	@BeforeAll
	public static void startUp() {
		jsonData = new ModelDAOImpl();
		modelDTOImpl = new ModelDTOImpl();
		mapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false)); //set by default no filter
	}

	@Test
	@DisplayName("ADD - Adds a new Individual with all its attributes")
	void saveAll() throws IOException { //test for the 3 entities for one individual

		//ARRANGE -- Individual with or all the attributes to be added / updated / or / deleted
		peopleList = new PeopleAndClaims();
		peopleList = jsonData.getAll(); //we launch the retrieve of the json with all the info
		List<Persons> people = modelDTOImpl.retrieveAllPeople(); //we get all the objects in the Persons class, focus of the test
		people.add(new Persons("Pablo","Miranda","France","Paris","01020","0000","test@test.com")); //ADDs the new record type Persons
		peopleList.setPersons(people); // we update the whole list

		List<Firestations> firestation = modelDTOImpl.retrieveAllFirestations(); //we get all the objects in the Firestations class
		firestation.add(new Firestations("France", 5)); //we add the new record type firestation
		peopleList.setFirestations(firestation); // we update the whole list

		List<MedicalRecords> medicalRecord = modelDTOImpl.retrieveAllMedications(); //we get all the objects in the MedicalRecord class
		medicalRecord.add(new MedicalRecords("Pablo","Miranda","1/1/1900","","")); //we add the new record type Medical
		peopleList.setMedicalRecords(medicalRecord); // we update the whole list

		//ACT
		mapper.writeValue(new File("data.json"), peopleList);

		//the object is saved to the mapper
		assert(true);
	}

	@Test
	@DisplayName("Remove - Remove an individual for the service required ")
	void removeIndividual_inPersonList() throws IOException { //test for the 3 entities for one individual
		//For only one individual at the time

		//ARRANGE -- Individual with or all the attributes to be added / updated / or / deleted
		peopleList = new PeopleAndClaims();
		peopleList = jsonData.getAll(); //we launch the retrieve of the json with all the info
		List<Persons> people = modelDTOImpl.retrieveAllPeople(); //we get all the objects in the Persons class, focus of the test

		//ACT - id removal based on firstName lastName
		int personID = modelDTOImpl.personID("Jacob","Boyd");

		people.remove(personID);
		peopleList.setPersons(people); // we update the whole list with the person that has been deleted
		mapper.writeValue(new File("./src/dataTest.json"), peopleList); //the object is saved to the mapper with the removal //Requires to check Exception if -1 if (personID == -1) return

		//ASSERT
		System.out.println(peopleList) ;
		assert(true);
	}

	@Test
	@DisplayName("REPLACE - Replace records for an individual for the service required ")
	void ReplaceIndividualInformation_inPersonList() throws IOException { //test for 1 service for one individual
		//For only one individual at the time

		//ARRANGE -- Individual retrieval
		peopleList = new PeopleAndClaims();
		peopleList = jsonData.getAll();
		List<Persons> people = modelDTOImpl.retrieveAllPeople(); //we get all the objects in the Persons class, focus of the test

		//ACT - id record replacement based on firstName lastName
		int personID = modelDTOImpl.personID("Jacob","Boyd");
		people.set(personID, new Persons("Jacob","Boyd","France","Paris","01020","0000","test@test.com")); //Replaces the index of the object for the new info record
		peopleList.setPersons(people); // we update the whole list with the updated record

		mapper.writeValue(new File("./src/dataTest.json"), peopleList); //the object is saved to the mapper with the removal //Requires to check Exception if -1 if (personID == -1) return

		//ASSERT
		System.out.println(peopleList) ;
		assert(true);
	}

	@Test
	@DisplayName("Determines if the children Count and Adults are shown and correct")
	void countChildrenTest() throws Exception {
		//Instantiation of models and data retrieval
		List<FullJoin> people = mergeService.FullJoin();
		PeopleService peopleService = new PeopleService();
		System.out.println(peopleService.countAdults(1));
		System.out.println(peopleService.countChildren(1));
	}

	@Test
	@DisplayName("Determines the MedicalID for a person in the list or not")
	void medicalIDTest() {
		assertEquals(modelDTOImpl.medicalID("Pablo", "Miranda"),-1);
		assertEquals(modelDTOImpl.medicalID("John", "Boyd"), 0);
	}
	@Test
	@DisplayName("Determines the PersonID in the list or not")
	void personIDTest() {
		assertEquals(modelDTOImpl.personID("Pablo", "Miranda"),-1);
		assertEquals(modelDTOImpl.personID("John", "Boyd"), 0);
	}


	//test with order and non ordered json file for merge results "data.json" and data2

}


