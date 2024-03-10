package com.safety.net.alerts.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CalculateAge {

    public int convertDate(String date) throws DateTimeException {
        LocalDate dateToConvert;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateToConvert = LocalDate.parse(date, formatter.withLocale(Locale.getDefault()));
        }
        catch (DateTimeException e) { //handles the 2nd type of format found
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            dateToConvert = LocalDate.parse(date, formatter.withLocale(Locale.getDefault()));
        }

        Period period = Period.between(LocalDate.now(), dateToConvert);
        int years = Math.abs(period.getYears());

        return years;
    }
}
