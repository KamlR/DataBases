package org.example.DBClasses;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "olympics")
public class OlympicGame {
    @Id
    @Column(name = "olympic_id", length = 7, unique = true)
    private String olympicId;

    @Column(name = "country_id", length = 3)
    private String countryId;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "year")
    private int year;

    @Column(name = "startdate")
    private java.sql.Date startDate;

    @Column(name = "enddate")
    private java.sql.Date endDate;

    public String getOlympicId() {
        return olympicId;
    }

    public void setOlympicId(String olympicId) {
        this.olympicId = olympicId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
