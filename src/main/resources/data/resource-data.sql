INSERT INTO users(description_profile, nickname, password, username)
VALUES ('hello', 'tanya', 'adminpass', 'admin'),
       ('hello its me', 'ivan', 'pass1', 'vanya'),
       ('i am Anya', 'anya', 'pass2', 'anya');

INSERT INTO subscriptions(id_follower, id_following)
VALUES (1, 2),
       (3, 1);

INSERT INTO recipe(category, cook_time, description, difficulty, dislikes, likes, publication_date, recipe_name, user_id)
VALUES ('Breakfast', 10, 'tasty omelet', 2, 0, 0, '2021-03-12 11:02:43', 'Omelet', 1);

INSERT INTO step (description, step_number, file_id, recipe_id)
VALUES ('take eggs', 1, 1, 1);

INSERT INTO file_table (photo_name, photo_path)
VALUES ('file', '');
