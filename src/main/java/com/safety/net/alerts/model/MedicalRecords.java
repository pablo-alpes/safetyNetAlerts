package com.safety.net.alerts.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

//{ "firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984", "medications":["aznol:350mg", "hydrapermazol:100mg"], "allergies":["nillacilan"] },
public class MedicalRecords {
    @JsonProperty("firstName")
    String firstName;
    @JsonProperty("lastName")
    String lastName;
    @JsonProperty("birthdate")
    String birthdate;
    //TODO -- conversion to format yyyymmdd to type it as Date
    //com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `java.util.Date` from String "03/06/1984":
    // not a valid representation (error: Failed to parse Date value '03/06/1984': Cannot parse date "03/06/1984":
    // not compatible with any of standard forms ("yyyy-MM-dd'T'HH:mm:ss.SSSX", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"))
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
}
