package com.safety.net.alerts.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.net.alerts.model.FullJoin;
import com.safety.net.alerts.repository.ModelDTOImpl;
import com.safety.net.alerts.service.MergeService;
import com.safety.net.alerts.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EndPointsHandlerController {
    private ModelDTOImpl modelDTOImpl; //by design we do reload the JSON each time
    private MergeService mergeService;
    private PeopleService peopleService;

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
        modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time
        List output = mergeService.PeopleMedicalJoin();
        output = peopleService.childrenAtAddress(address);
        if (output.isEmpty()) {
            return null; // no children in the address
        }
        else {
            output.add(peopleService.famillyAtAddress(address));
            return modelDTOImpl.filterFields(output, "firstName", "lastName", "birthdate");
        }
    }

    /**
     * Firestations servicing an address
     * @param address = string
     * @return given a specific address, then returns all families serviced by the firestation "int"
     */
    @GetMapping(value = "/firestation")
    public MappingJacksonValue firestation(@RequestParam String address) throws Exception {
        modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time

        SimpleBeanPropertyFilter columnsToKeep = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filters = new SimpleFilterProvider().addFilter("FullJoin", columnsToKeep); //id filter is set manually, to evolve once others cases are going to be developed
        MappingJacksonValue mapping = new MappingJacksonValue(peopleService.peopleAtAddressWithFirestation(address)); //input to run research
        mapping.setFilters(filters);
        return mapping;
    }

}
