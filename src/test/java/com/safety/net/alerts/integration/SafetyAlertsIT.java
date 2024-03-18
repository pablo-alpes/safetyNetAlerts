package com.safety.net.alerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.net.alerts.SafetyNetAlertsApplication;
import com.safety.net.alerts.model.*;
import com.safety.net.alerts.repository.ModelDAOImpl;
import com.safety.net.alerts.repository.ModelDTOImpl;
import com.safety.net.alerts.service.CalculateAge;
import com.safety.net.alerts.service.MergeService;
import com.safety.net.alerts.service.PeopleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = SafetyNetAlertsApplication.class)
class SafetyNetAlertsIT {
    private static ModelDAOImpl jsonData;
    private static ModelDTOImpl modelDTOImpl;
    private static PeopleAndClaims peopleList;

    private static PersonMedicalRecordsJoinMapper personsjoinmapper = Mappers.getMapper(PersonMedicalRecordsJoinMapper.class);
    private static ObjectMapper mapper = new ObjectMapper();
    private static MergeService mergeService = new MergeService(mapper);

    @BeforeAll
    public static void startUp() {
        jsonData = new ModelDAOImpl();
        modelDTOImpl = new ModelDTOImpl();
    }


    //test with order and non ordered json file for merge results "data.json" and data2

    @Nested
    class componentsTesting {
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
            medicalRecord = peopleList.getMedicalRecords().get(4); // we give the correspondance of ids 0 -> 4

            PersonsMedicalRecordsJoin personsjoin;
            personsjoin = personsjoinmapper.mergeRecord(medicalRecord, person);

            //ASSERt
            assertEquals(39, Integer.parseInt(personsjoin.getBirthdate()));

        }

        @Test
        void givenBirthdate_whenDateFormatDDMMYYorMMDDYYYisPassed_thenConvertToAge() throws Exception {
            //ARRANGE
            // FullJoin fulljoin = new FullJoin();
            try {
                peopleList = new PeopleAndClaims();
                peopleList = jsonData.getAll();

                String birthdateMddyyyy = peopleList.getMedicalRecords().get(17).getBirthdate();
                String birthdateddmmyyyy = peopleList.getMedicalRecords().get(6).getBirthdate();

                //ACT
                int ageMMDDYYYY = new CalculateAge().convertDate(birthdateMddyyyy);
                int ageDDMMYYYY = new CalculateAge().convertDate(birthdateddmmyyyy);

                //ASSERT - age calculation
                assertEquals(44, ageMMDDYYYY);
                assertEquals(12, ageDDMMYYYY);
            } catch (DateTimeException e) {
                throw new RuntimeException(e);
            }
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

        @Test
        void countChildrenTest() throws Exception {
            //ARRANGE
            MergeService mergeService = new MergeService(new ObjectMapper());
            List<FullJoin> people = mergeService.FullJoin();
            PeopleService peopleService = new PeopleService();

            //ACT
            int children = peopleService.countChildren(1);

            //ASSERT
            assertEquals(1, children);

        }

        @Test
        void countAdultsTest() throws Exception {
            //ARRANGE
            List<FullJoin> people = mergeService.FullJoin();
            MergeService mergeService = new MergeService(new ObjectMapper());
            PeopleService peopleService = new PeopleService();

            //ACT
            int adults = peopleService.countAdults(1);

            //ASSERT
            assertEquals(adults, 5);

        }
    }

    @Nested
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    class endPointsTesting {
        @LocalServerPort
        private int port = 8080; //port configuration

        @Autowired
        private TestRestTemplate restTemplate; //helps to perform HTTP requests

        /**
         * ATTENTION:
         * Rest Template is deprecated, to move towards WebClient for the future.
         * For simple up and running this is enough. For checking business logic/behaviour, Webclient provides better solution.
         */
        @Nested
        class endpointsUp {

            @ParameterizedTest
            @ValueSource(ints = {0, 1, 2, 3, 4}) //checks all firestations and inexisting ones
            @DisplayName("1/7 ENDPOINT - FIRESTATION - People served by the firestation and people counts- /http://localhost:8080/firestation?stationNumber=<station_number>")
            void testGetRequest_EndPoint1_UP(int firestations) {
                String url = "http://localhost:" + port + "/firestation?stationNumber=" + (firestations);
                String response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                assertNotNull(response);
            }

            @ParameterizedTest
            @ValueSource(strings = {"", "644 Gershwin Cir", "1509 Culver St"})
            //checks all firestations and inexisting ones
            @DisplayName("2/7 ChildAlert - http://localhost:8080/childAlert?address=<address>")
            void testGetRequest_EndPoint2_UP(String address) {
                String url = "http://localhost:" + port + "/childAlert?address=" + (address);
                String response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                assertNotNull(response); // TODO-- refactor function to ensure delivers expectation
            }

            @ParameterizedTest
            @ValueSource(ints = {0, 1, 2, 3, 4}) //checks all firestations and inexisting ones
            @DisplayName("3/7 phonelert - phone numbers - http://localhost:8080/phoneAlert?firestation=<firestation_number>\n" +
                    "Cette url doit retourner une liste des")
            void testGetRequest_EndPoint3_UP(int station) {
                String url = "http://localhost:" + port + "/phoneAlert?station=" + station;
                String response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                assertNotNull(response);
            }

            @ParameterizedTest
            @ValueSource(strings = {"", "city"})
            //checks with or without city parameters : so far this function and the whole program is insentive to this parameter
            @DisplayName("4/7 ENDPOINT - PEOPLE - Emails in the city")
            void testGetRequest_EndPoint4_UP(String city) {
                String url = "http://localhost:" + port + "/communityEmail?city=" + (city);
                String response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                assertNotNull(response);
            }

            @ParameterizedTest
            @ValueSource(strings = {"", "644 Gershwin Cir", "1509 Culver St"})
            //checks some addresses with null and existing values
            @DisplayName("5/7 ENDPOINT - People served by a firestation with all personal and med records - http://localhost:8080/fire?address=<address>")
            void testGetRequest_EndPoint5_UP(String address) {
                String url = "http://localhost:" + port + "/fire?address=" + (address);
                String response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                assertNotNull(response);
            }

            @ParameterizedTest
            @ValueSource(strings = {"0", "1", "2", "3", "4", "1,2,3,4"}) //checks all firestations and inexisting ones
            @DisplayName("6/7 ENDPOINT - People by address served by a given station - http://localhost:8080/flood/stations?stations=<a list of station_numbers>/")
            void testGetRequest_EndPoint6_UP(String station) {
                String url = "http://localhost:" + port + "/flood?stations=" + (station);
                String response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                assertNotNull(response);
            }

            @ParameterizedTest
            @CsvSource({",", "John,Boyd", "John,", ",Boyd"})
            //checks if is returned whether is given a person last or firstname or not
            @DisplayName("7/7 ENDPOINT - Individual Personal and Medical Records Info - http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>")
            void testGetRequest_EndPoint7_UP(String name, String lastname) {
                String url = "http://localhost:" + port + "/personInfo?firstName=" + (name) + "&lastName=" + (lastname);
                String response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                assertNotNull(response);
            }


        }
    }
}

