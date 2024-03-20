package com.safety.net.alerts.repository; //do i need also to expose to the level of project ?

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.net.alerts.constants.JSONFile;
import com.safety.net.alerts.model.*;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * InputsDTOController -- Reads a json file and parses it to a List of Lists of the Entities
 * in the model according to its properties (Firestations, Persons, MedicalRecords).
 *
 * @return PeopleList object with all the data from the JSON. It is sent to the Model to decouple by main Entities
 */
@Service
public class ModelDAOImpl {
    //Object Instantiations
    PeopleAndClaims peopleList = new PeopleAndClaims();
    ObjectMapper mapper = new ObjectMapper();

    public PeopleAndClaims getAll() {
        try {
            //JSON to Java Object deserialisation to a List of Lists 
            peopleList = mapper.readValue(new File(JSONFile.JSONDataPath), PeopleAndClaims.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peopleList;
    }

    public void saveAll(PeopleAndClaims people) throws IOException {
        try {
            SimpleModule module = new SimpleModule();
            module.addSerializer(PeopleAndClaims.class, new CustomPeopleAndClaimsSerializer());
            mapper.registerModule(module);
            mapper.writeValue(new File(JSONFile.JSONDataPath), people);
        } catch (IOException e) {
        }
    }

        //this method ensures we keep the initial json structure
        private static class CustomPeopleAndClaimsSerializer extends JsonSerializer<PeopleAndClaims> {
        @Override
        public void serialize(PeopleAndClaims peopleAndClaims, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            //jsonGenerator.writeStartArray();
            jsonGenerator.writeArrayFieldStart("persons");
            for (Persons p : peopleAndClaims.getPeople()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeObjectField("firstName",p.getFirstName());
                jsonGenerator.writeObjectField("lastName",p.getLastName());
                jsonGenerator.writeObjectField("address",p.getAddress());
                jsonGenerator.writeObjectField("city",p.getCity());
                jsonGenerator.writeObjectField("zip",p.getZip());
                jsonGenerator.writeObjectField("phone",p.getPhone());
                jsonGenerator.writeObjectField("email",p.getEmail());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();

            //jsonGenerator.writeStartArray();
            jsonGenerator.writeArrayFieldStart("firestations");
            for (Firestations f : peopleAndClaims.getFirestations()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeObjectField("station",f.getStation());
                jsonGenerator.writeObjectField("address",f.getAddress());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            //jsonGenerator.writeStartArray();
            jsonGenerator.writeArrayFieldStart("medicalrecords");

            for (MedicalRecords m : peopleAndClaims.getMedicalRecords()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeObjectField("firstName", m.getFirstName());
                jsonGenerator.writeObjectField("lastName",m.getLastName());
                jsonGenerator.writeObjectField("birthdate",m.getBirthdate());
                jsonGenerator.writeObjectField("medications",m.getMedications());
                jsonGenerator.writeObjectField("allergies",m.getAllergies());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();

        }
    }
}

