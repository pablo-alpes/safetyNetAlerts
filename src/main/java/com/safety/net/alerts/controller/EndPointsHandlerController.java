package com.safety.net.alerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.net.alerts.constants.People;
import com.safety.net.alerts.model.Persons;
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
     * @param city = string
     * @return json = List<emails>>
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
     * @param address = string
     * @return String children & family
     */
    @GetMapping(value = "/childAlert")
    public MappingJacksonValue childAtAddressAndFamily(@RequestParam String address) throws Exception {
        modelDTOImpl = new ModelDTOImpl(); //by design we do reload the JSON each time
        List output = mergeService.PeopleMedicalJoin();
        output = peopleService.childrenAtAddress(address);
        output.add(peopleService.famillyAtAddress(address));
        return modelDTOImpl.filterFields(output,"firstName","lastName","birthdate");
    }
}
