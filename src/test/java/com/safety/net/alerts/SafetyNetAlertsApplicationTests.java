/** Tests unitaires */

package com.safety.net.alerts;

import com.safety.net.alerts.model.*;
import com.safety.net.alerts.repository.ModelDAOImpl;
import com.safety.net.alerts.repository.ModelDTOImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = SafetyNetAlertsApplication.class)
class SafetyNetAlertsApplicationTests {
	private static ModelDAOImpl jsonData;
	private static ModelDTOImpl modelDTOImpl;
	private static PeopleAndClaims peopleList;

	private static PersonMedicalRecordsJoinMapper personsjoinmapper = Mappers.getMapper(PersonMedicalRecordsJoinMapper.class);

	@BeforeAll
	public static void startUp() {
		jsonData = new ModelDAOImpl();
		modelDTOImpl = new ModelDTOImpl();
	}


	//test with order and non ordered json file for merge results "data.json" and data2

	//@Test
	//public void []
	//http://localhost:8080/childAlert?address=1509 Culver St, 2 enfants + rajouter le test

//	@Test

}


