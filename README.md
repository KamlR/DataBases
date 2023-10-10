# Задание 1:
**Почему любое отношение в реляционной схеме имеет по крайней мере один ключ?**
У нас должна быть возможность идентифицировать объект по его уникальному значению. Поэтому если не будет уникального ключа, то сделать этого мы не сможем.

# Задание 2:
Я буду жирным выделять ключ.
## Библиотека
- Book: {[**ISBN**, Year, Name, Author, Pages]}
- BookCopy: {[**CopyId**, **ISBN**, Place ]}
- Publisher: {[**Name**, Address]}
- Reader: {[**ReaderId**, Name, Surname, Address, BirthDate]}
- Category: {[**Name**, ParentCategory]}
  
## Квартира расположена в доме на улице в городе в стране 
- Country: {[**CountryID**]}
- City: {[**CityIdID**, CountryID]}
- Street: {[**StreetID**,CityIdID, CountryID]}
- House: {[**HouseID**, StreetID, CityIdID, CountryID]}
- Flat: {[**FlatID**, StreetID, CityIdID, CountryID]}
