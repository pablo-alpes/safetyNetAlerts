package com.safety.net.alerts.model;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

//"firestations": [
//	{ "address":"1509 Culver St", "station":"3" },
@JsonFilter("StationsFilter")
public class Firestations {
    @JsonProperty("address")
    String address;
    @JsonProperty("station")
    int station;

    //getters
    public String getAddress() {
        return address;
    }

    public int getStation() {
        return station;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public Firestations() {
    }
}
