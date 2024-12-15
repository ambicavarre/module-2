-- 11. Hollywood is remaking the classic movie "The Blob" and doesn't have a director yet. Write an INSERT query to add yourself to the person
--     table, and an UPDATE query to assign yourself as the director of "The Blob" (the movie is already in the movie table). (1 record each)

INSERT INTO person (person_name, birthday, biography, profile_path, home_page)
VALUES ('SRI VARRE','1986-11-13','happy','https://image.tmdb.org/t/p/w185','https://image.tmdb.org/t/p/w185');
UPDATE movie
SET director_id = (SELECT person_id FROM person WHERE person_name = 'SRI VARRE')
WHERE title = 'The Blob';

