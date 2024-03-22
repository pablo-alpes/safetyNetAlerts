package com.safety.net.alerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.net.alerts.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;


@Service
public class ModelDTOImpl {
    //Intialisation of instances
    private ObjectMapper mapper = new ObjectMapper();
    private PeopleAndClaims peopleList = new PeopleAndClaims();

    private ModelDAOImpl jsonData = new ModelDAOImpl();

    public List<Persons> retrieveAllPeople() throws IOException {
        peopleList = jsonData.getAll();
        return peopleList.getPeople();
    }

    public List<Firestations> retrieveAllFirestations() throws IOException {
        peopleList = jsonData.getAll();
        return peopleList.getFirestations();
    }

    public List<MedicalRecords> retrieveAllMedications() throws IOException {
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
     * Performs deletion of the entity type people, firestation or meds for the given parameters and persists the operations into the json.
     * @param int type
     * @param String firstname, lastName or station/adress
     * @throws IOException
     * @returns void
     *
     */
    public PeopleAndClaims deleteRecord(int type, String... args) throws IOException {
        int id;
        switch (type) {
            case 1: //people
                List<Persons> people = retrieveAllPeople();
                id = personID(args[0], args[1]); //id removal based on firstName lastName
                if (id == -1) return null;
                people.remove(id); //removal for the current retrieved list
                peopleList.setPersons(people); // we update the whole list with the person that has been deleted
                break;
            case 2: //firestation
                List<Firestations> firestations = retrieveAllFirestations();
                id = stationID(args[0]); //id removal based address or station
                if (id == -1) return null;
                firestations.remove(id); //removal for the current retrieved list
                peopleList.setFirestations(firestations);
                break;
            case 3: //meds
                List<MedicalRecords> medicalRecords = retrieveAllMedications();
                id = medicalID(args[0], args[1]); //id removal based on firstName lastName
                if (id == -1) return null;
                medicalRecords.remove(id); //removal for the current retrieved list
                peopleList.setMedicalRecords(medicalRecords); // we update the whole list with the person that has been deleted
                break;
            default:
                return null;
        }
        return peopleList;
        //jsonData.saveAll(peopleList);
    }

    public PeopleAndClaims addRecord(int type, String... args) throws IOException {
        switch (type) {
            case 1: //people
                List<Persons> people = retrieveAllPeople(); //we get all the objects in the Persons class, focus of the test
                people.add(new Persons(args[0], args[1], args[2], args[3], args[4], args[5], args[6])); //ADDs the new record type Persons
                peopleList.setPersons(people); // we update the whole list
                break;
            case 2: //firestation
                List<Firestations> firestations = retrieveAllFirestations();
                firestations.add(new Firestations(args[0], Integer.parseInt(args[1]))); //we add the new record type firestation
                peopleList.setFirestations(firestations); // we update the whole list
                break;
            case 3: //meds
                List<MedicalRecords> medicalRecords = retrieveAllMedications();
                medicalRecords.add(new MedicalRecords(args[0], args[1], args[2], args[3], args[4])); //we add the new record type Medical
                peopleList.setMedicalRecords(medicalRecords); // we update the whole list
                break;
            default:
                return null;
        }
        return peopleList;
        //jsonData.saveAll(peopleList);
    }

    public PeopleAndClaims updateRecord(int type, String... args) throws IOException {
        int id;
        switch (type) {
            case 1: //people
                List<Persons> people = retrieveAllPeople();
                id = personID(args[0], args[1]); //id removal based on firstName lastName
                if (id == -1) return null;
                people.set(id, new Persons(args[0], args[1], args[2], args[3], args[4], args[5], args[6])); //removal for the current retrieved list
                peopleList.setPersons(people); // we update the whole list with the person that has been deleted
                break;
            case 2: //firestation
                List<Firestations> firestations = retrieveAllFirestations();
                id = stationID(args[0]); //id removal based address or station
                if (id == -1) return null;
                firestations.set(id, new Firestations(args[0], Integer.parseInt(args[1]))); //allows only for a change in the address for a specific station
                peopleList.setFirestations(firestations);
                break;
            case 3: //meds
                List<MedicalRecords> medicalRecords = retrieveAllMedications();
                id = medicalID(args[0], args[1]); //id removal based on firstName lastName
                if (id == -1) return null;
                medicalRecords.set(id, new MedicalRecords(args[0], args[1], args[2], args[3], args[4])); //removal for the current retrieved list
                peopleList.setMedicalRecords(medicalRecords); // we update the whole list with the person that has been deleted
                break;
            default:
                return null;
        }
        return peopleList;
        //jsonData.saveAll(peopleList);
    }


    //returns the id pos in the list of the List<object> (person, station or medical record) for the individual. Serves to edit or delete the item.
    public int personID(String firstName, String lastName) throws IOException {
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

    public int medicalID(String firstName, String lastName) throws IOException {
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

    public int stationID(String param) throws IOException {
        List<Firestations> firestations = retrieveAllFirestations();
        int positionIndex = IntStream.range(0, firestations.size())
                //.filter(index -> firestations.get(index).getAddress().equals(param) || (firestations.get(index).getStation() == Integer.parseInt(param)))
                .filter(index -> firestations.get(index).getAddress().equals(param))
                .findFirst()
                .orElse(-1);
        if (positionIndex >= 0) {
            return positionIndex;
        }
        else {
            return -1;
        }
    }


}
