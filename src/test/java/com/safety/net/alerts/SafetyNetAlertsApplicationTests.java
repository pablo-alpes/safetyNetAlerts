package com.safety.net.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.net.alerts.constants.JSONFile;
import com.safety.net.alerts.repository.JSONFileRetriever;
import com.safety.net.alerts.model.PeopleAndClaims;
import com.safety.net.alerts.repository.ModelDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = SafetyNetAlertsApplication.class)
class SafetyNetAlertsApplicationTests {
	private static JSONFileRetriever jsonData;
	private static ModelDTO modelDTO;
	private static PeopleAndClaims peopleList;
	@BeforeAll
	public static void startUp() {
		jsonData = new JSONFileRetriever();
		modelDTO = new ModelDTO();
	}

	@Test
	public void givenJsonFile_whenSerializingAsList_thenCorrectNumberOfItems()
		throws IOException {
		peopleList = new PeopleAndClaims();

		//JSON to Java Object deserialisation to a List of Lists
		//peopleList = mapper.readValue(new File(JSONFile.JSONDataPath), PeopleAndClaims.class);
		peopleList = jsonData.getAll();

		assertEquals(23, (peopleList.getPeople()).size());
		assertEquals(13, (peopleList.getFirestations()).size());
		assertEquals(23, (peopleList.getMedicalRecords()).size());
		}

}
