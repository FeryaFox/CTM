-- Отчет о посетителях, их посещениях и выручке от проданных билетов
SELECT
    v.visitor_id AS "ID Посетителя",
    CONCAT(v.last_name, ' ', v.first_name, ' ', COALESCE(v.middle_name, '')) AS "ФИО",
    COUNT(t.ticket_id) AS "Количество Купленных Билетов",
    SUM(t.price) AS "Общая Выручка от Посетителя",
    MAX(v.last_visit_date) AS "Дата Последнего Посещения",
    v.visit_count AS "Общее Количество Посещений"
FROM
    Visitor v
        LEFT JOIN Ticket t ON v.visitor_id = t.owner_id
GROUP BY
    v.visitor_id, v.last_name, v.first_name, v.middle_name, v.visit_count
ORDER BY
    "Общая Выручка от Посетителя" DESC,
    "Количество Купленных Билетов" DESC;
