-- Отчет о выставках, их кураторах и количестве проданных билетов

SELECT
    e.exhibition_id AS "ID Выставки",
    e.name AS "Название Выставки",
    e.theme AS "Тема",
    COUNT(DISTINCT t.ticket_id) AS "Продано Билетов",
    SUM(t.price) AS "Общая Выручка",
    COUNT(DISTINCT ec.employee_id) AS "Количество Кураторов",
    COUNT(DISTINCT ep.exhibit_id) AS "Количество Экспонатов"
FROM
    Exhibition e
        LEFT JOIN Ticket t ON e.exhibition_id = t.exhibition_id
        LEFT JOIN ExhibitionCurator ec ON e.exhibition_id = ec.exhibition_id
        LEFT JOIN ExhibitParticipation ep ON e.exhibition_id = ep.exhibition_id
GROUP BY
    e.exhibition_id, e.name, e.theme
ORDER BY
    SUM(t.price) DESC;
