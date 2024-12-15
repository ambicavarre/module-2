-- CONCATENATING OUTPUTS

-- All city names and their state abbreviation.
SELECT city_name || ', ' || state_abbreviation AS city_state_abbreviation
FROM city
ORDER BY population DESC;

-- All park names and area.
SELECT ('Name: ' || park_name || ' Area: ' || area) AS park_name_area
FROM park
ORDER BY park_name;

-- The census region and state name of all states in the West & Midwest sorted in ascending order.
SELECT census_region || ': ' || state_name AS region_state
FROM state
WHERE census_region ILIKE ('%west%')
ORDER BY region_state;


-- SUBQUERIES

-- List all cities in the western census region
SELECT city_name, state_abbreviation
FROM city 
WHERE state_abbreviation IN (
	SELECT state_abbreviation 
	FROM state 
	WHERE census_region = 'West')
ORDER BY state_abbreviation;

-- List all cities in states with "garden" in their nickname
SELECT city_name
FROM city 
WHERE state_abbreviation IN (
	SELECT state_abbreviation
	FROM state
	WHERE state_nickname ILIKE ('%garden%'))
ORDER BY state_abbreviation;

-- Get the state name and census region for all states with a national park
SELECT state_name, census_region 
FROM state 
WHERE state_abbreviation IN (
	SELECT state_abbreviation 
	FROM park_state)
ORDER BY census_region;



-- AGGREGATE FUNCTIONS

-- The number of cities with populations greater than 1 million
SELECT COUNT(city_name) AS big_city_count
FROM city
WHERE population > 1000000;

SELECT 
	COUNT(city_name) AS city_count, 
	AVG(population) as average_population, 
	SUM(area) as total_area
FROM city
WHERE population > 1000000;



-- The number of state nicknames.
SELECT COUNT(state_nickname) AS nickname_count, COUNT(*) as row_count
FROM state;

-- Subqueries aren't limited to WHERE clauses, can appear in SELECT list (must return only 1 result)
-- Include state name rather than the state abbreviation while counting the number of cities in each state,
--   ordered from most cities to least.

--AO: Column in SELECT CAN BE subquery too
SELECT city_name, 
	(SELECT state_name
	 FROM state
	 WHERE state_abbreviation = city.state_abbreviation
	) AS state_name
FROM city
ORDER BY city_name DESC;

--DO it again with Aggregate Function
SELECT COUNT(city_name) AS cities,
	(SELECT state_name
        FROM state
        WHERE state_abbreviation = city.state_abbreviation
    ) AS state_name
FROM city
GROUP BY state_abbreviation
ORDER BY cities DESC;



-- Average population across all the states. Note the use of alias, common with aggregated values.
SELECT AVG(population) as avg_state_population
FROM state;

-- Total population in the West and South census regions
SELECT census_region, SUM(population) as west_south_population
FROM state
WHERE census_region IN ('West', 'South')
GROUP BY census_region;


-- The area of the smallest and largest parks.
SELECT MIN(area) as smallest, MAX(area) AS largest
FROM park;

-- Get the park names with the smallest and largest areas
-- Note you can use a subquery to get the park names with the smallest and largest areas.

SELECT park_name, area
FROM park
WHERE area = (SELECT MIN(area) FROM park) OR
	  area = (SELECT MAX(area) FROM park);


-- GROUP BY
-- Count the number of cities in each state, ordered from most cities to least.
SELECT COUNT(city_name) as city_count, state_abbreviation
FROM city
GROUP BY state_abbreviation
ORDER BY city_count DESC;


-- Determine the average park area depending upon whether parks allow camping or not.
SELECT has_camping, ROUND(AVG(area),0) as park_area
FROM park
GROUP BY has_camping;
-- Sum of the population of cities in each state ordered by state abbreviation.
SELECT state_abbreviation, SUM(population) as sum_city_population
FROM city
GROUP BY state_abbreviation
ORDER BY state_abbreviation;

-- The smallest city population in each state ordered by city population.
SELECT state_abbreviation, MIN(population) as smallest_city_population 
FROM city
GROUP BY state_abbreviation
ORDER BY smallest_city_population;

