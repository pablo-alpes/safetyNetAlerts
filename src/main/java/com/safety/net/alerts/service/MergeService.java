package com.safety.net.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.net.alerts.model.MedicalRecords;
import com.safety.net.alerts.model.PersonMedicalRecordsJoinMapper;
import com.safety.net.alerts.model.Persons;
import com.safety.net.alerts.model.PersonsMedicalRecordsJoin;
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
}
