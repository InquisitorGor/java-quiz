INSERT INTO category (category)
VALUES ('ООП');
INSERT INTO theory (description, title, category_id)
VALUES
       ('<p>Объектно-ориентированное программирование (ООП) — методология программирования</p> %картинка 1% %картинка 2%',
        'Что такое ООП?', 1);
INSERT INTO attachment (caption, path, size, type, theory_id)
VALUES
       ('caption 1','/temp/423312картинка1img.png', 'SMALL', 'IMAGE', 1 ),
       ('caption 2','/temp/42bj12картинка2img.png', 'SMALL', 'IMAGE', 1 );
