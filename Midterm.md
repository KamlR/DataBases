# Midterm
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
    budget_id INTEGER
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
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);
## Задание 2
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
    Family.family_id,
    Family.budget_id,
    Categories.name AS category_name,
    COALESCE(SUM(Spendings.suma), 0) AS total_spending,
    COALESCE(SUM(Income.suma), 0) AS total_income,
    COALESCE(SUM(Income.suma), 0) - COALESCE(SUM(Spendings.suma), 0) AS balance
FROM
    Family
JOIN Categories ON Family.budget_id = Categories.category_id
LEFT JOIN User ON Family.family_id = User.family_id
LEFT JOIN Spendings ON User.user_id = Spendings.user_id
                AND Spendings.date <= 'specified_date'
LEFT JOIN Income ON User.user_id = Income.user_id
                AND Income.date <= 'specified_date'
GROUP BY
    Family.family_id, Family.budget_id, Categories.name;
```
