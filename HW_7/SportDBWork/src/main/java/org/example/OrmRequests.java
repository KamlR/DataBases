package org.example;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class OrmRequests {
    SessionFactory sessionFactory;
    public OrmRequests(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    public void firstRequest(){
        String hql = "SELECT YEAR(p.birthdate), COUNT(p), " +
                "SUM(CASE WHEN r.medal = 'Gold' THEN 1 ELSE 0 END) " +
                "FROM Player p " +
                "JOIN p.results r " +
                "JOIN r.event e " +
                "JOIN e.olympic o " +
                "WHERE o.year = :olympicYear " +
                "GROUP BY YEAR(p.birthdate) " +
                "HAVING SUM(CASE WHEN r.medal = 'Gold' THEN 1 ELSE 0 END) > 0 " +
                "ORDER BY YEAR(p.birthdate)";
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(hql)
                    .setParameter("olympicYear", 2004);

            List<Object[]> result = query.getResultList();

            for (Object[] row : result) {
                Integer birthYear = (Integer) row[0];
                Long playerCount = (Long) row[1];
                Long goldMedalsCount = (Long) row[2];

                System.out.println("Year: " + birthYear + ", Player Count: " + playerCount + ", Gold Medals Count: " + goldMedalsCount);
            }

            transaction.commit();
        }
    }

    public void secondRequest(){
        String hql = "SELECT DISTINCT e " +
                "FROM Events e " +
                "JOIN Results r ON e.event_id = r.event_id " +
                "WHERE e.is_team_event = 0 " +
                "   AND r.medal = 'Gold' " +
                "GROUP BY e.event_id, r.medal " +
                "HAVING COUNT(DISTINCT r.player_id) >= 2";

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(hql);

            List<Events> result = query.getResultList();

            for (Events event : result) {
                System.out.println("Event ID: " + event.getEventId() + ", Name: " + event.getName());
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void thirdRequest(){
        String hql = "SELECT DISTINCT p.name, r.olympic_id " +
                "FROM Players p " +
                "JOIN Results r ON p.player_id = r.player_id " +
                "WHERE r.medal IN ('GOLD', 'SILVER', 'BRONZE')";

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(hql);

            List<Object[]> result = query.getResultList();

            for (Object[] row : result) {
                String playerName = (String) row[0];
                String olympicId = (String) row[1];

                System.out.println("Player Name: " + playerName + ", Olympic ID: " + olympicId);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fourthRequest(){
        String hql = "SELECT c.name " +
                "FROM Countries c " +
                "WHERE c.country_id = ( " +
                "    SELECT DISTINCT c1.country_id " +
                "    FROM Countries c1 " +
                "    JOIN Players p ON c1.country_id = p.country_id " +
                "    WHERE LOWER(SUBSTRING(p.name, 1, 1)) IN ('a', 'e', 'i', 'o', 'u') " +
                "    GROUP BY c1.country_id " +
                "    ORDER BY COUNT(DISTINCT p.player_id) DESC " +
                "    LIMIT 1 " +
                ")";

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(hql);

            List<String> result = query.getResultList();

            if (!result.isEmpty()) {
                System.out.println("Country with the highest percentage of players whose names start with a vowel: " + result.get(0));
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fifthRequest(){
        String hql = "SELECT c.name, " +
                "       COUNT(DISTINCT CASE WHEN e.is_team_event = 1 AND r.medal IN ('GOLD', 'SILVER', 'BRONZE') THEN e.event_id END) " +
                "           / CAST(c.population AS double) AS medalsPerCapita " +
                "FROM Countries c " +
                "JOIN Olympics o ON c.country_id = o.country_id " +
                "JOIN Events e ON o.olympic_id = e.olympic_id " +
                "LEFT JOIN Results r ON e.event_id = r.event_id " +
                "WHERE o.year = 2000 " +
                "GROUP BY c.country_id " +
                "ORDER BY medalsPerCapita " +
                "LIMIT 5";

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(hql);

            List<Object[]> result = query.getResultList();

            for (Object[] row : result) {
                String countryName = (String) row[0];
                Double medalsPerCapita = (Double) row[1];

                System.out.println("Country: " + countryName + ", Medals per Capita: " + medalsPerCapita);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
