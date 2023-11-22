## Задание 1
``` sql
CREATE PROCEDURE NEW_JOB (IN job_id VARCHAR(10), IN job_title VARCHAR(50), IN min_salary DECIMAL(10,2))
BEGIN
    DECLARE max_salary DECIMAL(10,2);

    SET max_salary = min_salary * 2;
   
    INSERT INTO JOBS (JOB_ID, JOB_TITLE, MIN_SALARY, MAX_SALARY)
    VALUES (job_id, job_title, min_salary, max_salary);
END;
CALL NEW_JOB('SY_ANAL', 'System Analyst', 6000);

```

## Задание 2
``` sql
CREATE PROCEDURE ADD_JOB_HIST (IN emp_id INT, IN new_job_id VARCHAR(10))
BEGIN
    DECLARE hire_date_found BOOLEAN DEFAULT TRUE;
    DECLARE hire_date DATE;

    -- Обработка несуществующего сотрудника, в hire_date_found запишется false, если select ниже не найдёт сотрудника.
    DECLARE CONTINUE HANDLER FOR NOT FOUND
        SET hire_date_found = FALSE;
    -- Отключение триггеров
    DISABLE TRIGGER ALL;
    SELECT HIRE_DATE INTO hire_date FROM EMPLOYEES WHERE EMPLOYEE_ID = emp_id;
    -- Если select нашёл сотрудника с указанным id, то можем назначить ему новую должность
    IF hire_date_found THEN
        -- Вставка записи в таблицу JOB_HISTORY
        INSERT INTO JOB_HISTORY (EMPLOYEE_ID, START_DATE, END_DATE, JOB_ID, DEPARTMENT_ID)
        VALUES (emp_id, hire_date, CURDATE(), new_job_id, (SELECT DEPARTMENT_ID FROM EMPLOYEES WHERE EMPLOYEE_ID =                emp_id));

        -- Обновление данных сотрудника в таблице EMPLOYEES
        UPDATE EMPLOYEES
        SET HIRE_DATE = CURDATE(),
            JOB_ID = new_job_id,
            SALARY = (SELECT MIN_SALARY + 500 FROM JOBS WHERE JOB_ID = new_job_id)
        WHERE EMPLOYEE_ID = emp_id;

        -- Включение триггеров
        ENABLE TRIGGER ALL;
    ELSE
        -- Здесь должен быть код для несуществующего сотрудника, но я ничего писать не стала:)
    END IF;
END;

CALL ADD_JOB_HIST(106, 'SY_ANAL');
```

## Задание 3
``` sql
CREATE PROCEDURE UPD_JOBSAL (IN job_id VARCHAR(10), IN new_min_salary DECIMAL(10,2), IN new_max_salary
DECIMAL(10,2))
BEGIN
    -- Если ресурс будет заблокирован
    DECLARE resource_busy_error BOOLEAN DEFAULT FALSE;
    -- Если минимальная зп окажется больше максимальной
    DECLARE invalid_salary_error BOOLEAN DEFAULT FALSE;

    -- Если вдруг строка будет заблокирована
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
        SET resource_busy_error = TRUE;

    -- Проверка на недопустимые значения зарплат
    IF new_min_salary > new_max_salary THEN
        SET invalid_salary_error = TRUE;
    END IF;

    -- Отключение триггеров
    DISABLE TRIGGER ALL;

    -- Попытка обновления минимальной и максимальной зарплаты
    IF NOT invalid_salary_error THEN
        UPDATE JOBS
        SET MIN_SALARY = new_min_salary, MAX_SALARY = new_max_salary
        WHERE JOB_ID = job_id;
    ELSE
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = ='Минимальная зарпалата не может быть выше макс.';
    END IF;

    -- Проверка наличия заблокированной строки в таблице JOBS
    IF resource_busy_error THEN
        SIGNAL SQLSTATE '-54'
            SET MESSAGE_TEXT = 'Строка, которую вы хотели обновить заблокирована';
    END IF;

    -- Включение триггеров
    ENABLE TRIGGER ALL;
END;

CALL UPD_JOBSAL('SY_ANAL', 7000, 140);

CALL UPD_JOBSAL('SY_ANAL', 8000, 7000);
```
