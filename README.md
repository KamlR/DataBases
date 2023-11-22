# Домашняя работа №7
## Общая информация
- В домашней работе было несколько пунктов, я буду описывать, как делала каждый из них. К каждому пункту буду приклдывать фрагменты с кодом, полный код приложения можной найти в папке.
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
