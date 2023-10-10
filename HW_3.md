# Задание 1:
**Почему любое отношение в реляционной схеме имеет по крайней мере один ключ?**
У нас должна быть возможность идентифицировать объект по его уникальному значению. Поэтому если не будет уникального ключа, то сделать этого мы не сможем.

# Задание 2:
Я буду жирным выделять ключ.
### Библиотека
- Book: {[**ISBN**, Year, Name, Author, Pages]}
- BookCopy: {[**CopyId**, **ISBN**, Place ]}
- Publisher: {[**Name**, Address]}
- Reader: {[**ReaderId**, Name, Surname, Address, BirthDate]}
- Category: {[**Name**, ParentCategory]}
  
### Квартира расположена в доме на улице в городе в стране 
- Country: {[**CountryID**]}
- City: {[**CityIdID**, CountryID]}
- Street: {[**StreetID**,CityIdID, CountryID]}
- House: {[**HouseID**, StreetID, CityIdID, CountryID]}
- Flat: {[**FlatID**, StreetID, CityIdID, CountryID]}

### Две команды играют друг против друга в футбол под руководством арбитра:
Team: {[**TeamID**]}
Referee: {[**RefereeID**, Name]}
Play: {[**PlayID**, RefereeID, TeamID1, TeamID2]}

### У каждого человека (мужчины и женщины) есть отец и мать
Person: {[**PersonID**, MotherID, FatherID]}

### ER-model
- Entity:{[**EntityName**]}
- RelationShip[{**EntityName1**, **EntityName2**, Realtion}]
- Attribute[{**AttributeID**, Name, Type, EntityName}]

# Задание 3:  
### 3.1 
- Station: {[**Name**, Tracks, CityName, Region]}
- City: {[**CityName**, **Region**]}
- Train: {[**TrainNR**, Length, ConnectonID]}
- Connected: {[**ConnectonID**, DepartureStationName, ArrivalStatioName, TrainNR ]}

### 3.2
- StationPersonell: {[**PersNr**, Name, Job, StatNr]}
- Caregiver: {[**PersNr**, Qualification]}
- Doctor: {[**PersNr**, Area, Rank]}
- Patient: {[**PatientNr**, Name, Disease, PersnNr, RoomNr, AdmissionNr]}
- Station: {[**StatNr**, Name]}
- Room: {[**RoomNr**, Beds]}
- Admission: [{**AdmisionNr**, AdmissionFrom(PersNr), AdmisionTo(PatientNr)}]
