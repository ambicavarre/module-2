-- 18. The average length of movies in the "Science Fiction" genre. Name the column 'average_length'.
-- (1 row, expected result between 110-120)

SELECT AVG(movie.length_minutes) AS average_length
FROM movie
JOIN movie_genre ON movie.movie_id =movie_genre.movie_id
JOIN genre ON movie_genre.genre_id = genre.genre_id
WHERE genre.genre_name = 'Science Fiction'
LIMIT 1;
