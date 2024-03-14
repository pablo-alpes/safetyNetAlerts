/** Tests unitaires */

package com.safety.net.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.net.alerts.model.*;
import com.safety.net.alerts.repository.ModelDAOImpl;
import com.safety.net.alerts.repository.ModelDTOImpl;
import com.safety.net.alerts.service.MergeService;
import com.safety.net.alerts.service.PeopleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	}

	@Test
	void countChildrenTest(int station) throws Exception {
		//Instantiation of models and data retrieval
		List<FullJoin> people = mergeService.FullJoin();
		PeopleService peopleService = new PeopleService();
		System.out.println(peopleService.countAdults(1));
		System.out.println(peopleService.countChildren(1));
	}


	//test with order and non ordered json file for merge results "data.json" and data2

	//@Test
	//public void []
	//http://localhost:8080/childAlert?address=1509 Culver St, 2 enfants + rajouter le test


}


