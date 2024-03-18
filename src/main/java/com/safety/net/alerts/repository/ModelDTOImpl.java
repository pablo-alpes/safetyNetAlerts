package com.safety.net.alerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.net.alerts.model.*;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Locale.filter;

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

    /**
     * Performs deletion of the person firstName, lastName from the json and updates the change in the json file
     * @param firstName
     * @param lastName
     * @throws IOException
     * @returns void
     *
     */
    public void deleteRecord(String firstName, String lastName) throws IOException {
        List<Persons> people = retrieveAllPeople(); //we get all the objects in the Persons class

        //id removal based on firstName lastName
        int personIdentifier = personID(firstName, lastName);
        if (personIdentifier == -1) return;

        people.remove(personIdentifier); //removal for the current retrieved list
        peopleList.setPersons(people); // we update the whole list with the person that has been deleted
        jsonData.saveAll(peopleList);
    }


    //returns the id pos in the list of the List<object> (person, station or medical record) for the individual. Serves to edit or delete the item.
    public int personID(String firstName, String lastName) {
        List<Persons> persons = retrieveAllPeople();
        int positionIndex = IntStream.range(0,persons.size())
                .filter(index -> persons.get(index).getFirstName().equals(firstName) && persons.get(index).getLastName().equals(lastName))
                .findFirst()
                .orElse(-1);
        if (positionIndex >= 0) {
            return positionIndex;
        }
        else {
            return -1;
        }
    }

    public int medicalID(String firstName, String lastName) {
        List<MedicalRecords> medicalRecords = retrieveAllMedications();
        int positionIndex = IntStream.range(0, medicalRecords.size())
                .filter(index -> medicalRecords.get(index).getFirstName().equals(firstName) && medicalRecords.get(index).getLastName().equals(lastName))
                .findFirst()
                .orElse(-1);
        if (positionIndex >=0) {
            return positionIndex;
        }
        else {
            return -1;
        }
    }


}
