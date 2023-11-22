package org.example;

import org.example.seeder.DatabaseSeeder;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/sport", "karina", "admin")
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();

        DatabaseSeeder databaseSeeder = new DatabaseSeeder("jdbc:postgresql://localhost:5432/sport", "karina", "admin");
        databaseSeeder.seedCountries(15);
        databaseSeeder.seedOlympics(15);
        databaseSeeder.seedPlayers(15);
        databaseSeeder.seedEvents(15);
        databaseSeeder.seedResults(15);


        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        OrmRequests ormRequests = new OrmRequests(sessionFactory);
        ormRequests.firstRequest();
        ormRequests.secondRequest();
        ormRequests.thirdRequest();
        ormRequests.fourthRequest();
        ormRequests.fifthRequest();
    }
}
