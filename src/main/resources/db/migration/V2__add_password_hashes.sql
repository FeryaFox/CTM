ALTER TABLE Employee
    ADD COLUMN password_hash VARCHAR(255) NOT NULL default ''; -- Хэш пароля

ALTER TABLE Visitor
    ADD COLUMN password_hash VARCHAR(255) NOT NULL default ''; -- Хэш пароля
