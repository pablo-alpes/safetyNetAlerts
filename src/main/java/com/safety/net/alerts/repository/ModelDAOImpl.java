package com.safety.net.alerts.repository; //do i need also to expose to the level of project ?

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.net.alerts.constants.JSONFile;
import com.safety.net.alerts.model.PeopleAndClaims;

import java.io.File;
import java.io.IOException;

/**
 * InputsDTOController -- Reads a json file and parses it to a List of Lists of the Entities
 * in the model according to its properties (Firestations, Persons, MedicalRecords).
 *
 * @return PeopleList object with all the data from the JSON. It is sent to the Model to decouple by main Entities
 */
public class ModelDAOImpl {
    public PeopleAndClaims getAll() {
        PeopleAndClaims peopleList = new PeopleAndClaims();
        try {
            //Object Instantiations
            ObjectMapper mapper = new ObjectMapper();
            peopleList = new PeopleAndClaims();

            //JSON to Java Object deserialisation to a List of Lists 
            peopleList = mapper.readValue(new File(JSONFile.JSONDataPath), PeopleAndClaims.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peopleList;
    }
}

