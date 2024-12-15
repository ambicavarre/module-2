-- CONCATENATING OUTPUTS

-- All city names and their state abbreviation.
-- Note surrounding "()" on state abbreviation.
-- Note the implied default ASC in ORDER BY. In general practice, only DESC is typically specified in the
--   SQL as needed.
-- Note building strings is typically not done within the SQL. Normally, the raw values are returned, and
--   concatenating is done within the application code that requested the data from the database.
SELECT (city_name || ' (' || state_abbreviation || ')') AS city_state_abbreviation
    FROM city
    ORDER BY city_name;

-- All park names and area
-- Note "Name: " and " Area: " in the concatenated string
SELECT ('Name: ' || park_name || ' Area: ' || area) as park_area
    FROM park
    ORDER BY park_name;

-- The census region and state name of all states in the West & Midwest sorted in ascending order.
-- Note colon-space between census region and state -- "census_region: state"
-- (optional) Note alternative ORDER BY, discuss why just the column names in the ORDER BY still returns
--   the same results as using the region_and_state concatenated string alias.
SELECT (census_region || ': ' || state_name) AS region_and_state
    FROM state
    WHERE census_region ILIKE '%west'
    ORDER BY region_and_state;
-- ORDER BY census_region, state_name; -- Alternative producing the same results


-- SUBQUERIES

-- List all cities in the western census region
SELECT city_name, state_abbreviation
    FROM city
    WHERE state_abbreviation IN (
        SELECT state_abbreviation
            FROM state
            WHERE census_region = 'West'
    )
    ORDER BY state_abbreviation;

-- List all cities in states with "garden" in their nickname
SELECT city_name, state_abbreviation
    FROM city
    WHERE state_abbreviation IN (
        SELECT state_abbreviation
            FROM state
            WHERE state_nickname ILIKE '%garden%'
    )
    ORDER BY state_abbreviation;

-- Get the state name and census region for all states with a national park
SELECT state_name, census_region
    FROM state
    WHERE state_abbreviation IN (
        SELECT state_abbreviation
            FROM park_state
    )
    ORDER BY census_region;


-- AGGREGATE FUNCTIONS

-- The number of cities with populations greater than 1 million
SELECT COUNT(population) AS big_cities_count
    FROM city
    WHERE population > 1000000;

-- The number of state nicknames.
-- Note COUNT(*) is commonly used to specify a row count rather than counting a specific column.
-- Note difference in the counts. NULL state nicknames are ignored by COUNT(state_nickname).
SELECT COUNT(state_nickname) AS nickname_count, COUNT(*) AS row_count
    FROM state;

-- Subqueries aren't limited to WHERE clauses, can appear in SELECT list (must return only 1 result)
-- Include state name rather than the state abbreviation while counting the number of cities in each state,
--   ordered from most cities to least.
SELECT COUNT(city_name) AS cities,
	(SELECT state_name
        FROM state
        WHERE state_abbreviation = city.state_abbreviation
    ) AS state_name
FROM city
GROUP BY state_abbreviation
ORDER BY cities DESC;

-- Average population across all the states. Note the use of alias, common with aggregated values.
SELECT AVG(population) AS avg_state_population
    FROM state;

-- Total population in the West and South census regions
SELECT SUM(population) AS west_south_population
    FROM state
    WHERE census_region = 'West' OR census_region = 'South';

-- The area of the smallest and largest parks.
-- Note SQL statements aren't limited to just one aggregate function at a time.
SELECT MIN(area) AS smallest, MAX(area) AS largest
    FROM park;

-- Get the park names with the smallest and largest areas
-- Note you can use a subquery to get the park names with the smallest and largest areas.
SELECT park_name, area
    FROM park
    WHERE area = (SELECT MIN(area) FROM park) OR
          area = (SELECT MAX(area) FROM park);


-- GROUP BY

-- Count the number of cities in each state, ordered from most cities to least.
-- Note state_abbreviation can be included in SELECT because it's included in GROUP BY.
SELECT COUNT(city_name) AS cities, state_abbreviation
    FROM city
    GROUP BY state_abbreviation
    ORDER BY cities DESC;

-- Determine the average park area depending upon whether parks allow camping or not.
SELECT has_camping, AVG(area) AS avg_park_area
    FROM park
    GROUP BY has_camping;

-- Sum of the population of cities in each state ordered by state abbreviation.
SELECT state_abbreviation, SUM(population) as sum_city_population
    FROM city
    GROUP BY state_abbreviation
    ORDER BY state_abbreviation;

-- The smallest city population in each state ordered by city population.
SELECT state_abbreviation, MIN(population) AS smallest_city_population
    FROM city
    GROUP BY state_abbreviation
    ORDER BY smallest_city_population;

