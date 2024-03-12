package com.safety.net.alerts.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.net.alerts.model.*;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModelDTOImpl {
    //Intialisation of instances
    private ObjectMapper mapper = new ObjectMapper();
    private PeopleAndClaims peopleList = new PeopleAndClaims();
    private ModelDAOImpl jsonData = new ModelDAOImpl();

    public List<Persons> retrieveAllPeople() {
        peopleList = jsonData.getAll();
        return peopleList.getPeople();
    }

    public List<Firestations> retrieveAllFirestations() {
        peopleList = jsonData.getAll();
        return peopleList.getFirestations();
    }

    public List<MedicalRecords> retrieveAllMedications() {
        peopleList = jsonData.getAll();
        return peopleList.getMedicalRecords();
    }

    public MappingJacksonValue filterFields(List<PersonsMedicalRecordsJoin> listToFilter, String... arg) { //arguments to filter out
        SimpleBeanPropertyFilter columnsToKeep = SimpleBeanPropertyFilter.filterOutAllExcept(arg);
        FilterProvider filters = new SimpleFilterProvider().addFilter("PersonsMergedFilter", columnsToKeep); //id filter is set manually, to evolve once others cases are going to be developed
        MappingJacksonValue mapping = new MappingJacksonValue(listToFilter); //input merge table
        mapping.setFilters(filters);
        return mapping;
    }

    public List<String> saveToJson() {
        return null;
    }

    public List<String> updateRegistry() {
        return null;
    }
}


