# Домашняя работа №7
## Общая информация
- В домашней работе было несколько пунктов, я буду описывать, как делала каждый из них. К каждому пункту буду прикладывать фрагменты с кодом, полный код приложения можно найти в папке.
- Работу я выполняла с использованием языка Java, для работы с бд Hibernate.


## Как я делала
### 1. Создать миграции, согласно схеме по ссылке выше
***Для создания миграций я использовала FlyWay***
- Для начала я добавила зависимость
 ```
  <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>8.0.0</version>
  </dependency>
```
- Далее создала 5 файлов с названиями V1_countries.sql и тд, которые лежат в папке db.migration.
  
  Пример:
```
create table Countries (
    name char(40),
    country_id char(3) unique,
    area_sqkm integer,
    population integer
);-
```
- В main прописала следующий код:
```
Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/sport", "karina", "admin")
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();
```
Соответственно при запуске программы FlyWay применит то, что лежит в файликах миграции. Таким образом, в базу данных добавятся нужные таблицы.

### 2. Создать скрипт для автоматического наполнения базы данных (seeder).
***Для создания фейковых данных я использовала Faker***
- Первым делом я добавила зависимость в pom.xml
  ```
  <dependency>
            <groupId>com.github.javafaker</groupId>
            <artifactId>javafaker</artifactId>
            <version>1.0.2</version> <!-- Последняя версия может отличаться -->
  </dependency>
  ```

- Далее я создала свой класс DatabaseSeeder, в котором прописала методы для заполнения каждой отдельной таблицы

  Вот так, например, выглядит метод для создания таблицы countries.
  ```
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
  ```
  Мы открываем соединение с бд, далее создаём объект Faker для генерации данных

### 3. Выполнить запросы при помощи ORM.
***Тут я использовала Hibernate***
- Как обычно первым делом я добавила зависимость
    ```
    <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.1.1.Final</version>
    </dependency>
    ```
- Далее я создала файлик hibernate.cfg.xml, где добавила все нужные данные для того, чтобы Hibernate смог использовать бд.
  ```
  <hibernate-configuration>
    <session-factory>
        <property name="dialect">org.hibernate.dialect.PostgreSQLDialect</property>
        <property name="connection.driver_class">org.postgresql.Driver</property>
        <property name="connection.url">"jdbc:postgresql://localhost:5432/sport"</property>
        <property name="connection.username">karina</property>
        <property name="connection.password">admin</property>
        <property name="hibernate.show_sql ">true</property>
        <property name="hbm2ddl.auto">update</property>

        <mapping class="org.example.DBClasses.Country"/>
        <mapping class="org.example.DBClasses.OlympicGame"/>
        <mapping class="org.example.DBClasses.Player"/>
        <mapping class="org.example.DBClasses.Event"/>
        <mapping class="org.example.DBClasses.Result"/>
    </session-factory>
</hibernate-configuration>
  ```
  
Тут я указала данные для соединения, некоторые доп характеристики для отслеживания запросов, а также используемые классы
- Обязательно создала сами классы, которые отвечают за таблицы
Пример класса OlympicGame

```
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
```
Там ещё есть getter и setter для всех полей.
@Table(name = "olympics") и @Column(name = "city", length = 50) позволят создать Hiberanate соответствие между классом и самой таблицей.

- Для написания самих запросов я создала отдельный класс OrmRequests. Для каждого из 5 запросов я создала отдельный метод.
Вот, например, так выглядит 3 запрос
```
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
```
Тут я использую объект sessionFactory, который передала в класс через конструктор из Main.
Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
Это код из Main для отправки запросов:
```
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        OrmRequests ormRequests = new OrmRequests(sessionFactory);
        ormRequests.firstRequest();
        ormRequests.secondRequest();
        ormRequests.thirdRequest();
        ormRequests.fourthRequest();
        ormRequests.fifthRequest();
```
