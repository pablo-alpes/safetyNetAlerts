package com.safety.net.alerts.model;
import org.mapstruct.*;
@Mapper
public interface FullJoinMapper {
    @Mapping(source = "personsMedicalRecordsJoin.firstName", target = "firstName")
    @Mapping(source = "personsMedicalRecordsJoin.lastName", target = "lastName")
    @Mapping(source = "personsMedicalRecordsJoin.address", target = "address")
    @Mapping(source = "personsMedicalRecordsJoin.medications", target = "medications")
    @Mapping(source = "personsMedicalRecordsJoin.allergies", target = "allergies")
    @Mapping(source = "personsMedicalRecordsJoin.phone", target = "phone")
    @Mapping(source = "personsMedicalRecordsJoin.birthdate", target = "birthdate")
    @Mapping(source = "station", target = "station")

    FullJoin mergeRecord(PersonsMedicalRecordsJoin personsMedicalRecordsJoin, int station) throws Exception;

}

