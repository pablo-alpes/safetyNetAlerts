package com.safety.net.alerts.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class PeopleAndClaims {
    @JsonProperty("persons")
    private List<Persons> persons;
    @JsonProperty("firestations")
    private List<Firestations> firestations;

    @JsonProperty("medicalrecords")
    private List<MedicalRecords> medicalRecords;

    //getters and setters

    public List<Persons> getPeople() {
        return persons;
    }
    public List<Firestations> getFirestations() {
        return firestations;
    }
    public List<MedicalRecords> getMedicalRecords() {
        return medicalRecords;
    }
    public List<Persons> getPersons() { return persons;    }

    public void setFirestations(List<Firestations> firestations) {
        this.firestations = firestations;
    }

    public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public void setPersons(List<Persons> persons) {
        this.persons = persons;
    }

    public void setPeople(List<Persons> persons) {
        this.persons = persons;
    }




}

