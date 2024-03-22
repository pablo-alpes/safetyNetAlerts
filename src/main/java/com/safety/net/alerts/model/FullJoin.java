package com.safety.net.alerts.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@JsonFilter("FullJoin")
@JsonPropertyOrder(value = {"firstName", "lastName","medications","allergies", "address", "phone"}, alphabetic = true)
public class FullJoin  {
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
    @JsonProperty("station")
    int station;
    @JsonProperty("phone")
    String phone;
    @JsonProperty("peopleCount")
    List<Map<String, Integer>> peopleCount;

    public List<Map<String, Integer>> getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(List<Map<String, Integer>> peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public int getStation() { return station; }

    public void setStation(int station) { this.station = station; }

    public void setAllergies(Object allergies) {
        this.allergies = allergies;
    }



    public FullJoin(String firstName, String lastName, String address, String birthdate, Object medications, Object allergies, int station, String phone, List<Map<String, Integer>> peopleCount, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
        this.station = station;
        this.phone = phone;
        this.peopleCount = peopleCount;
    }

    public FullJoin() { }

    @Configuration
    //TODO -- Define the by default configuration for this filter (jackson filter)
    public class FilterConfiguration {
        public FilterConfiguration(ObjectMapper objectMapper) {
            FilterProvider columnsToKeep = new SimpleFilterProvider().setDefaultFilter(SimpleBeanPropertyFilter.serializeAll());
            objectMapper.setFilterProvider(columnsToKeep);

        }
    }
}
