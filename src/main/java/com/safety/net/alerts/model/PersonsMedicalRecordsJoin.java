package com.safety.net.alerts.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Configuration;


@JsonFilter("PersonsMergedFilter")
public class PersonsMedicalRecordsJoin {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("address")
    private String address;
    @JsonProperty("birthdate")
    private String birthdate;
    @JsonProperty("medications")
    private Object medications;
    @JsonProperty("allergies")
    private Object allergies;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("phone")
    String phone;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getMedications() {
        return medications;
    }

    public void setMedications(Object medications) {
        this.medications = medications;
    }

    public Object getAllergies() {
        return allergies;
    }

    public void setAllergies(Object allergies) {
        this.allergies = allergies;
    }
    @Configuration
    //TODO -- Define the by default configuration for this filter (jackson filter)
    public class FilterConfiguration {
        public FilterConfiguration(ObjectMapper objectMapper) {
            FilterProvider columnsToKeep = new SimpleFilterProvider().setDefaultFilter(SimpleBeanPropertyFilter.serializeAll());
            objectMapper.setFilterProvider(columnsToKeep);

        }
    }
}
