SELECT
    ex.exhibit_id,
    ex.name AS exhibit_name,
    CASE WHEN ep.exhibit_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_participating
FROM
    Exhibition e
        CROSS JOIN
        Exhibit ex
        LEFT JOIN
        exhibitparticipation ep ON e.exhibition_id = ep.exhibition_id AND ex.exhibit_id = ep.exhibit_id
WHERE e.exhibition_id = :exhibitionId;
