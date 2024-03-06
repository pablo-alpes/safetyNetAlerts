package com.safety.net.alerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safety.net.alerts.model.PeopleAndClaims;
import com.safety.net.alerts.repository.ModelDTO;
import com.safety.net.alerts.view.DisplayEndPoints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndPointsHandlerController {
    @GetMapping("/communityEmail/")
    //Endpoint http://localhost:8080/communityEmail?city=<city>
    //Liste Adresses mail de tous les habitants de la ville
    public String test() throws JsonProcessingException {
        ModelDTO modelDTO = new ModelDTO(); //by design we do reload the JSON each time
        DisplayEndPoints display = new DisplayEndPoints();
        String formatOutput = display.listToJson(modelDTO.retrieveFilteredBy("email").toString());
        return formatOutput;
    }

}

