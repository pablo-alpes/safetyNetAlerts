package com.safety.net.alerts.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

//{ "firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984", "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"] },


public class MedicalRecords {

    public MedicalRecords(String firstName, String lastName, String birthdate, Object medications, Object allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public MedicalRecords() {
    }

    @JsonProperty("firstName")
    String firstName;
    @JsonProperty("lastName")
    String lastName;
    @JsonProperty("birthdate")
    String birthdate;
    @JsonProperty("medications")
    Object medications;
    @JsonProperty("allergies")
    Object allergies;

    //getters

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Object getAllergies() {
        //pris comme generique, list de strings
        return allergies;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public Object getMedications() {
        //pris comme generique, list de strings
        return medications;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setMedications(Object medications) {
        this.medications = medications;
    }

    public void setAllergies(Object allergies) {
        this.allergies = allergies;
    }



    /**verride
    public String toString() {
        return
        //{ "firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984", "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"] },
    }
    */
}
