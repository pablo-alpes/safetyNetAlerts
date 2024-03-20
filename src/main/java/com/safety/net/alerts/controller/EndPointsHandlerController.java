package com.safety.net.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.net.alerts.model.Firestations;
import com.safety.net.alerts.model.FullJoin;
import com.safety.net.alerts.model.MedicalRecords;
import com.safety.net.alerts.model.Persons;
import com.safety.net.alerts.repository.ModelDAOImpl;
import com.safety.net.alerts.repository.ModelDTOImpl;
import com.safety.net.alerts.service.MergeService;
import com.safety.net.alerts.service.PeopleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class EndPointsHandlerController {
    @Autowired
    private ModelDTOImpl modelDTOImpl; //by design we do reload the JSON each time
    private MergeService mergeService;
    private PeopleService peopleService;
    @Autowired
    private ModelDAOImpl jsonData;

    @Autowired
    public void setMyService2(PeopleService peopleService) {
        this.peopleService = peopleService;
    } //Injection of the Service

    @Autowired
    public void setMyService(MergeService mergeService) {
        this.mergeService = mergeService;
    } //Injection of the Service

    /**
     * Gives the info for a firstname and / or lastname individual(s)
     * @param firstName, lastName
     * @return json = List<emails> (in all the cities, since no parametrisation included)
     */

    @GetMapping(value = "/personInfo")
    public MappingJacksonValue personInfo(@RequestParam String firstName, @RequestParam String lastName) throws Exception {
        //TODO -- To define the configuration by default to this filter, otherwise it will be needed to be passed for each endpoint.
        //See solution : https://stackoverflow.com/questions/66114813/jsonfilter-throws-error-no-filterprovider-configured-for-id && https://programmerfriend.com/filtering-json-springboot
        SimpleBeanPropertyFilter columnsToKeep = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filters = new SimpleFilterProvider().addFilter("PersonsMergedFilter", columnsToKeep); //id filter is set manually, to evolve once others cases are going to be developed
        MappingJacksonValue mapping = new MappingJacksonValue(peopleService.specificPerson(firstName, lastName)); //input merge table
        mapping.setFilters(filters);
        return mapping;
    }

    /**
     * Email list given a city
     * @param city
     * @return List < emails >
     */

    @GetMapping("/communityEmail")
    public MappingJacksonValue emailAllPeopleInCity(@RequestParam String city)  {
        modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time
        //Defining the filter to apply -> to refactor towards the DTO
        SimpleBeanPropertyFilter filterEmail = SimpleBeanPropertyFilter.filterOutAllExcept("email"); //column to keep
        FilterProvider filters = new SimpleFilterProvider().addFilter("PersonsFilter", filterEmail);
        MappingJacksonValue mapping = new MappingJacksonValue(modelDTOImpl.retrieveAllPeople());

        mapping.setFilters(filters);
        return mapping;
    }

    /**
     * Send all the children living in an address and its family adult members
     * @param address = string
     * @return String children & family
     */
    @GetMapping(value = "/childAlert")
    public MappingJacksonValue childAtAddressAndFamily(@RequestParam String address) throws Exception {
        try {
            modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time
            List output;
            output = peopleService.childrenAtAddress(address);
            if (output.isEmpty()) {
                return new MappingJacksonValue(null); // no children in the address
            } else {
                output.add(peopleService.famillyAtAddress(address));
                return modelDTOImpl.filterFields(output, "firstName", "lastName", "birthdate");
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Firestations servicing an address
     * @param address = string
     * @return given a specific address, then returns all families serviced by the firestation "int"
     */
    @GetMapping(value = "/fire")
    public MappingJacksonValue firestation(@RequestParam String address) throws Exception {
        modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time

        SimpleBeanPropertyFilter columnsToKeep = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filters = new SimpleFilterProvider().addFilter("FullJoin", columnsToKeep); //id filter is set manually, to evolve once others cases are going to be developed
        MappingJacksonValue mapping = new MappingJacksonValue(peopleService.peopleAtAddressWithFirestation(address)); //input to run research
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping(value = "/flood")
    public MappingJacksonValue peopleByFirestation(@RequestParam List<Integer> stations) throws Exception {
        modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time

        SimpleBeanPropertyFilter columnsToKeep = SimpleBeanPropertyFilter.serializeAllExcept("address","station","peopleCount");
        FilterProvider filters = new SimpleFilterProvider().addFilter("FullJoin", columnsToKeep); //id filter is set manually, to evolve once others cases are going to be developed
        MappingJacksonValue mapping = new MappingJacksonValue(peopleService.peopleAtAddressWithFirestationGroupedByAddress((stations))); //input to run research
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping(value = "/firestation")
    public MappingJacksonValue peopleByASingleFirestation(@RequestParam int stationNumber) throws Exception {
        modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time

        SimpleBeanPropertyFilter columnsToKeep = SimpleBeanPropertyFilter.serializeAllExcept("allergies","medications","birthdate", "peopleCount");
        FilterProvider filters = new SimpleFilterProvider().addFilter("FullJoin", columnsToKeep); //id filter is set manually, to evolve once others cases are going to be developed

        MappingJacksonValue mapping = new MappingJacksonValue(peopleService.peopleAtStation(stationNumber)); //input to run research
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping(value = "/phoneAlert")
    public MappingJacksonValue phoneByFirestation(@RequestParam int station) throws Exception {
        modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time

        SimpleBeanPropertyFilter columnsToKeep = SimpleBeanPropertyFilter.filterOutAllExcept("phone");
        FilterProvider filters = new SimpleFilterProvider().addFilter("FullJoin", columnsToKeep); //id filter is set manually, to evolve once others cases are going to be developed
        MappingJacksonValue mapping = new MappingJacksonValue(peopleService.peopleAtStation(station)); //input to run research
        mapping.setFilters(filters);
        return mapping;
    }

    //CRUD operations // Flow: Controller calls -> Service (calls) -> DAO/DTO and

    //DELETE OPERATIONS FOR EACH ENTITY
    @DeleteMapping(value="/person/{firstName}" +"&"+ "{lastName}")
    public void personDeleteRecord(
            @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName
    ) throws IOException {
        //modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time
        jsonData.saveAll(modelDTOImpl.deleteRecord(1,firstName, lastName));
    }

    @DeleteMapping("/medicalRecord/{firstName}" + "&" + "{lastName}")
    //finetune params parsing to rather have key vale http://localhost:8080/medicalRecord/Pablo&Miranda
    //?parama=a&paramb=a
    public void medicalDeleteRecord(
            @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName
    ) throws IOException {
        //modelDTOImpl = new ModelDTOImpl();
        jsonData.saveAll(modelDTOImpl.deleteRecord(3,firstName, lastName));
    }

    @DeleteMapping("/firestation/{param}")
    public void firestationDeleteRecord(
            @PathVariable("param")
            String param
    ) throws IOException {
        //modelDTOImpl = new ModelDTOImpl();
        jsonData.saveAll(modelDTOImpl.deleteRecord(2, param));
    }

    //UPDATE OPERATIONS FOR EACH ENTITY - PUT
    @PutMapping(value="/medicalRecord", consumes = {"application/json"})
    public void medicalUpdateRecord(
            @RequestBody String medical
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MedicalRecords medicalRecords = mapper.readValue(medical, MedicalRecords.class);
        //modelDTOImpl = new ModelDTOImpl();
        jsonData.saveAll(modelDTOImpl.updateRecord(3, medicalRecords.getFirstName(), medicalRecords.getLastName(), medicalRecords.getBirthdate(), medicalRecords.getMedications().toString(), medicalRecords.getAllergies().toString()));
    }
    @PutMapping(value="/firestation", consumes = {"application/json"}) //TODO -- REVIEW THE BUSINESS LOGIC.
    public void firestationUpdateRecord(
            @RequestBody String firestation
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Firestations firestations = mapper.readValue(firestation, Firestations.class);
        //modelDTOImpl = new ModelDTOImpl();
        jsonData.saveAll(modelDTOImpl.updateRecord(2, firestations.getAddress(), String.valueOf(firestations.getStation())));
        //{ "address":"1509 Culver St", "station":"3" },
    }

    @PutMapping(value="/person", consumes = {"application/json"})
    public void personUpdateRecord(
            @RequestBody String individual
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Persons person = mapper.readValue(individual, Persons.class);
        //modelDTOImpl = new ModelDTOImpl();
        jsonData.saveAll(modelDTOImpl.updateRecord(1, person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(), person.getZip(), person.getPhone(), person.getEmail()));
    }


    //ADD OPERATION FOR EACH ENTITY - POST
    @PostMapping(value="/medicalRecord", consumes = {"application/json"})
    public void medicalSaveRecord(
            @RequestBody String medical
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MedicalRecords medicalRecords = mapper.readValue(medical, MedicalRecords.class);
        //modelDTOImpl = new ModelDTOImpl();
        jsonData.saveAll(modelDTOImpl.addRecord(3, medicalRecords.getFirstName(), medicalRecords.getLastName(), medicalRecords.getBirthdate(), medicalRecords.getMedications().toString(), medicalRecords.getAllergies().toString()));
    }
    @PostMapping(value="/firestation", consumes = {"application/json"})
    public void firestationSaveRecord(
            @RequestBody String firestation
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Firestations firestations = mapper.readValue(firestation, Firestations.class);
        //modelDTOImpl = new ModelDTOImpl();
        jsonData.saveAll(modelDTOImpl.addRecord(2, firestations.getAddress(), String.valueOf(firestations.getStation())));
    }

    @PostMapping(value="/person", consumes = {"application/json"})
    public void personSaveRecord(
            @RequestBody String individual
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Persons person = mapper.readValue(individual, Persons.class);
        //modelDTOImpl = new ModelDTOImpl();
        jsonData.saveAll(modelDTOImpl.addRecord(1, person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(), person.getZip(), person.getPhone(), person.getEmail()));
    }

}


