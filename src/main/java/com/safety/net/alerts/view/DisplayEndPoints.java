package com.safety.net.alerts.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class DisplayEndPoints {
    public String listToJson(String list) throws JsonProcessingException {
        //instantiation
        ObjectMapper mapper = new ObjectMapper();
        //write
        return (mapper.writeValueAsString(list));
    }
}
