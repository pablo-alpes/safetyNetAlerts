package com.safety.net.alerts.service;

import com.safety.net.alerts.model.FullJoin;
import com.safety.net.alerts.model.MedicalRecords;
import com.safety.net.alerts.model.Persons;
import com.safety.net.alerts.model.PersonsMedicalRecordsJoin;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PeopleService {
    private MergeService mergeService;
    @Autowired
    public void setMyService(MergeService mergeService) {
        this.mergeService = mergeService;
    } //Injection of the Service
    /**
     * Chjildren at address = age <= 18
     *
     * @param address
     * @return list
     */
    public List<PersonsMedicalRecordsJoin> childrenAtAddress(String address) throws Exception {
        //Instantiation of models and data retrieval
        List<PersonsMedicalRecordsJoin> people = mergeService.PeopleMedicalJoin();
        return people.stream()
                .filter(children -> Integer.parseInt(children.getBirthdate()) <= 18)
                .filter(children -> children.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    /**
     * Adults with age > 18
     *
     * @param address
     * @return list
     */
    public List<PersonsMedicalRecordsJoin> famillyAtAddress(String address) throws Exception {
        //Instantiation of models and data retrieval
        List<PersonsMedicalRecordsJoin> people = mergeService.PeopleMedicalJoin();
        return people.stream()
                .filter(children -> Integer.parseInt(children.getBirthdate()) > 18)
                .filter(children -> children.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    /**
     * Individual Person Info based on
     * @param firstName and lastName
     * @return PersonMediRecord
     */
    public List<PersonsMedicalRecordsJoin> specificPerson(String firstName, String lastName) throws Exception {
        // optimiser les conditions if else dans le stream (skip?)
        //Instantiation of models and data retrieval
        List<PersonsMedicalRecordsJoin> people = mergeService.PeopleMedicalJoin();
        if (lastName.isEmpty()) {
            return people.stream()
            .filter(person -> person.getFirstName().equals(firstName))
             .collect(Collectors.toList());
        }
        else if (firstName.isEmpty()){
            return people.stream()
                    .filter(person -> person.getLastName().equals(lastName))
                    .collect(Collectors.toList());

        }
        else{
            return people.stream()
                    .filter(person -> person.getLastName().equals(lastName))
                    .filter(person -> person.getFirstName().equals(firstName))
                    .collect(Collectors.toList());
        }
    }

    /**
     * People at specific address and determines which firestation services them
     *
     * @param address
     * @return list of firestations serving
     */
    public List<FullJoin> peopleAtAddressWithFirestation(String address) throws Exception {
        //Instantiation of models and data retrieval
        List<FullJoin> people = mergeService.FullJoin();
        return people.stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    /**
     * People at specific address and determines which firestation services them
     *
     * @param station
     * @return list of firestations serving
     */
    public List<FullJoin> peopleAtStation(int station) throws Exception {
        //Instantiation of models and data retrieval
        List<FullJoin> people = mergeService.FullJoin();
        return people.stream()
                .filter(person -> person.getStation() == station)
                .collect(Collectors.toList());
    }

}
