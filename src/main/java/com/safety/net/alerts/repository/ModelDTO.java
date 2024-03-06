package com.safety.net.alerts.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.net.alerts.model.Firestations;
import com.safety.net.alerts.model.MedicalRecords;
import com.safety.net.alerts.model.PeopleAndClaims;
import com.safety.net.alerts.model.Persons;

import java.util.ArrayList;
import java.util.List;

public class ModelDTO {
    //Intialisation of instances
    private ObjectMapper mapper = new ObjectMapper();
    private PeopleAndClaims peopleList = new PeopleAndClaims();
    private JSONFileRetriever jsonData = new JSONFileRetriever();


    public List<Persons> retrieveAllPeople() throws JsonProcessingException {
        peopleList = jsonData.getAll();
        return peopleList.getPeople();
    }

    List<Firestations> retrieveAllFirestations() {
        peopleList = jsonData.getAll();
        return peopleList.getFirestations();
    }

    List<MedicalRecords> retrieveAllMedications() {
        peopleList = jsonData.getAll();
        return peopleList.getMedicalRecords();
    }

    List<String> retrievePeopleWithMeds() {
        //lier avec la clé name-lastname
        return null;
    }

    public List<String> retrieveFilteredBy(String filter) { //renvoie la réponse selon le critère demandé
        //Template to return the variable firstName from Persons property
        peopleList = jsonData.getAll();
        int nbpeople = (peopleList.getPeople()).size();
        List<String> listQuery = new ArrayList<String>();

        switch (filter) {
            case "email": { //all emails, all cities
                for (int i = 0; i < nbpeople; i++) {
                    String item = ((peopleList.getPeople()).get(i)).getEmail();
                    listQuery.add(item);
                }
            }
        }
        return listQuery;
    }

    List<String> saveToJson() {
        return null;
    }

    List<String> updateRegistry() {
        return null;
    }
}

