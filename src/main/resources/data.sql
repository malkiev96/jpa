insert into categories(id, name)
values (1, 'Категория 1'),
       (2, 'Категория 2'),
       (3, 'Категория 3'),
       (4, 'Категория 4'),
       (5, 'Категория 5'),
       (6, 'Категория 6'),
       (7, 'Категория 7'),
       (8, 'Категория 8'),
       (9, 'Категория 9');

insert into roles (id, code, name)
values (1, 'ROLE_USER', 'Пользователь'),
       (2, 'ROLE_ADMIN', 'Администратор');

insert into users(id, first_name, last_name, role_id)
values (1, 'Иван', 'Иванов', 2),
       (2, 'Петр', 'Петров', 2),
       (3, 'Олег', 'Иванов', 1),
       (4, 'Александр', 'Быков', 1),
       (5, 'Никита', 'Горохов', 1);

insert into statuses(id, code, name)
values (1, 'CREATED', 'Создан'),
       (2, 'PUBLISHED', 'Опубликован'),
       (3, 'REMOVED', 'Удален'),
       (4, 'BLOCKED', 'Заблокирован');

insert into tags(id, name)
values (1, 'Тег 1'),
       (2, 'Тег 2'),
       (3, 'Тег 3'),
       (4, 'Тег 4'),
       (5, 'Тег 5'),
       (6, 'Тег 6'),
       (7, 'Тег 7'),
       (8, 'Тег 8'),
       (9, 'Тег 9');

insert into posts(id, title, author_id, category_id, status_id)
values (1, 'Пост 1', 1, 1, 1),
       (2, 'Пост 2', 1, 1, 2),
       (3, 'Пост 3', 1, 2, 3),
       (4, 'Пост 4', 1, 2, 4),
       (5, 'Пост 5', 2, 3, 1),
       (6, 'Пост 6', 2, 3, 2),
       (7, 'Пост 7', 2, 4, 3),
       (8, 'Пост 8', 2, 4, 4),
       (9, 'Пост 9', 3, 5, 1),
       (10, 'Пост 10', 3, 5, 2),
       (11, 'Пост 11', 3, 6, 3),
       (12, 'Пост 12', 3, 6, 4),
       (13, 'Пост 13', 4, 7, 1),
       (14, 'Пост 14', 4, 7, 2),
       (15, 'Пост 15', 4, 8, 3),
       (16, 'Пост 16', 4, 8, 4),
       (17, 'Пост 17', 5, 9, 1),
       (18, 'Пост 18', 5, 9, 2),
       (19, 'Пост 19', 5, 9, 3),
       (20, 'Пост 20', 5, 9, 4);

insert into post_tags(post_id, tag_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 3),
       (3, 4);

insert into comments(id, message, author_id, post_id)
values (1, 'Коммент 1', 1, 1),
       (2, 'Коммент 2', 2, 1),
       (3, 'Коммент 3', 3, 3),
       (4, 'Коммент 4', 5, 3),
       (5, 'Коммент 5', 5, 3);

