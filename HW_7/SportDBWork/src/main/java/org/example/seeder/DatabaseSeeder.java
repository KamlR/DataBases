package org.example.seeder;

import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class DatabaseSeeder {
    private String url;
    private String user;
    private String password;
    ArrayList<String> countryIds  = new ArrayList();
    ArrayList<String> olympicIds  = new ArrayList();
    ArrayList<String> playerIds  = new ArrayList();
    ArrayList<String> eventIds  = new ArrayList();
    Random random = new Random();

    public DatabaseSeeder(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void seedCountries(int numOfRecords){
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Faker faker = new Faker(new Locale("en"));
            String query = "INSERT INTO Countries (name, country_id, area_sqkm, population) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (int i = 0; i < numOfRecords; i++) {
                    String countryId = faker.address().countryCode();
                    countryIds.add(countryId);
                    preparedStatement.setString(1, faker.address().country());
                    preparedStatement.setString(2, countryId);
                    preparedStatement.setInt(3, faker.number().numberBetween(1000, 1000000));
                    preparedStatement.setInt(4, faker.number().numberBetween(1000000, 100000000));
                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void seedOlympics(int numOfRecords){
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            Faker faker = new Faker(new Locale("en"));
            String query = "INSERT INTO Olympics (olympic_id, country_id, city, year, startdate, enddate) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (int i = 0; i < numOfRecords; i++) {

                    String cityAbbreviation = faker.address().city().substring(0, 3).toUpperCase();
                    int olympicYear = faker.number().numberBetween(2000, 2022);
                    String olympicId = cityAbbreviation + olympicYear;
                    preparedStatement.setString(1, olympicId);
                    olympicIds.add(olympicId);
                    // Выбор случайного country_id из ArrayList
                    String randomCountryId = countryIds.get(random.nextInt(countryIds.size()));
                    preparedStatement.setString(3, randomCountryId);

                    preparedStatement.setString(3, faker.address().city());
                    preparedStatement.setInt(4, faker.number().numberBetween(2000, 2022));
                    preparedStatement.setDate(5, java.sql.Date.valueOf(faker.date().future(30, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                    preparedStatement.setDate(6, java.sql.Date.valueOf(faker.date().future(60, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Не получилось заполнить таблицу 'Olympics' данными");
        }
    }

    public void seedPlayers(int numOfRecords){
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            Faker faker = new Faker(new Locale("en"));
            String query = "INSERT INTO Players (name, player_id, country_id, birthdate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (int i = 0; i < numOfRecords; i++) {
                    playerIds.add("PLAYER" + (i + 1));
                    preparedStatement.setString(1, faker.name().fullName());
                    preparedStatement.setString(2, "PLAYER" + (i + 1));
                    // Выбор случайного country_id из ArrayList
                    String randomCountryId = countryIds.get(random.nextInt(countryIds.size()));
                    preparedStatement.setString(3, randomCountryId);
                    preparedStatement.setDate(4, java.sql.Date.valueOf(faker.date().birthday(18, 40).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Не получилось заполнить таблицу 'Players' данными");
        }
    }
    public void seedEvents(int numOfRecords){
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            Faker faker = new Faker(new Locale("en"));
            String query = "INSERT INTO Events (event_id, name, eventtype, olympic_id, is_team_event, num_players_in_team, result_noted_in) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (int i = 0; i < numOfRecords; i++) {
                    eventIds.add("E" + (i + 1));
                    preparedStatement.setString(1, "E" + (i + 1));
                    preparedStatement.setString(2, faker.team().sport());
                    preparedStatement.setString(3, faker.team().name());
                    String cityAbbreviation = faker.address().city().substring(0, 3).toUpperCase();
                    String olympicId = olympicIds.get(random.nextInt(olympicIds.size()));
                    preparedStatement.setString(4, olympicId);
                    preparedStatement.setInt(5, faker.number().numberBetween(0, 2));
                    preparedStatement.setInt(6, faker.number().numberBetween(1, 11));
                    preparedStatement.setString(7, faker.team().state());
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Не получилось заполнить таблицу 'Events' данными");
        }
    }





    public void seedResults(int numOfRecords){
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            Faker faker = new Faker(new Locale("en"));
            String query = "INSERT INTO Results (event_id, player_id, medal, result) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (int i = 0; i < numOfRecords; i++) {
                    preparedStatement.setString(1, eventIds.get(random.nextInt(countryIds.size())));
                    preparedStatement.setString(2, playerIds.get(random.nextInt(countryIds.size())));
                    preparedStatement.setString(3, faker.options().option("Gold", "Silver", "Bronze", null));
                    preparedStatement.setFloat(4, (float) faker.number().randomDouble(2, 0, 100));
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Не получилось заполнить таблицу 'Results' данными");
        }
    }
}
