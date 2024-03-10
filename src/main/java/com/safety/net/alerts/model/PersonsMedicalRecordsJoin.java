package com.safety.net.alerts.model;

public class PersonsMedicalRecordsJoin {
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private Object medications;
    private Object allergies;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getMedications() {
        return medications;
    }

    public void setMedications(Object medications) {
        this.medications = medications;
    }

    public Object getAllergies() {
        return allergies;
    }

    public void setAllergies(Object allergies) {
        this.allergies = allergies;
    }
}
