# Midterm (Мавлетова Карина Радиковна, БПИ216)
## Задание 1
``` sql
-- Создание таблицы User
CREATE TABLE User (
    user_id INTEGER PRIMARY KEY,
    family_id INTEGER,
    name VARCHAR,
    budget INTEGER
);

-- Создание таблицы Categories
CREATE TABLE Categories (
    category_id INTEGER PRIMARY KEY
);

-- Создание таблицы Family
CREATE TABLE Family (
    family_id INTEGER PRIMARY KEY,
);

-- Создание таблицы Spendings
CREATE TABLE Spendings (
    spending_id INTEGER PRIMARY KEY,
    user_id INTEGER,
    date INTEGER,
    suma INTEGER
);
-- Создание таблицы Income
CREATE TABLE Income (
    income_id INTEGER PRIMARY KEY,
    user_id INTEGER,
    date INTEGER,
    suma INTEGER,
);
```
## Задание 2
Сразу скажу, что в заданиях используются формулировки, где нужно "для конкретной семьи", "между конкретными датами", для этого я просто использовала specified 
#### Запрос 1
``` sql
SELECT
    Users.user_id,
    Users.name AS user_name,
    Spendings.spending_id,
    Categories.name AS category_name,
    Spendings.suma,
    Spendings.date
FROM
    Users
JOIN Spendings ON Users.user_id = Spendings.user_id
JOIN Categories ON Spendings.category_id = Categories.category_id
LEFT JOIN Budgets ON Users.user_id = Budgets.user_id
                AND Spendings.category_id = Budgets.category_id
                AND Spendings.date BETWEEN Budgets.start_date AND Budgets.end_date
WHERE
    Users.family_id = 'specified_family_id'
    AND Spendings.date BETWEEN 'start_date' AND 'end_date'
    AND Budgets.id IS NULL;

```
#### Запрос 2
``` sql
SELECT
    u.user_id,
    u.name AS user_name,
    SUM(s.suma) AS total_spending
FROM
    User u
JOIN Spendings s ON u.user_id = s.user_id
WHERE
    YEAR(s.date) = YEAR(CURRENT_DATE())
GROUP BY
    u.user_id, u.name
HAVING
    total_spending > u.budget;
```

#### Запрос 3
Здесь мне так же показалось логичным сгруппировать для каждой семьи ещё и по категориям их доходы минус расходы
``` sql
SELECT
    F.family_id,
    COALESCE(SUM(I.suma), 0) AS total_income,
    COALESCE(SUM(S.suma), 0) AS total_spending,
    COALESCE(SUM(I.suma), 0) - COALESCE(SUM(S.suma), 0) AS balance
FROM
    Family F
JOIN Users U ON F.family_id = U.family_id
LEFT JOIN Spendings S ON U.user_id = S.user_id
                   AND S.date <= 'specified_date'
LEFT JOIN Income I ON U.user_id = I.user_id
                  AND I.date <= 'specified_date'
GROUP BY
    F.family_id;
```
