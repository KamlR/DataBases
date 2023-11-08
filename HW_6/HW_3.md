## Задание 1
### Показать все названия книг вместе с именами издателей:
``` sql
select distinct title, author from book
```
<img width="300" alt="Снимок экрана 2023-11-08 в 11 31 13" src="https://github.com/KamlR/DataBases/assets/115434090/c764c9a8-6573-4d73-909a-47006d4787da">

### В какой книге наибольшее количество страниц?
``` sql
select distinct title from book
where pagesnum = (select (max(pagesnum)) from book)
```
<img width="272" alt="Снимок экрана 2023-11-08 в 11 37 25" src="https://github.com/KamlR/DataBases/assets/115434090/aee3b3dc-c036-4e35-9611-c83074e55003">


### Какие авторы написали более 5 книг?
``` sql
select author from book
group by author
having count(*) > 5
```
Там нет авторов, которые написали более пяти книг. Есть Достоевский, но он ровно 5 написал

### В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?
``` sql
select  distinct title from book
where pagesnum > (select avg(pagesnum) from book)
```
<img width="272" alt="Снимок экрана 2023-11-08 в 11 37 25" src="https://github.com/KamlR/DataBases/assets/115434090/2163e225-1bc5-4dd5-b9bf-59161fd20e66">

### Какие категории содержат подкатегории?
``` sql
select distinct parentcat from category
where parentcat is not null;
```
<img width="176" alt="Снимок экрана 2023-11-08 в 13 57 13" src="https://github.com/KamlR/DataBases/assets/115434090/4271034a-7382-4a43-9a40-700e1ef26caf">


### У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
``` sql
select author, count(*) as count from book
group by author
order by count desc
limit 1;
```
<img width="300" alt="Снимок экрана 2023-11-08 в 12 06 26" src="https://github.com/KamlR/DataBases/assets/115434090/e45e9f3d-d8c2-4106-a7c8-a3259f149151">

### Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?
Я написала часть запроса
``` sql
select * from reader
join borrowing on reader.id = borrowing.readernr
where borrowing.isbn in (select isbn from book where author like 'Марк Твен');
```
Он выводит:

<img width="400" alt="Снимок экрана 2023-11-08 в 14 57 46" src="https://github.com/KamlR/DataBases/assets/115434090/8230590b-d793-4ad9-a35b-8a421943cd39">

А вот как вывести, чтобы читателю соотв. все книги Марка Твена, я не поняла
### Какие книги имеют более одной копии? 
``` sql
select title, count(copynumber) as count_copy from book
join copy on book.isbn = copy.isbn
group by copy.isbn, book.title
having count(*) > 1;
```
<img width="300" alt="Снимок экрана 2023-11-08 в 13 42 52" src="https://github.com/KamlR/DataBases/assets/115434090/44ca40bf-3336-493e-bda9-9793e00a5927">

### ТОП 10 самых старых книг
``` sql
select title, author, pubyear from book
order by pubyear
limit 10
```
<img width="550" alt="Снимок экрана 2023-11-08 в 13 45 31" src="https://github.com/KamlR/DataBases/assets/115434090/2f73fda0-ec7f-4235-99ff-8255f92b5773">

### Перечислите все категории в категории “Спорт” (с любым уровнем вложености).
``` sql
select categoryname from category
where parentcat like 'Sports'
```
<img width="213" alt="Снимок экрана 2023-11-08 в 13 49 40" src="https://github.com/KamlR/DataBases/assets/115434090/1a2f5805-65c0-4bc1-8825-e0b071ea10a3">


## Задание 2
### Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.
Там с isbn 123456 уже была запись, добавить не вышло, я просто поменяла isbn на 123459
``` sql
insert into borrowing (readernr, isbn, copynumber, returndate)
VALUES (
    (SELECT reader.id FROM Reader WHERE LastName = 'Петров' AND FirstName = 'Василий'),
    '123459', 4, '2032-07-20'
);
```

### Удалить все книги, год публикации которых превышает 2000 год.
``` sql
delete from book where pubyear > 2000;
```
### Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).
``` sql
update borrowing
set returndate = returndate + 30
where isbn in ( select isbn from bookcat where categoryname like 'Базы данных')
and returndate > 2016-01-01;
```
## Задание 3 (Опишите на русском языке результаты следующих запросов):
### 1
```sql
SELECT s.Name, s.MatrNr FROM Student s 
WHERE NOT EXISTS ( 
SELECT * FROM Check c WHERE c.MatrNr = s.MatrNr AND c.Note >= 4.0 ) ; 
```
Здесь мы хотим выбрать студентов, у которых плохая успеваемость, а именно все оценки меньше 4.
### 2
```sql
( SELECT p.ProfNr, p.Name, sum(lec.Credit) 
FROM Professor p, Lecture lec 
WHERE p.ProfNr = lec.ProfNr
GROUP BY p.ProfNr, p.Name)
UNION
( SELECT p.ProfNr, p.Name, 0 
FROM Professor p
WHERE NOT EXISTS ( 
SELECT * FROM Lecture lec WHERE lec.ProfNr = p.ProfNr )); 
```
Здесь у нас есть два запроса, результаты которых объединяются при помощи Union.
В первом случае мы достаём данные из таблицы о профессорах и лекциях. Далее считаем суммарное количество кредитов для каждого профессора по лекциям.
Во втором запросе мы ищем профессоров, которые вообще не ведут лекции и ставим им 0 в сумму.

### 3
```sql
SELECT s.Name, p.Note
FROM Student s, Lecture lec, Check c
WHERE s.MatrNr = c.MatrNr AND lec.LectNr = c.LectNr AND c.Note >= 4 
AND c.Note >= ALL ( 
SELECT c1.Note FROM Check c1 WHERE c1.MatrNr = c.MatrNr ) 
```
Здесь мы хотим вывести студентов и рядом их оценку по принципу: она больше или равна 4 и при этом является наивысшей среди всех представленных.
