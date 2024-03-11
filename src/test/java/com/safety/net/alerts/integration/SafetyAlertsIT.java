package com.safety.net.alerts.integration;

import com.safety.net.alerts.SafetyNetAlertsApplication;
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
class SafetyNetAlertsIT {
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


    @Test
    void givenPeopleJoinMedicalRecords_whenCallingIt_thenExpectJointureDone() throws IOException {

        //we do the test from the JSON
        peopleList = new PeopleAndClaims();
        peopleList = jsonData.getAll();
        Persons person = new Persons();
        person.setFirstName(((peopleList.getPeople().get(0).getFirstName())));
        person.setLastName((peopleList.getPeople().get(0).getLastName()));

        MedicalRecords medicalRecord = new MedicalRecords();

        //ACT
        //condition to testequality between keys
        medicalRecord = peopleList.getMedicalRecords().get(4); //how to ensure the mapper knows this? - with the primary key match

        PersonsMedicalRecordsJoin personsjoin;
        personsjoin = personsjoinmapper.mergeRecord(medicalRecord, person);

        //ASSERT - COnversion and result of merge
        //LocalDate birthdate = modelDTOImpl.convertDate(personsjoin.getBirthdate());
        //Period period = Period.between(LocalDate.now(), birthdate);
        //int years = Math.abs(period.getYears());
        //System.out.println("Age:"+years);

        assertEquals("03/06/1984", personsjoin.getBirthdate());

    }

    @Test
    void givenPeopleJoinMedicalRecords_whenCallingIt_thenAllJsonIsMerged() throws IOException {

        //we do the test from the JSON
        peopleList = new PeopleAndClaims();
        peopleList = jsonData.getAll();
        Persons person = new Persons();

        MedicalRecords medicalRecord = new MedicalRecords();
        PersonsMedicalRecordsJoin personsjoin;
        personsjoin = personsjoinmapper.mergeRecord(medicalRecord, person);

        //ACT
        //condition to testequality between keys

        //ASSERT - COnversion and result of merge
        ;

    }
}

