-- INNER JOIN

-- Write a query to retrieve the name and state abbreviation for the 2 cities named "Columbus" in the database
SELECT city_name, state_abbreviation
FROM city
WHERE city_name = 'Columbus';

-- Modify the previous query to retrieve the names of the states (rather than their abbreviations).
SELECT city_name, state_name
FROM city
JOIN state ON city.state_abbreviation = state.state_abbreviation
WHERE city_name = 'Columbus';


-- Write a query to retrieve the names of all the national parks with their state abbreviations.
-- (Some parks will appear more than once in the results, because they cross state boundaries.)
SELECT park_name, state_abbreviation
FROM park
JOIN park_state ON park.park_id = park_state.park_id;

-- The park_state table is an associative table that can be used to connect the park and state tables.
-- Modify the previous query to retrieve the names of the states rather than their abbreviations.
SELECT park_name, state_name, state.state_abbreviation, park.park_id
FROM park
JOIN park_state ON park.park_id = park_state.park_id
JOIN state ON park_state.state_abbreviation = state.state_abbreviation;

--This is the same query
SELECT park_name, state_name, state.state_abbreviation, park.park_id
FROM state
JOIN park_state ON park_state.state_abbreviation = state.state_abbreviation
JOIN park ON park.park_id = park_state.park_id;


-- Modify the previous query to include the name of the state's capital city.
SELECT park_name, state_name, state.state_abbreviation, park.park_id, city.city_name
FROM park
JOIN park_state ON park.park_id = park_state.park_id
JOIN state ON park_state.state_abbreviation = state.state_abbreviation
JOIN city ON state.capital = city.city_id
ORDER BY city.city_name;

select * from city where state_abbreviation = 'UT';


-- Modify the previous query to include the area of each park.
SELECT park_name, state_name, state.state_abbreviation, park.area, city.city_name
FROM park
JOIN park_state ON park.park_id = park_state.park_id
JOIN state ON park_state.state_abbreviation = state.state_abbreviation
JOIN city ON state.capital = city.city_id
ORDER BY city.city_name;

-- Write a query to retrieve the names and populations of all the cities in the Midwest census region.
SELECT city.city_name, state.state_abbreviation, city.population
FROM city
INNER JOIN state ON city.state_abbreviation = state.state_abbreviation
WHERE state.census_region = 'Midwest';

-- Write a query to retrieve the number of cities in the city table for each state in the Midwest census region.
SELECT state.state_name, COUNT(*) as number_of_cities
FROM city
JOIN state ON city.state_abbreviation = state.state_abbreviation
WHERE state.census_region = 'Midwest'
GROUP BY state.state_name
ORDER BY number_of_cities DESC;

-- LEFT JOIN

-- Write a query to retrieve the state name and the earliest date a park was established in that state (or territory) for every record in the state table that has park records associated with it.
SELECT state_name, MIN(date_established) as earliest_park
FROM state
JOIN park_state ON state.state_abbreviation = park_state.state_abbreviation
JOIN park ON park.park_id = park_state.park_id
GROUP BY state_name;

-- Modify the previous query so the results include entries for all the records in the state table, even if they have no park records associated with them.
SELECT state_name, MIN(date_established) as earliest_park
FROM state
LEFT JOIN park_state ON state.state_abbreviation = park_state.state_abbreviation
LEFT JOIN park ON park.park_id = park_state.park_id
GROUP BY state_name;


-- MovieDB
-- After creating the MovieDB database and running the setup script, make sure it is selected in pgAdmin and confirm it is working correctly by writing queries to retrieve...

-- The names of all the movie genres
SELECT * 
FROM genre;

-- The titles of all the Comedy movies
SELECT movie.title
FROM movie
JOIN movie_genre ON movie.movie_id = movie_genre.movie_id
JOIN genre ON genre.genre_id = movie_genre.genre_id
WHERE genre.genre_name = 'Comedy';


-- 1. The titles and release dates of movies that Tom Hanks has appeared in.
-- Order the results by release date, newest to oldest.
-- (47 rows)
SELECT movie.title, movie.release_date
FROM movie
JOIN movie_actor ON movie.movie_id = movie_actor.movie_id
JOIN person ON movie_actor.actor_id = person.person_id
WHERE person_name = 'Tom Hanks'
ORDER BY movie.release_date DESC;


-- 15. The title of the movie and the name of director for movies where the director was also an actor in the same movie.
-- Order the results by movie title (A-Z)
-- (73 rows)
SELECT movie.title, person.person_name
FROM person 
JOIN movie_actor ON movie_actor.actor_id = person.person_id
JOIN movie ON movie_actor.movie_id = movie.movie_id AND person.person_id = movie.director_id
ORDER BY movie.title;


-- 21. For every person in the person table with the first name of "George", list the number of movies they've been in. Name the count column 'num_of_movies'.
-- Include all Georges, even those that have not appeared in any movies. Display the names in alphabetical order.
-- (59 rows)
-- TIP: This one can be a little tricky. If you''re off by one, look closer at each clause of your statement. There's something you can change to get a different result set.
SELECT person_name, COUNT(movie_id) as num_of_movies
FROM person
LEFT JOIN movie_actor ON movie_actor.actor_id = person.person_id
WHERE person_name LIKE 'George %'
GROUP BY person_name, person_id
ORDER BY person_name;




