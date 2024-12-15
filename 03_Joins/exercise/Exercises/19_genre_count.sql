-- 19. The genre name and the number of movies in each genre. Name the count column 'num_of_movies'.
-- Order the results from the highest movie count to the lowest.
-- (19 rows, the highest expected count is around 400).

SELECT genre.genre_name,COUNT(movie.movie_id) AS num_of_movies
FROM genre
LEFT JOIN movie_genre ON genre.genre_id = movie_genre.genre_id
LEFT JOIN movie ON movie_genre.movie_id = movie.movie_id
GROUP BY genre.genre_name
ORDER BY num_of_movies DESC LIMIT 19;