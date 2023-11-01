# Library
### Какие фамилии читателей в Москве?
``` sql
select lastname FROM library.public.reader
where address like '%Москва%'; 
```
<img width="300" alt="Снимок экрана 2023-11-01 в 14 17 44" src="https://github.com/KamlR/DataBases/assets/115434090/40bd678b-ec02-4527-9ba4-8fbc1e5a4f43">

### Какие книги (author, title) брал Иван Иванов?
``` sql
SELECT library.public.book.author, library.public.book.title
FROM reader
Join Borrowing ON Reader.ID = Borrowing.ReaderNr
JOIN Book ON Borrowing.ISBN = Book.ISBN
WHERE Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов';
```
<img width="300" alt="Снимок экрана 2023-11-01 в 14 29 30" src="https://github.com/KamlR/DataBases/assets/115434090/6ced7459-bb25-463a-8c4e-8cd45b23a560">

### Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"?
``` sql
select bookcat.isbn from bookcat
JOIN book on bookcat.isbn = book.isbn
where bookcat.categoryname = 'Mountains' and bookcat.isbn not in (
    select bookcat.isbn from bookcat
    where bookcat.categoryname = 'Travel'
    )
```
<img width="300" alt="Снимок экрана 2023-11-01 в 14 45 28" src="https://github.com/KamlR/DataBases/assets/115434090/12b7864e-e3ee-4dc4-9b21-5c5e09fcd36c">

### Какие читатели (LastName, FirstName) вернули копию книги? 
``` sql
select library.public.reader.firstname, library.public.reader.lastname from reader
join borrowing on reader.id = borrowing.readernr
where borrowing.returndate is not null;
```
<img width="300" alt="Снимок экрана 2023-11-01 в 15 11 37" src="https://github.com/KamlR/DataBases/assets/115434090/22d9156c-a74f-427f-ac28-229544eed252"> 

###  Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?
``` sql
select distinct library.public.reader.firstname, library.public.reader.lastname from reader
join borrowing on reader.id = borrowing.readernr
where borrowing.isbn in (
    select borrowing.isbn from reader
    where reader.firstname = 'Иван' and reader.lastname = 'Иванов'
    )
and reader.lastname != 'Иванов' and reader.firstname != 'Иван'
```
<img width="300" alt="Снимок экрана 2023-11-01 в 15 30 30" src="https://github.com/KamlR/DataBases/assets/115434090/fb297252-3ee5-419e-bea4-cf27a8d45acb">
