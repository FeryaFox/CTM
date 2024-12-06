-- Отчет о сотрудниках, их участии в выставках и курации экспонатов
SELECT
    emp.employee_id AS "ID Сотрудника",
    CONCAT(emp.last_name, ' ', emp.first_name, ' ', COALESCE(emp.middle_name, '')) AS "ФИО",
    emp.position AS "Должность",
    COUNT(DISTINCT ec.exhibition_id) AS "Количество Выставок (Куратор)",
    COUNT(DISTINCT ex_cur.exhibit_id) AS "Количество Экспонатов (Куратор)",
    AVG(emp.salary) AS "Средняя Заработная Плата"
FROM
    Employee emp
        LEFT JOIN ExhibitionCurator ec ON emp.employee_id = ec.employee_id
        LEFT JOIN ExhibitCurator ex_cur ON emp.employee_id = ex_cur.employee_id
        LEFT JOIN Exhibit ex ON ex_cur.exhibit_id = ex.exhibit_id
GROUP BY
    emp.employee_id, emp.last_name, emp.first_name, emp.middle_name, emp.position
ORDER BY
    COUNT(DISTINCT ec.exhibition_id) DESC, -- Сортировка по количеству выставок
    COUNT(DISTINCT ex_cur.exhibit_id) DESC; -- Затем по количеству экспонатов
