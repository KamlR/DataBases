package org.example.DBClasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "countries")
public class Country {
    @Column(name = "name", length = 40)
    private String name;
    @Id
    @Column(name = "country_id", length = 3, unique = true)
    private String countryId;
    @Column(name = "area_sqkm")
    private int areaSqkm;
    @Column(name = "population")
    private int population;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public int getAreaSqkm() {
        return areaSqkm;
    }

    public void setAreaSqkm(int areaSqkm) {
        this.areaSqkm = areaSqkm;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
