-- SELECT
-- Use a SELECT statement to return a literal string

SELECT 'Hello SQL!';

-- Use a SELECT statement to add two numbers together (and label the result "sum")

SELECT 2 + 2 AS sum;


-- SELECT ... FROM
-- Write queries to retrieve...

-- The names from all the records in the state table

SELECT state_name
    FROM state;

-- The names and populations of all cities

SELECT city_name, population
    FROM city;

-- All columns from the park table

SELECT *
    FROM park;


-- ORDERING RESULTS

-- Populations of all states from largest to smallest.
-- Note ASC is default
SELECT state_name, population
    FROM state
    ORDER BY population;

-- Change sort order to DESC to see largest to smallest
SELECT state_name, population
    FROM state
    ORDER BY population DESC;

-- States sorted alphabetically (A-Z) within their census region. The census regions are sorted in reverse alphabetical (Z-A) order.
-- Note the NULL values mean there is no census region assigned. (You'll see how to filter these out later.)
-- Note order of columns in the ORDER BY: census_region is the major sort, state_name the minor sort,
--   meaning it's sorted within the major column, i.e. census_region.
-- Note order of columns in the SELECT only controls order columns are returned (i.e. "displayed"), not sort order.
SELECT state_name, census_region
    FROM state
    ORDER BY census_region DESC, state_name ASC;

-- The biggest park by area
-- Note that area isn't in the SELECT, but can be used in the ORDER BY
SELECT park_name
    FROM park
    ORDER BY area DESC;


-- LIMITING RESULTS

-- The 10 largest cities by populations
-- Note ORDER BY to sort city population from largest to smallest.
-- Note LIMIT 10 limits the number of results (i.e. "rows") returned. By itself, it has nothing to do with sort order,
--   as in returning the 10 "top" (i.e. largest populated) cities.
SELECT city_name, population
    FROM city
    ORDER BY population DESC
    LIMIT 10;

-- The 20 oldest parks from oldest to youngest in years, sorted alphabetically by name.
-- Note use of alternative calculations and/or non-aggregate functions to calculate age of park in years. (Pick one to show students)
-- Note LIMIT 20 returns rows where several of the parks are the same age in years.
-- Note park_name ASC in ORDER BY means parks that are the same years in age will be returned in alphabetic
--   order within the shared age.
SELECT CURRENT_DATE - date_established AS age, park_name -- difference between the two dates is given in days
--SELECT (CURRENT_DATE - date_established) / 365 AS age, park_name -- divide difference by 365 to determine years
--SELECT (date_part('year', CURRENT_DATE) - date_part('year', date_established)) AS age, park_name
--SELECT (EXTRACT(year FROM CURRENT_DATE) - EXTRACT(year FROM date_established)) AS age, park_name
    FROM park
    ORDER BY age DESC, park_name ASC
    LIMIT 20;


-- SELECT __ FROM __ WHERE
-- Write queries to retrieve...

-- The names of cities in California (CA)
SELECT city_name
    FROM city
    WHERE state_abbreviation = 'CA'
    ORDER BY city_name;

-- The names and state abbreviations of cities NOT in California
SELECT city_name, state_abbreviation
    FROM city
    WHERE state_abbreviation <> 'CA'
    ORDER BY state_abbreviation, city_name;

-- The names and areas of cities smaller than 25 square kilometers
SELECT city_name, area
    FROM city
    WHERE area < 25
    ORDER BY area DESC;

-- The names from all records in the state table that have no assigned census region
SELECT state_name
    FROM state
    WHERE census_region IS NULL
    ORDER BY state_name;

-- The names and census regions from all records in the state table that have an assigned census region
SELECT state_name, census_region
    FROM state
    WHERE census_region IS NOT NULL
    ORDER BY state_name;


-- WHERE with AND/OR
-- Write queries to retrieve...

-- The names, areas, and populations of cities smaller than 25 sq. km. with more than 100,000 people
SELECT city_name, area, population
    FROM city
    WHERE area < 25 AND population > 100000
    ORDER BY city_name;

-- The names and census regions of all states (and territories and districts) not in the Midwest region (including those that don't have any census region)
SELECT state_name, census_region
    FROM state
    WHERE census_region <> 'Midwest' OR census_region IS NULL
    ORDER BY census_region, state_name;

-- The names, areas, and populations of cities in California (CA) or Florida (FL)
SELECT city_name, area, population
    FROM city
    WHERE state_abbreviation = 'CA' OR state_abbreviation = 'FL'
    ORDER BY city_name;

-- The names, areas, and populations of cities in New England -- Connecticut (CT), Maine (ME), Massachusetts (MA), New Hampshire (NH), Rhode Island (RI) and Vermont (VT)
SELECT city_name, area, population
    FROM city
    WHERE state_abbreviation IN ('CT', 'ME', 'MA', 'NH', 'RI', 'VT')
    ORDER BY city_name;


-- SELECT statements involving math
-- Write a query to retrieve the names and areas of all parks in square METERS
-- (the values in the database are stored in square kilometers)
-- Label the second column "area_in_square_meters"
SELECT park_name, (area * 1000000) AS area_in_square_meters
    FROM park;


-- All values vs. distinct values

-- Write a query to retrieve the names of all cities and notice repeats (like Springfield and Columbus)
SELECT city_name
    FROM city;

-- Do it again, but without the repeats (note the difference in row count)
SELECT DISTINCT city_name
    FROM city;


-- LIKE
-- Write queries to retrieve...

-- The names of all cities that begin with the letter "A"
SELECT city_name
    FROM city
    WHERE city_name LIKE 'A%';

-- The names of all cities that end with "Falls"
SELECT city_name
    FROM city
    WHERE city_name LIKE '% Falls';

-- The names of all cities that contain a space
SELECT city_name
    FROM city
    WHERE city_name LIKE '% %';


-- BETWEEN
-- Write a query to retrieve the names and areas of parks of at least 100 sq. kilometers but no more than 200 sq. kilometers
SELECT park_name, area
    FROM park
    WHERE area BETWEEN 100 AND 200;

-- DATES
-- Write a query to retrieve the names and dates established of parks established before 1900
SELECT park_name, date_established
    FROM park
    WHERE date_established < '1/1/1900';



--Whats on the homework!!

--IS NULL
--Write a query to retrieve the state name, population and area of states with a NULL 
-- and census_region and an area less than 10,000 Order By State Name
SELECT state_name, population, area
    FROM state
    WHERE census_region IS NULL
    AND area < 10000
    ORDER BY state_name;


--Derived Field in the Where Clause and Order By 
SELECT state_name, (population/area) AS population_density
    FROM state
    WHERE (population/area) > 10000
    ORDER BY population_density;