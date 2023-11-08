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
select distinct parentcat from bookcat
where parentcat is not null;
```
У меня почему-то не видит parentcat

### У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
``` sql
select author, count(*) as count from book
group by author
order by count desc
limit 1;
```
<img width="300" alt="Снимок экрана 2023-11-08 в 12 06 26" src="https://github.com/KamlR/DataBases/assets/115434090/e45e9f3d-d8c2-4106-a7c8-a3259f149151">

### Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?

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
