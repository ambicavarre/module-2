-- 7. The name, date established, and area of the 10 smallest parks.
-- (10 rows)

 SELECT park_name,date_established,area
 FROM park
 ORDER BY area ASC LIMIT 10;
