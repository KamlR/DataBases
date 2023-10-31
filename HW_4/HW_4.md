### Создать физические модели - библиотеки из прошлого задания и две модели из скриншотов прошлого задания 
Я создавала https://dbdiagram.io три физические модели. Ниже прикреплю скриншоты того, что вышло.

#### Библиотека:
<img width="603" alt="Снимок экрана 2023-10-31 в 13 21 08" src="https://github.com/KamlR/DataBases/assets/115434090/c18620f7-238e-454b-ac34-5e028c5e0e88">

#### Станции и пути:
<img width="509" alt="Снимок экрана 2023-10-31 в 13 37 08" src="https://github.com/KamlR/DataBases/assets/115434090/70e87886-ce9a-475e-b24c-4cbb6f8350d8">

#### Персонал станций:
<img width="578" alt="Снимок экрана 2023-10-31 в 13 57 10" src="https://github.com/KamlR/DataBases/assets/115434090/8c2014db-521c-4b5c-b34a-89b021ceb0a1">


### Создать docker-composer файл, в котором будет 3 бд:
```
version: "3"

services:
    library:
        image: postgres:14.5
        environment:
            POSTGRES_PASSWORD: admin
            POSTGRES_USER: karina
            POSTGRES_DB: library
        ports:
            - "5435:5432"
        volumes:
            - library_data:/var/lib/postgresql/data

    stations:
        image: postgres:14.5
        environment:
            POSTGRES_PASSWORD: admin
            POSTGRES_USER: karina
            POSTGRES_DB: stations
        ports:
            - "5433:5432"

    stations_personell:
        image: postgres:14.5
        environment:
            POSTGRES_PASSWORD: admin
            POSTGRES_USER: karina
            POSTGRES_DB: stations_personell
        ports:
            - "5434:5432"
volumes:
  library_data:

```

Соответственно тут я обозначила три бд, далее при помощи команды docker-compose up -d я запустила все три контейнера.
Вот скрин из докера:
<img width="842" alt="Снимок экрана 2023-10-31 в 23 40 15" src="https://github.com/KamlR/DataBases/assets/115434090/a7ebfea5-fc37-442c-af00-8b55b6759d31">

### Скрипты для создания таблиц в бд:
Для library сделать не вышло, почему-то ни на одном порту не хотел подключаться. Пробовала полностью удалять контейнеры, создавать заново, но не получилось:(

Для двух остальных всё вышло.

#### Скрипт для "Станции и пути":
```
CREATE TABLE station (
  name varchar PRIMARY KEY,
  tracks integer,
  city_name varchar,
  region varchar
);


CREATE TABLE city (
  city_name varchar PRIMARY KEY,
  region varchar
);


CREATE TABLE train (
  train_nr integer PRIMARY KEY,
  length integer,
  connection_id integer
);


CREATE TABLE connected (
  connected_id integer PRIMARY KEY,
  departure_station_name varchar,
  arrival_station_name varchar,
  train_nr integer
);
```

##### Также скриншот из DataGrip:
<img width="232" alt="Снимок экрана 2023-10-31 в 23 45 31" src="https://github.com/KamlR/DataBases/assets/115434090/e2fc57f8-a079-497e-82b4-104a75c56de9">


#### Скрипт для "Персонал станций":
```
CREATE TABLE StationPersonell (
  PersNr integer PRIMARY KEY,
  Name varchar,
  Job varchar,
  StatNr integer
);

CREATE TABLE Caregiver (
  PersNr integer PRIMARY KEY,
  Qualification varchar
);

CREATE TABLE Doctor (
  PersNr integer PRIMARY KEY,
  Area varchar,
  Rank varchar
);

CREATE TABLE Patient (
  PatientNr integer PRIMARY KEY,
  Name varchar,
  Disease varchar,
  PersNr integer,
  RoomNr integer,
  AdmissionNr integer
);

CREATE TABLE Station (
  StatNr integer PRIMARY KEY,
  Name varchar
);

CREATE TABLE Room (
  RoomNr integer PRIMARY KEY,
  Beds integer
);

CREATE TABLE Admission (
  AdmisionNr integer PRIMARY KEY,
  AdmissionFrom integer,
  AdmisionTo integer
);
```
##### И скриншот из DataGrip:
<img width="219" alt="Снимок экрана 2023-10-31 в 23 49 39" src="https://github.com/KamlR/DataBases/assets/115434090/fafa7271-2a15-4ad1-9e96-d2f0691b86df">
