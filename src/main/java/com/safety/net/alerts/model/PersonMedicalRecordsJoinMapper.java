package com.safety.net.alerts.model;

import com.safety.net.alerts.service.CalculateAge;
import com.safety.net.alerts.service.PeopleService;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PersonMedicalRecordsJoinMapper {
    @Mapping(target = "firstName", source = "person.firstName")
    @Mapping(target = "lastName", source = "person.lastName")
    @Mapping(source = "person.address", target = "address")
    @Mapping(source = "medicalRecord.birthdate", target = "birthdate", qualifiedByName = "age")
    @Mapping(source = "medicalRecord.medications", target = "medications")
    @Mapping(source = "medicalRecord.allergies", target = "allergies")
    PersonsMedicalRecordsJoin mergeRecord(MedicalRecords medicalRecord, Persons person);
    @Named("age")
    public static int age(String birthdate){
        return new CalculateAge().convertDate(birthdate);
    }
}





