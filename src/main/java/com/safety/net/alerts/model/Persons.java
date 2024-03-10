package com.safety.net.alerts.model;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.el.stream.Stream;

/**
 * @return Persons.getFirstName() and so on so forth
 */
@JsonFilter("PersonsFilter")
public class Persons {
    @JsonProperty("firstName")
    String firstName;
    @JsonProperty("lastName")
    String lastName;
    @JsonProperty("address")
    String address;
    @JsonProperty("city")
    String city;
    @JsonProperty("zip")
    String zip;
    @JsonProperty("phone")
    String phone;
    @JsonProperty("email")
    String email;
    public Persons() {}


    //getters & setters for each variable
    private String getKey() {
        return firstName + "-" + lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getZip() {
        return zip;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "persons:{" +
                "firstName:" + firstName + "," +
                "lastName:" + lastName + "," +
                "address:" + address + "," +
                "city:" + city + "," +
                "zip:" + zip + "," +
                "phone:" + phone + "," +
                "email:" + email + "}";
    }

}
