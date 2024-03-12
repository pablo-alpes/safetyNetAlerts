package com.safety.net.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.net.alerts.model.*;
import com.safety.net.alerts.repository.ModelDAOImpl;
import com.safety.net.alerts.repository.ModelDTOImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MergeService {
    private ObjectMapper mapper = new ObjectMapper();

    public MergeService(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    private ModelDAOImpl jsonData;
    private ModelDTOImpl modelDTOImpl;
    private PersonMedicalRecordsJoinMapper personsjoinmapper = Mappers.getMapper(PersonMedicalRecordsJoinMapper.class);

    private FullJoinMapper fullJoinMapper = Mappers.getMapper(FullJoinMapper.class);

    /**
     * PeopleMedicalJoin Performs the merge of all existing json data in the file
     * @params none
     * @return void
     * @throws Exception
     */
    public List<PersonsMedicalRecordsJoin> PeopleMedicalJoin() throws Exception {
        //Instantiation of models and data retrieval
        modelDTOImpl = new ModelDTOImpl();
        List<MedicalRecords> medicalRecords = modelDTOImpl.retrieveAllMedications();
        List<Persons> people = modelDTOImpl.retrieveAllPeople();

        // Process the list and filter based on the matching of keys first - last name
        return medicalRecords.stream()
                .map(medicalRecord -> {
                    int idPersonMatch = personIdFinder(medicalRecord); //passing the right person item based on medicalrecord primary key
                    if (idPersonMatch >= 0) {
                        Persons person = people.get(idPersonMatch);
                        return personsjoinmapper.mergeRecord(medicalRecord, person);
                    }
                    else return null;
                })
                .collect(Collectors.toList());
    }

    /**
     * PersonIdFinder returns the ID for the medicalrecord line indicated as origin for the "persons list"
     * @param medicalRecord
     * @return int : id / -1 if not found
     */
    private int personIdFinder(MedicalRecords medicalRecord) {
        List<Persons> people = modelDTOImpl.retrieveAllPeople();
        int positionIndex = IntStream.range(0, people.size())
                .filter(pos -> people.get(pos).getFirstName().equals(medicalRecord.getFirstName()) &&
                        people.get(pos).getLastName().equals(medicalRecord.getLastName()))
                .findFirst()
                .orElse(-1);

        if (positionIndex >= 0) {
            return positionIndex;
        }
        else {
            return -1;
        }
    }

    /**
     * FullJoin Performs the merge of all existing tables in the json file
     * @params none
     * @return List<FullJoin>
     * @throws Exception
     */
    public List<FullJoin> FullJoin() throws Exception {
        //Instantiation of models and data retrieval
        modelDTOImpl = new ModelDTOImpl();
        List<PersonsMedicalRecordsJoin> personsMedicalRecordsJoin = PeopleMedicalJoin();
        List<Firestations> firestations = modelDTOImpl.retrieveAllFirestations();

        // Process the list and filter based on the matching of keys address - station for each individual
        return personsMedicalRecordsJoin.stream()
                .map(person -> {
                    int station = firestations.get(stationIdFinder(person.getAddress())).getStation(); //get id for which station correspond to the address person.getAddress()
                    return fullJoinMapper.mergeRecord(person, station);
                })
                .collect(Collectors.toList());
    }

    /**
     * AddressIdFinder returns the ID for the firestation
     * @param address
     * @return int : id / -1 if not found
     */
    private int stationIdFinder(String address) {
        List<Firestations> firestations = modelDTOImpl.retrieveAllFirestations();
        int positionIndex = IntStream.range(0, firestations.size())
                .filter(pos -> firestations.get(pos).getAddress().equals(address))
                .findFirst() //assuming: an address has a single firestation allocated
                .orElse(-1);

        if (positionIndex >= 0) {
            return positionIndex;
        }
        else {
            return -1;
        }
    }

}
