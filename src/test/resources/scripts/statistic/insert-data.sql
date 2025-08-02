INSERT INTO roles (id, name) VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO users (id, email, password, first_name, last_name)
VALUES (1, 'Admin@mail.com','1234', 'Admin', 'User');

INSERT INTO users (id, email, password, first_name, last_name)
VALUES (2, 'User@mail.com', '4321', 'Regular', 'User');

INSERT INTO users (id, email, password, first_name, last_name)
VALUES (3, 'Test@mail.com', '2314', 'Tset', 'User');

INSERT INTO users (id, email, password, first_name, last_name)
VALUES (4, 'email@mail.com', '1qw2', 'Electronic', 'Mail');

INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 2),
    (1, 2),
    (4, 2);

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (1, 'title1','content1', 1, '2020-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (2, 'title2', 'content2', 1, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (3, 'title3', 'content3', 1, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (4, 'title4','content4', 1, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (5, 'title5', 'content5', 1, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (6, 'title6', 'content6', 1, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (7, 'title7','content7', 2, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (8, 'title8', 'content8', 2, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (9, 'title9', 'content9', 2, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (10, 'title10', 'content10', 2, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (11, 'title11','content11', 3, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (12, 'title12', 'content12', 3, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (13, 'title13', 'content13', 3, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (14, 'title14','content14', 3, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (15, 'title15', 'content15', 4, '2025-08-02 15:30:00');

INSERT INTO articles (id, title, content, user_id, date_of_publishing)
VALUES (16, 'title16', 'content16', 4, '2025-08-02 15:30:00');


