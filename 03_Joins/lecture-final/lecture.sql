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

SELECT park_name, state_name
    FROM park
    JOIN park_state ON park.park_id = park_state.park_id
    JOIN state ON park_state.state_abbreviation = state.state_abbreviation;

-- Modify the previous query to include the name of the state's capital city.

SELECT park_name, state_name, city_name AS capital_name
    FROM park
    JOIN park_state ON park.park_id = park_state.park_id
    JOIN state ON park_state.state_abbreviation = state.state_abbreviation
    JOIN city ON state.capital = city.city_id;

-- Modify the previous query to include the area of each park.

SELECT park_name, state_name, city_name AS capital_name, park.area AS park_area
    FROM park
    JOIN park_state ON park.park_id = park_state.park_id
    JOIN state ON park_state.state_abbreviation = state.state_abbreviation
    JOIN city ON state.capital = city.city_id;

-- Write a query to retrieve the names and populations of all the cities in the Midwest census region.

SELECT city_name, city.population
    FROM city
    JOIN state ON city.state_abbreviation = state.state_abbreviation
    WHERE census_region = 'Midwest';

-- Write a query to retrieve the number of cities in the city table for each state in the Midwest census region.

SELECT state_name, COUNT(*) AS number_of_cities
    FROM city
    JOIN state ON city.state_abbreviation = state.state_abbreviation
    WHERE census_region = 'Midwest'
    GROUP BY state_name;

-- Modify the previous query to sort the results by the number of cities in descending order.

SELECT state_name, COUNT(*) AS number_of_cities
    FROM city
    JOIN state ON city.state_abbreviation = state.state_abbreviation
    WHERE census_region = 'Midwest'
    GROUP BY state_name
    ORDER BY number_of_cities DESC;


-- LEFT JOIN

-- Write a query to retrieve the state name and the earliest date a park was established in that state (or territory) for every record in the state table that has park records associated with it.

SELECT state_name, MIN(date_established) AS earliest_park
    FROM state
    JOIN park_state ON state.state_abbreviation = park_state.state_abbreviation
    JOIN park ON park_state.park_id = park.park_id
    GROUP BY state_name;

-- Modify the previous query so the results include entries for all the records in the state table, even if they have no park records associated with them.

SELECT state_name, MIN(date_established) AS earliest_park
    FROM state
    LEFT JOIN park_state ON state.state_abbreviation = park_state.state_abbreviation
    LEFT JOIN park ON park_state.park_id = park.park_id
    GROUP BY state_name;


-- MovieDB
-- After creating the MovieDB database and running the setup script, make sure it is selected in pgAdmin and confirm it is working correctly by writing queries to retrieve...

-- The names of all the movie genres

SELECT genre_name
    FROM genre;

-- The titles of all the Comedy movies

SELECT title
    FROM movie
    JOIN movie_genre ON movie.movie_id = movie_genre.movie_id
    JOIN genre ON movie_genre.genre_id = genre.genre_id
    WHERE genre_name = 'Comedy';



-- 1. The titles and release dates of movies that Tom Hanks has appeared in.
-- Order the results by release date, newest to oldest.
-- (47 rows)

SELECT title, release_date
    FROM movie m
    JOIN movie_actor ma ON m.movie_id = ma.movie_id
    JOIN person p ON ma.actor_id = p.person_id
    WHERE person_name = 'Tom Hanks'
    ORDER BY release_date DESC;

    

-- 15. The title of the movie and the name of director for movies where the director was also an actor in the same movie.
-- Order the results by movie title (A-Z)
-- (73 rows)

SELECT m.title, person_name
    FROM person p
    JOIN movie_actor ma ON p.person_id = ma.actor_id
    JOIN movie m ON ma.movie_id = m.movie_id AND p.person_id = m.director_id
    ORDER BY m.title;


-- 21. For every person in the person table with the first name of "George", list the number of movies they've been in. Name the count column 'num_of_movies'.
-- Include all Georges, even those that have not appeared in any movies. Display the names in alphabetical order.
-- (59 rows)
-- TIP: This one can be a little tricky. If you're off by one, look closer at each clause of your statement. There's something you can change to get a different result set.

SELECT person_name, COUNT(movie_id) AS num_of_movies
    FROM person p
    LEFT JOIN movie_actor ma ON p.person_id = ma.actor_id
    WHERE person_name LIKE 'George %'
    GROUP BY person_id, person_name
    ORDER BY person_name;