# Домашняя работа №9
### Задание 1
``` sql
SELECT
  (BIT_LENGTH(name) + LENGTH(race)) AS calculation
FROM
  demographics;
```

### Задание 2
``` sql
SELECT
  id,
  BIT_LENGTH(name) AS name,
  birthday,
  BIT_LENGTH(race) AS race
FROM
  demographics;
```

### Задание 3
``` sql
SELECT
  id,
  ASCII(SUBSTRING(name FROM 1 FOR 1)) AS name,
  birthday,
  ASCII(SUBSTRING(race FROM 1 FOR 1)) AS race
FROM
  demographics;
```

### Задание 4
``` sql
SELECT
  CONCAT_WS(' ', prefix, first, last, suffix) AS title
FROM
  names;
```

### Задание 5
``` sql
SELECT
  RPAD(md5, LENGTH(sha256), '1') AS md5,
  LPAD(sha1, LENGTH(sha256), '0') AS sha1,
  sha256
FROM
  encryption;
```

### Задание 6
``` sql
SELECT
  LEFT(project, commits) AS project,
  RIGHT(address, contributors) AS address
FROM
  repositories;
```

### Задание 7
``` sql
SELECT
  project,
  commits,
  contributors,
  REGEXP_REPLACE(address, '[0-9]', '!', 'g') AS address
FROM
  repositories;
```

### Задание 8
``` sql
SELECT
  name,
  weight,
  price,
  ROUND(price / (weight / 1000), 2) AS price_per_kg
FROM
  Products
ORDER BY
  price_per_kg ASC, name ASC;
```
