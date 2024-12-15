-- 23. The name and date established of parks opened in the 1960s.
-- Order the results by date established, oldest first.
-- (6 rows)

  SELECT park_name, date_established
  FROM park
  WHERE date_established BETWEEN '1/1/1960' AND '1/1/1970'
  ORDER BY date_established ASC LIMIT 6;
