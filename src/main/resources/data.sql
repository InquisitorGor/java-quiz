INSERT INTO category (category)
VALUES ('ООП'),
       ('JVM'),
       ('Java Core'),
       ('Java Collection'),
       ('Базы данных'),
       ('SQL'),
       ('JDBC');
INSERT INTO theory (title, description, category_id)
VALUES ('Что такое ООП?', 'Объектно-ориентированное программирование (ООП) — ' ||
                          'методология программирования, основанная на представлении программы ' ||
                          'в виде совокупности объектов, каждый из которых является экземпляром ' ||
                          'определенного класса, а классы образуют иерархию наследования.',
        1),
       ('Что такое «инкапсуляция»?', 'Инкапсуляция – это свойство системы, ' ||
                                     'позволяющее объединить данные и методы, работающие с ними, ' ||
                                     'в классе и скрыть детали реализации от пользователя, ' ||
                                     'открыв только то, что необходимо при последующем использовании.',
        1),
       ('За что отвечает JVM?', 'Виртуальная машина Java (Java Virtual Machine) - это механизм, ' ||
                                'предоставляющий среду выполнения для управления Java-кодом или ' ||
                                'приложениями. Виртуальная машина является независимой оболочкой ' ||
                                'исполнения кода, благодаря которой возможен её запуск на любой ОС, ' ||
                                'без влияния ОС на выполняемую программу.',
        2),
       ('Чем различаются JRE, JVM и JDK?', 'JVM, Java Virtual Machine (Виртуальная машина Java) — ' ||
                                           'основная часть среды времени исполнения Java (JRE). ' ||
                                           'Виртуальная машина Java исполняет байт-код Java, ' ||
                                           'предварительно созданный из исходного текста Java-программы компилятором Java. ' ||
                                           'JVM может также использоваться для выполнения программ, написанных на других языках программирования. ' ||
                                           'JRE, Java Runtime Environment (Среда времени выполнения Java) - минимально-необходимая реализация виртуальной машины для исполнения Java-приложений. ' ||
                                           'Состоит из JVM и стандартного набора библиотек классов Java.',
        3),
       ('Что такое «коллекция»?', '«Коллекция» - это структура данных, набор каких-либо объектов. ' ||
                                  'Данными (объектами в наборе) могут быть числа, строки, ' ||
                                  'объекты пользовательских классов и т.п.',
        4),
       ('Что такое «база данных»?', 'База данных — организованный и адаптированный для обработки вычислительной системой набор информации.',
        5),
       ('Что такое «SQL»?', 'SQL, Structured query language («язык структурированных запросов») — ' ||
                            'формальный непроцедурный язык программирования, применяемый для создания, ' ||
                            'модификации и управления данными в произвольной реляционной базе данных, ' ||
                            'управляемой соответствующей системой управления базами данных (СУБД).',
        6),
       ('Что такое JDBC?', 'JDBC, Java DataBase Connectivity (соединение с базами данных на Java) — ' ||
                           'промышленный стандарт взаимодействия Java-приложений с различными СУБД. ' ||
                           'Реализован в виде пакета java.sql, входящего в состав Java SE.',
        7);
INSERT INTO user_data (id, name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES (1, 'Михаил', 100, 140, 5, '01.01.2021', 145, 2);
INSERT INTO user_credential (id, login, password, role)
VALUES (1, 'm', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'USER');
INSERT INTO user_data (id, name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES (2, 'Антон', 100, 140, 5, '01.01.2021', 145, 6);
INSERT INTO user_credential (id, login, password, role)
VALUES (2, 'a', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'USER');
INSERT INTO user_data (id, name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES (3, 'Влад', 100, 140, 5, '01.01.2021', 145, 2);
INSERT INTO user_credential (id, login, password, role)
VALUES (3, 'v', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'USER');
INSERT INTO user_data (id, name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES (4, 'Паша', 100, 140, 5, '01.01.2021', 145, 6);
INSERT INTO user_credential (id, login, password, role)
VALUES (4, 'p', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'USER');
INSERT INTO competition_info (image_link, description, category_id)
VALUES ('/images/mini/java_mini.png', 'Соревнование по Java Core</br>' ||
                                      'В данном соревновании будут рассмотрены следующие моменты:<br>' ||
                                      '<ul><li>Основы синтаксиса</li><li>Парадигмы</li><li>Условия</li></ul>',
        3),
       ('/images/mini/oop_mini.png', 'Соревнование по ООП</br>' ||
                                     'В данном соревновании будут рассмотрены следующие моменты:<br>' ||
                                     '<ul><li>Основы синтаксиса</li><li>Парадигмы</li><li>Условия</li></ul>',
        1),
       ('/images/mini/collection_mini.png', 'Соревнование по Java Коллекциям</br>' ||
                                            'В данном соревновании будут рассмотрены следующие моменты:<br>' ||
                                            '<ul><li>Основы синтаксиса</li><li>Парадигмы</li><li>Условия</li></ul>',
        4);
INSERT INTO task (question, prestige, category_id)
VALUES ('Основные принципы ООП', 20, 1),
       ('Что не относится к парадигмам ООП', 10, 1),
       ('АОП расширяет ООП?', 5, 1),
       ('Инкапсуляция это', 15, 1),
       ('Полиморфизм это', 15, 1),
       ('Наследование это', 15, 1),
       ('Паттерны нужны?', 5, 1),
       ('Java и JS одно и тоже?', 15, 1),
       ('В JDK входит JVM?', 15, 1),
       ('В JDK входит JRE?', 15, 1),
       ('2+2=4', 15, 1);
INSERT INTO option (option)
VALUES ('Полиморфизм'),
       ('Инкапсуляция'),
       ('Наследование'),
       ('Сегрегация'),
       ('Да'),
       ('Нет'),
       ('Сокрытие реализации класса'),
       ('Написание геттеров и сеттеров'),
       ('Способность объекта иметь много форм'),
       ('Наличие родительских методов'),
       ('Создание сущности на базе уже существующей'),
       ('Ключевое слово extends');
INSERT INTO task_option(is_correct, option_id, task_id)
VALUES (true, 1, 1),
       (true, 2, 1),
       (true, 3, 1),
       (false, 4, 1),
       (false, 1, 2),
       (false, 2, 2),
       (false, 3, 2),
       (true, 4, 2),
       (true, 5, 3),
       (false, 6, 3),
       (true, 7, 4),
       (false, 8, 4),
       (true, 9, 5),
       (false, 10, 5),
       (true, 11, 6),
       (false, 12, 6),
       (true, 5, 7),
       (false, 6, 7),
       (true, 5, 8),
       (false, 6, 8),
       (true, 5, 9),
       (false, 6, 9),
       (true, 5, 10),
       (false, 6, 10),
       (true, 5, 11),
       (false, 6, 11);
INSERT INTO competition(finished_at, started_at, category_id)
VALUES ('2021-04-18 14:50:30.838000', '2021-04-18 14:50:02.653000', 1),
       ('2021-04-18 14:53:48.322000', '2021-04-18 14:50:53.241000', 1),
       ('2021-04-18 14:54:11.241000', '2021-04-18 14:51:19.160000', 1);
INSERT INTO contestant_info(score, competition_id, user_data_id, prestige)
VALUES (1, 1, 1, 20),
       (0, 1, 2,-30),
       (1, 2, 2, 15),
       (0, 3, 2, -9),
       (0, 2, 1, -8),
       (2, 3, 1, 40);
INSERT INTO contestant_result(contestant_info_id, task_option_id)
VALUES (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 11),
       (3, 12),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (5, 1),
       (5, 2),
       (5, 3),
       (5, 4),
       (6, 1),
       (6, 2),
       (6, 3),
       (6, 4),
       (6, 11),
       (6, 12),
       (6, 13),
       (6, 14);
INSERT INTO competition_task(competition_id, task_id)
VALUES
       (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5);




