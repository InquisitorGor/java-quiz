INSERT INTO category (category)
VALUES ('ООП'),
       ('JVM'),
       ('Java Core'),
       ('Java Collection'),
       ('Базы данных'),
       ('SQL'),
       ('JDBC');
INSERT INTO theory (description, title, category_id) VALUES ('<p>Объектно-ориентированное программирование (ООП) — методология программирования, основанная на представлении программы в виде совокупности объектов, каждый из которых является экземпляром определенного класса, а классы образуют иерархию наследования.</p>', 'Что такое ООП?', 1);
INSERT INTO theory (description, title, category_id) VALUES ('<p>Инкапсуляция – это свойство системы, позволяющее объединить данные и методы, работающие с ними, в классе и скрыть детали реализации от пользователя, открыв только то, что необходимо при последующем использовании.</p>', 'Что такое «инкапсуляция»?', 1);
INSERT INTO theory (description, title, category_id) VALUES ('<p>Виртуальная машина Java (Java Virtual Machine) - это механизм, предоставляющий среду выполнения для управления Java-кодом или приложениями. Виртуальная машина является независимой оболочкой исполнения кода, благодаря которой возможен её запуск на любой ОС, без влияния ОС на выполняемую программу.</p>', 'За что отвечает JVM?', 2);
INSERT INTO theory (description, title, category_id) VALUES ('<p>JVM, Java Virtual Machine (Виртуальная машина Java) — основная часть среды времени исполнения Java (JRE). Виртуальная машина Java исполняет байт-код Java, предварительно созданный из исходного текста Java-программы компилятором Java. JVM может также использоваться для выполнения программ, написанных на других языках программирования. JRE, Java Runtime Environment (Среда времени выполнения Java) - минимально-необходимая реализация виртуальной машины для исполнения Java-приложений. Состоит из JVM и стандартного набора библиотек классов Java.</p>', 'Чем различаются JRE, JVM и JDK?', 3);
INSERT INTO public.theory ( description, title, category_id) VALUES ( '<p>«Коллекция» - это структура данных, набор каких-либо объектов. Данными (объектами в наборе) могут быть числа, строки, объекты пользовательских классов и т.п.</p>', 'Что такое «коллекция»?', 4);
INSERT INTO public.theory ( description, title, category_id) VALUES ( '<p>База данных — организованный и адаптированный для обработки вычислительной системой набор информации.', 'Что такое «база данных»?', 5);
INSERT INTO public.theory ( description, title, category_id) VALUES ( '<p>SQL, Structured query language («язык структурированных запросов») — формальный непроцедурный язык программирования, применяемый для создания, модификации и управления данными в произвольной реляционной базе данных, управляемой соответствующей системой управления базами данных (СУБД).</p>', 'Что такое «SQL»?', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( '<p>JDBC, Java DataBase Connectivity (соединение с базами данных на Java) — промышленный стандарт взаимодействия Java-приложений с различными СУБД. Реализован в виде пакета java.sql, входящего в состав Java SE.</p>', 'Что такое JDBC?', 7);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что такое «абстракция»?','Что такое «абстракция»?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что представляет собой «обмен сообщениями»?','Что представляет собой «обмен сообщениями»?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Расскажите про основные понятия ООП: «класс», «объект», «интерфейс».','Расскажите про основные понятия ООП: «класс», «объект», «интерфейс».', 1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'В чем заключаются преимущества и недостатки объектно-ориентированного подхода в программировании?','В чем заключаются преимущества и недостатки объектно-ориентированного подхода в программировании?', 1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что подразумевают в плане принципов ООП выражения «является» и «имеет»?','Что подразумевают в плане принципов ООП выражения «является» и «имеет»?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'В чем разница между композицией и агрегацией?', 'В чем разница между композицией и агрегацией?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что такое статическое и динамическое связывание?','Что такое статическое и динамическое связывание?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','Что такое композиция?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','Что такое агрегация?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','Что такое объект?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','Какие проявления полиморфизма вы знаете?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','В чем преимущества объектно-ориентированных  языков программирования?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','Как использование объектно-ориентерованного подхода улучшает разработку программного обеспечения?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','В  чем разница между композицией  и агрегацией?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','В чем преимущества объектно-ориентированных  языков программирования?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что такое статическое и динамическое связывание?','Что такое статическое и динамическое связывание?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что такое статическое и динамическое связывание?','Что такое статическое и динамическое связывание?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что такое статическое и динамическое связывание?','Что такое статическое и динамическое связывание?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что такое статическое и динамическое связывание?','Что такое статическое и динамическое связывание?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что такое статическое и динамическое связывание?','Что такое статическое и динамическое связывание?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что такое «абстракция»?','Что такое «абстракция»?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'В чем разница между композицией и агрегацией?', 'В чем разница между композицией и агрегацией?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Что','Что вы подразумеваете под  полиморфизмом, инкапсуляцией и  динамическим связыванием?',  1);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'За что отвечает JVM','За что отвечает JVM',  2);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Области данных времени выполнения','Области данных времени выполнения',  2);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Frames','Frames', 2);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'Execution Engine','Execution Engine', 2);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 2);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го5', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'г34о', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'г6', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'г7о', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( '5го', 'гhyо', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'г654о', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'гgdgо', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'г6о', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'го', 'го', 6);
INSERT INTO public.theory ( description, title, category_id) VALUES ( 'г4о', 'го', 6);
INSERT INTO user_data (name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES ('Михаил', 100, 140, 5, '01.01.2021', 145, 2);
INSERT INTO user_credential (id, login, password, role)
VALUES (1, 'm', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'USER');
INSERT INTO user_data (name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES ('Антон', 100, 140, 5, '01.01.2021', 145, 6);
INSERT INTO user_credential (id, login, password, role)
VALUES (2, 'a', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'USER');
INSERT INTO user_data (name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES ('Влад', 100, 140, 5, '01.01.2021', 145, 2);
INSERT INTO user_credential (id, login, password, role)
VALUES (3, 'v', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'USER');
INSERT INTO user_data (name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES ('Паша', 100, 140, 5, '01.01.2021', 145, 6);
INSERT INTO user_credential (id, login, password, role)
VALUES (4, 'p', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'USER');
INSERT INTO user_data (name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES ('admin', 100, 140, 5, '01.01.2021', 145, 6);
INSERT INTO user_credential (id, login, password, role)
VALUES (5, 'admin', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'ADMIN');
INSERT INTO user_data (name, prestige, victories, defeats, registration_date, amount_of_battles, draws)
VALUES ('author', 100, 140, 5, '01.01.2021', 145, 6);
INSERT INTO user_credential (id, login, password, role)
VALUES (6, 'author', '$2y$12$r3Mwhpc1T0KrxAVE9MsbJeQuKsxBsnsVNH92wVvjt37yUceZpchom', 'AUTHOR');
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
INSERT INTO task (question, prestige, is_approved, category_id)
VALUES ('Основные принципы ООП', 20,true, 1),
       ('Что не относится к парадигмам ООП', 10,true, 1),
       ('АОП расширяет ООП?', 5,true, 1),
       ('Инкапсуляция это', 15,true, 1),
       ('Полиморфизм это', 15,true, 1),
       ('Наследование это', 15,true, 1),
       ('Паттерны нужны?', 5,true, 1),
       ('Java и JS одно и тоже?', 15,true, 1),
       ('В JDK входит JVM?', 15,true, 1),
       ('2+2=4', 15,true, 1);
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
       (false, 6, 10);
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
-- INSERT INTO contestant_result(contestant_info_id, task_option_id)
-- VALUES (1, 5),
--        (1, 6),
--        (1, 7),
--        (1, 8),
--        (3, 1),
--        (3, 2),
--        (3, 3),
--        (3, 4),
--        (3, 11),
--        (3, 12),
--        (4, 1),
--        (4, 2),
--        (4, 3),
--        (4, 4),
--        (5, 1),
--        (5, 2),
--        (5, 3),
--        (5, 4),
--        (6, 1),
--        (6, 2),
--        (6, 3),
--        (6, 4),
--        (6, 11),
--        (6, 12),
--        (6, 13),
--        (6, 14);
-- INSERT INTO competition_task(competition_id, task_id)
-- VALUES
--        (1, 1),
--        (1, 2),
--        (1, 3),
--        (1, 4),
--        (1, 5),
--        (2, 1),
--        (2, 2),
--        (2, 3),
--        (2, 4),
--        (2, 5),
--        (3, 1),
--        (3, 2),
--        (3, 3),
--        (3, 4),
--        (3, 5);




