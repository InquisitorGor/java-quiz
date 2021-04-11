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