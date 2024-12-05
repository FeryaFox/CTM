-- Таблица для сотрудников
CREATE TABLE Employee (
                          employee_id SERIAL PRIMARY KEY, -- Уникальный идентификатор сотрудника
                          first_name VARCHAR(100) NOT NULL, -- Имя сотрудника
                          last_name VARCHAR(100) NOT NULL, -- Фамилия сотрудника
                          middle_name VARCHAR(100), -- Отчество сотрудника (может быть пустым)
                          gender CHAR(1) CHECK (gender IN ('М', 'Ж')), -- Пол сотрудника: "М" - мужской, "Ж" - женский
                          position VARCHAR(100) NOT NULL, -- Должность сотрудника
                          birth_date DATE NOT NULL, -- Дата рождения сотрудника
                          hire_date TIMESTAMP NOT NULL, -- Дата и время приема на работу
                          salary NUMERIC(10, 2) NOT NULL, -- Оклад сотрудника
                          home_address VARCHAR(255), -- Домашний адрес сотрудника (может быть пустым)
                          contact_phone VARCHAR(15) NOT NULL, -- Контактный телефон сотрудника
                          home_phone VARCHAR(15) -- Домашний телефон сотрудника (может быть пустым)
);

-- Таблица для экспонатов
CREATE TABLE Exhibit (
                         exhibit_id SERIAL PRIMARY KEY, -- Уникальный идентификатор экспоната
                         name VARCHAR(255) NOT NULL, -- Название экспоната
                         production_date DATE NOT NULL, -- Дата и время производства экспоната
                         manufacturer VARCHAR(255), -- Производитель экспоната
                         device_type VARCHAR(100), -- Тип устройства или экспоната
                         condition VARCHAR(255) NOT NULL, -- Состояние экспоната
                         history TEXT, -- История экспоната
                         technical_specs TEXT, -- Технические характеристики экспоната
                         museum_entry_date DATE NOT NULL -- Дата и время поступления экспоната в музей
);

-- Таблица для выставок
CREATE TABLE Exhibition (
                            exhibition_id SERIAL PRIMARY KEY, -- Уникальный идентификатор выставки
                            name VARCHAR(255) NOT NULL, -- Название выставки
                            start_date TIMESTAMP NOT NULL, -- Дата и время начала выставки
                            end_date TIMESTAMP NOT NULL, -- Дата и время окончания выставки
                            theme VARCHAR(255) NOT NULL, -- Тема выставки
                            description TEXT NOT NULL, -- Описание выставки
                            ticket_price NUMERIC(10, 2) NOT NULL, -- Цена билета на выставку
                            visitor_count INT DEFAULT 0 -- Количество посетителей (по умолчанию 0)
);

-- Таблица для посетителей
CREATE TABLE Visitor (
                         visitor_id SERIAL PRIMARY KEY, -- Уникальный идентификатор посетителя
                         first_name VARCHAR(100) NOT NULL, -- Имя посетителя
                         last_name VARCHAR(100) NOT NULL, -- Фамилия посетителя
                         middle_name VARCHAR(100), -- Отчество посетителя (может быть пустым)
                         gender CHAR(1) CHECK (gender IN ('М', 'Ж')), -- Пол посетителя: "М" - мужской, "Ж" - женский
                         contact_phone VARCHAR(15) NOT NULL, -- Контактный телефон посетителя
                         last_visit_date TIMESTAMP NOT NULL, -- Дата и время последнего посещения
                         visit_count INT DEFAULT 0 -- Общее количество посещений (по умолчанию 0)
);

-- Таблица для билетов
CREATE TABLE Ticket (
                        ticket_id SERIAL PRIMARY KEY, -- Уникальный идентификатор билета
                        price NUMERIC(10, 2) NOT NULL, -- Цена билета
                        exhibition_id INT NOT NULL, -- Идентификатор выставки, на которую был куплен билет
                        owner_id INT NOT NULL, -- Идентификатор владельца билета (посетителя)
                        purchase_date TIMESTAMP NOT NULL, -- Дата и время покупки билета
                        exhibition_date TIMESTAMP NOT NULL, -- Дата и время посещения выставки
                        FOREIGN KEY (exhibition_id) REFERENCES Exhibition(exhibition_id) ON DELETE CASCADE, -- Связь с выставкой
                        FOREIGN KEY (owner_id) REFERENCES Visitor(visitor_id) ON DELETE CASCADE -- Связь с посетителем
);

-- Связующая таблица для сотрудников и выставок (многие ко многим)
CREATE TABLE ExhibitionCurator (
                                   employee_id INT NOT NULL, -- Идентификатор сотрудника
                                   exhibition_id INT NOT NULL, -- Идентификатор выставки
                                   PRIMARY KEY (employee_id, exhibition_id), -- Составной первичный ключ
                                   FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE, -- Связь с сотрудником
                                   FOREIGN KEY (exhibition_id) REFERENCES Exhibition(exhibition_id) ON DELETE CASCADE -- Связь с выставкой
);



-- Связующая таблица для сотрудников и экспонатов (многие ко многим)
CREATE TABLE ExhibitCurator (
                                employee_id INT NOT NULL, -- Идентификатор сотрудника
                                exhibit_id INT NOT NULL, -- Идентификатор экспоната
                                PRIMARY KEY (employee_id, exhibit_id), -- Составной первичный ключ
                                FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE, -- Связь с сотрудником
                                FOREIGN KEY (exhibit_id) REFERENCES Exhibit(exhibit_id) ON DELETE CASCADE -- Связь с экспонатом
);

-- Связующая таблица для экспонатов и выставок (многие ко многим)
CREATE TABLE ExhibitParticipation (
                                      exhibit_id INT NOT NULL, -- Идентификатор экспоната
                                      exhibition_id INT NOT NULL, -- Идентификатор выставки
                                      PRIMARY KEY (exhibit_id, exhibition_id), -- Составной первичный ключ
                                      FOREIGN KEY (exhibit_id) REFERENCES Exhibit(exhibit_id) ON DELETE CASCADE, -- Связь с экспонатом
                                      FOREIGN KEY (exhibition_id) REFERENCES Exhibition(exhibition_id) ON DELETE CASCADE -- Связь с выставкой
);
