package com.safety.net.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.net.alerts.model.FullJoin;
import com.safety.net.alerts.model.PersonsMedicalRecordsJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class PeopleService {
    private ObjectMapper mapper = new ObjectMapper();
    private MergeService mergeService = new MergeService(mapper);

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
    public List<PersonsMedicalRecordsJoin> childrenAtAddress(String address) {
        //Instantiation of models and data retrieval
        List<PersonsMedicalRecordsJoin> people = mergeService.PeopleMedicalJoin();
        return people.stream()
                .filter(children -> Integer.parseInt(children.getBirthdate()) <= 18)
                .filter(children -> children.getAddress().equals(address))
                .collect(toList());
    }

    /**
     * Adults with age > 18
     *
     * @param address
     * @return list
     */
    public List<PersonsMedicalRecordsJoin> famillyAtAddress(String address) {
        //Instantiation of models and data retrieval
        List<PersonsMedicalRecordsJoin> people = mergeService.PeopleMedicalJoin();
        return people.stream()
                .filter(children -> Integer.parseInt(children.getBirthdate()) > 18)
                .filter(children -> children.getAddress().equals(address))
                .collect(toList());
    }

    /**
     * Individual Person Info based on
     * @param firstName and lastName
     * @return PersonMediRecord
     */
    public List<PersonsMedicalRecordsJoin> specificPerson(String firstName, String lastName) throws Exception {
        //Instantiation of models and data retrieval
        List<PersonsMedicalRecordsJoin> people = mergeService.PeopleMedicalJoin();

        return people.stream()
                .filter(person -> person.getFirstName().equals(firstName) || person.getFirstName().isEmpty())
                .filter(person -> person.getLastName().equals(lastName) || person.getLastName().isEmpty())
                .collect(toList());
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
                .collect(toList());
    }

    /**
     * People at specific address and determines which firestation services them
     *
     * @param station
     * @return list of firestations serving
     */
    public Map<List<Map<String, Integer>>,List<FullJoin>> peopleAtStation(int station) throws Exception {
        //Instantiation of models and data retrieval
        List<FullJoin> people = mergeService.FullJoin();
        PeopleService peopleService = new PeopleService();
        people = people.stream()
                .filter(person -> person.getStation() == station)
                .collect(toList());

        List<Map<String, Integer>> peopleCount =
                Stream.of(Map.of("children", peopleService.countChildren(station)), Map.of("adults", peopleService.countAdults(station))).toList();

        people.forEach(person-> person.setPeopleCount(peopleCount));

        return people.stream()
                .collect(groupingBy(FullJoin::getPeopleCount));
    }

    /**
     * Chjildren at station = age <= 18
     *
     * @param station
     * @return list
     */
    public int countChildren(int station) throws Exception {
        //Instantiation of models and data retrieval
        List<FullJoin> people = mergeService.FullJoin();
        return people.stream()
                .filter(children -> Integer.parseInt(children.getBirthdate()) <= 18)
                .filter(children -> children.getStation() == station)
                .toList().size();
    }

    public int countAdults(int station) throws Exception {
        //Instantiation of models and data retrieval
        List<FullJoin> people = mergeService.FullJoin();
        return people.stream()
                .filter(children -> Integer.parseInt(children.getBirthdate()) > 18)
                .filter(children -> children.getStation() == station)
                .toList().size();
    }

    /**
     * People at specific address and determines which firestation services them
     *
     * @param station
     * @return list of firestations served by address for a given station
     */
    public Map<String,List<FullJoin>> peopleAtAddressWithFirestationGroupedByAddress(List<Integer> station) throws Exception {
        //Instantiation of models and data retrieval
        List<FullJoin> people = mergeService.FullJoin();
        return people.stream()
                .filter(person -> station.contains(person.getStation()))
                .collect(groupingBy(FullJoin::getAddress, toList()));
    }


}
