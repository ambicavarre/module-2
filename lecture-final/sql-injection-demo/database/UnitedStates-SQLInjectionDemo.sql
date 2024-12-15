-- This SQL script is solely meant for the SQL Injection demo and 
-- NOT intended as a replacement for the `UnitedStates` database.
-- It's highly recommended to create this database using 
-- any name besides `UnitedStates` (e.g., `SqlInjectionDemo`).

START TRANSACTION;

DROP TABLE IF EXISTS park, state CASCADE;

CREATE TABLE state (
	state_abbreviation char(2) NOT NULL,
	state_name varchar(50) NOT NULL,
	population int NOT NULL,
	area int NOT NULL,
	-- capital int /*NOT*/ NULL, -- city table doesn't exist in this version
	sales_tax numeric(5,3) NOT NULL,
	state_nickname varchar(100) NULL,
	census_region varchar(10) NULL,
	CONSTRAINT PK_state PRIMARY KEY(state_abbreviation),      
	CONSTRAINT UQ_state_name UNIQUE(state_name),        
    CONSTRAINT UQ_state_nickname UNIQUE(state_nickname),
	CONSTRAINT CHK_census_region CHECK (census_region IS NULL OR census_region IN ('Northeast', 'South', 'Midwest', 'West'))
);

CREATE TABLE park (
	park_id serial,
	park_name varchar(50) NOT NULL,
	date_established date NOT NULL,
	area numeric(6,1) NOT NULL,
	has_camping boolean NOT NULL,
	CONSTRAINT PK_park PRIMARY KEY(park_id),
	CONSTRAINT UQ_park_name UNIQUE(park_name)
);

-- insert data

-- data collected February 10, 2021
-- state name, abbrev, capital, population (2019 estimate), area (sq km, 2018 data) - https://en.wikipedia.org/wiki/List_of_states_and_territories_of_the_United_States
-- state sales tax (base) - https://en.wikipedia.org/wiki/Sales_taxes_in_the_United_States
-- state nickname (first official nickname only, NULL if no official nickname) - https://en.wikipedia.org/wiki/List_of_U.S._state_and_territory_nicknames
-- census region - https://en.wikipedia.org/wiki/List_of_regions_of_the_United_States#Census_Bureau-designated_regions_and_divisions
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('AL', 'Alabama', 4903185, 135767, 4, 'Heart of Dixie', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('AK', 'Alaska', 731545, 1723337, 0, 'The Last Frontier', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('AS', 'American Samoa', 57400, 1505, 0, 'Motu o Fiafiaga', NULL);
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('AZ', 'Arizona', 7278717, 295234, 5.6, 'Grand Canyon State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('AR', 'Arkansas', 3017804, 137732, 6.5, 'The Natural State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('CA', 'California', 39512223, 423967, 7.25, 'The Golden State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('CO', 'Colorado', 5758736, 269601, 2.9, 'Centennial State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('CT', 'Connecticut', 3565278, 14357, 6.35, 'Constitution State', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('DE', 'Delaware', 973764, 6446, 0, 'The First State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('DC', 'District of Columbia', 705749, 176, 5.75, NULL, 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('FL', 'Florida', 21477737, 170312, 6, 'Sunshine State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('GA', 'Georgia', 10617423, 153910, 4, 'Peach State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('GU', 'Guam', 161700, 1478, 4, 'Tano y Chamorro', NULL);
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('HI', 'Hawaii', 1415872, 28313, 4.166, 'Aloha State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('ID', 'Idaho', 1787065, 216443, 6, 'Gem State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('IL', 'Illinois', 12671821, 149995, 6.25, 'Prairie State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('IN', 'Indiana', 6732219, 94326, 7, 'Hoosier State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('IA', 'Iowa', 3155070, 145746, 6, 'Hawkeye State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('KS', 'Kansas', 2913314, 213100, 6.5, 'Sunflower State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('KY', 'Kentucky', 4467673, 104656, 6, 'Bluegrass State ', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('LA', 'Louisiana', 4648794, 135659, 4.45, 'Pelican State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('ME', 'Maine', 1344212, 91633, 5.5, 'Vacationland', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('MD', 'Maryland', 6045680, 32131, 6, 'Free State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('MA', 'Massachusetts', 6892503, 27336, 6.25, 'The Bay State', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('MI', 'Michigan', 9986857, 250487, 6, 'The Great Lakes State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('MN', 'Minnesota', 5639632, 225163, 6.875, 'Land of 10,000 Lakes', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('MS', 'Mississippi', 2976149, 125438, 7, 'Magnolia State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('MO', 'Missouri', 6137428, 180540, 4.225, 'Show-Me State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('MT', 'Montana', 1068778, 380831, 0, 'Treasure State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('NE', 'Nebraska', 1934408, 200330, 5.5, 'Cornhusker State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('NV', 'Nevada', 3080156, 286380, 6.85, 'Silver State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('NH', 'New Hampshire', 1359711, 24214, 0, 'Granite State', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('NJ', 'New Jersey', 8882190, 22591, 6.625, 'Garden State', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('NM', 'New Mexico', 2096829, 314917, 5.125, 'Land of Enchantment', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('NY', 'New York', 19453561, 141297, 4, 'Empire State', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('NC', 'North Carolina', 10488084, 139391, 4.75, 'Tar Heel State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('ND', 'North Dakota', 762062, 183108, 5, 'Peace Garden State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('MP', 'Northern Mariana Islands', 52300, 5117, 0, NULL, NULL);
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('OH', 'Ohio', 11689100, 116098, 5.75, 'Buckeye State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('OK', 'Oklahoma', 3956971, 181037, 4.5, 'Sooner State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('OR', 'Oregon', 4217737, 254799, 0, 'Beaver State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('PA', 'Pennsylvania', 12801989, 119280, 6, 'Keystone State', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('PR', 'Puerto Rico', 3193694, 13791, 10.5, 'Isla del Encanto', NULL);
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('RI', 'Rhode Island', 1059361, 4001, 7, 'Ocean State', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('SC', 'South Carolina', 5148714, 82933, 6, 'Palmetto State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('SD', 'South Dakota', 884659, 199729, 4, 'The Mount Rushmore State', 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('TN', 'Tennessee', 6829174, 109153, 7, 'Volunteer State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('TX', 'Texas', 28995881, 695662, 6.25, 'Lone Star State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('VI', 'U.S. Virgin Islands', 103700, 1898, 5, NULL, NULL);
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('UT', 'Utah', 3205958, 219882, 5.95, 'Beehive State', 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('VT', 'Vermont', 623989, 24906, 6, 'Green Mountain State', 'Northeast');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('VA', 'Virginia', 8535519, 110787, 5.3, 'The Old Dominion', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('WA', 'Washington', 7614893, 184661, 6.5, NULL, 'West');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('WV', 'West Virginia', 1792147, 62756, 6, 'Mountain State', 'South');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('WI', 'Wisconsin', 5822434, 169635, 5, NULL, 'Midwest');
INSERT INTO state (state_abbreviation, state_name, population, area, sales_tax, state_nickname, census_region) VALUES ('WY', 'Wyoming', 578759, 253335, 4, 'Equality State', 'West');


-- data collected February 10, 2021
-- park name, area (sq km, 2019 data) - https://en.wikipedia.org/wiki/List_of_national_parks_of_the_United_States
-- has_camping - https://www.nps.gov/subjects/camping/campground.htm
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Acadia', '2/26/1919', 198.6, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('American Samoa', '10/31/1988', 33.4, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Arches', '11/12/1971', 310.3, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Badlands', '11/10/1978', 982.4, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Big Bend', '6/12/1944', 3242.2, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Biscayne', '6/28/1980', 700, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Black Canyon of the Gunnison', '10/21/1999', 124.6, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Bryce Canyon', '2/25/1928', 145, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Canyonlands', '9/12/1964', 1366.2, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Capitol Reef', '12/18/1971', 979, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Carlsbad Caverns', '5/14/1930', 189.3, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Channel Islands', '3/5/1980', 1009.9, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Congaree', '11/10/2003', 107.1, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Crater Lake', '5/22/1902', 741.5, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Cuyahoga Valley', '10/11/2000', 131.8, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Death Valley', '10/31/1994', 13793.3, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Denali', '2/26/1917', 19185.8, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Dry Tortugas', '10/26/1992', 261.8, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Everglades', '5/30/1934', 6106.5, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Gates of the Arctic', '12/2/1980', 30448.1, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Gateway Arch', '2/22/2018', 0.8, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Glacier', '5/11/1910', 4100, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Glacier Bay', '12/2/1980', 13044.6, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Grand Canyon', '2/26/1919', 4862.9, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Grand Teton', '2/26/1929', 1254.7, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Great Basin', '10/27/1986', 312.3, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Great Sand Dunes', '9/13/2004', 434.4, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Great Smoky Mountains', '6/15/1934', 2114.2, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Guadalupe Mountains', '10/15/1966', 349.5, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Haleakalā', '7/1/1961', 134.6, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Hawaiʻi Volcanoes', '8/1/1916', 1317.7, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Hot Springs', '3/4/1921', 22.5, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Indiana Dunes', '2/15/2019', 62.1, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Isle Royale', '4/3/1940', 2314, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Joshua Tree', '10/31/1994', 3217.9, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Katmai', '12/2/1980', 14870.3, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Kenai Fjords', '12/2/1980', 2710, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Kings Canyon', '3/4/1940', 1869.2, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Kobuk Valley', '12/2/1980', 7084.9, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Lake Clark', '12/2/1980', 10602, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Lassen Volcanic', '8/9/1916', 431.4, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Mammoth Cave', '7/1/1941', 218.6, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Mesa Verde', '6/29/1906', 212.4, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Mount Rainier', '3/2/1899', 956.6, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('New River Gorge', '12/27/2020', 28.4, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('North Cascades', '10/2/1968', 2042.8, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Olympic', '6/29/1938', 3733.8, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Petrified Forest', '12/9/1962', 895.9, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Pinnacles', '1/10/2013', 108, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Redwood', '10/2/1968', 562.5, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Rocky Mountain', '1/26/1915', 1075.7, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Saguaro', '10/14/1994', 375.8, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Sequoia', '9/25/1890', 1635.2, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Shenandoah', '12/26/1935', 806.2, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Theodore Roosevelt', '11/10/1978', 285.1, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Virgin Islands', '8/2/1956', 60.9, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Voyageurs', '4/8/1975', 883.1, false);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('White Sands', '12/20/2019', 592.2, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Wind Cave', '1/9/1903', 137.5, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Wrangell-St. Elias', '12/2/1980', 33682.6, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Yellowstone', '3/1/1872', 8983.2, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Yosemite', '10/1/1890', 3082.7, true);
INSERT INTO park (park_name, date_established, area, has_camping) VALUES ('Zion', '11/19/1919', 595.9, true);


COMMIT;
