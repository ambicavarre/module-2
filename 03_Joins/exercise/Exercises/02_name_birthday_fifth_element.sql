-- 2. The names and birthdays of actors in "The Fifth Element"
-- Order the results alphabetically (A-Z) by name.
-- (15 rows)

SELECT person.person_name,person.birthday
FROM person
JOIN movie_actor ON person.person_id = movie_actor.actor_id
JOIN movie ON movie_actor.movie_id = movie.movie_id

WHERE movie.title = 'The Fifth Element'
ORDER BY person.person_name LIMIT 15 ;