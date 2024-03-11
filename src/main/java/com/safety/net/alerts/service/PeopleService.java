package com.safety.net.alerts.service;

import com.safety.net.alerts.model.PersonsMedicalRecordsJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeopleService {
    private MergeService mergeService;
    @Autowired
    public void setMyService(MergeService mergeService) {
        this.mergeService = mergeService;
    } //Injection of the Service
    /**
     * Chjildren at address = age < 18
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
     * Adults with age >= 18
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
}
