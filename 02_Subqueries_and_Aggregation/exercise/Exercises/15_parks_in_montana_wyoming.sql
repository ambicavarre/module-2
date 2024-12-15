-- 15. The park name, date established, and area for parks in Montana and Wyoming.
-- Order the results by park name alphabetically.
-- (3 rows)
SELECT park_name, area, date_established
FROM park
WHERE park_id IN (
				SELECT park_id FROM park_state
				WHERE state_abbreviation IN (
					SELECT state_abbreviation
					FROM state
					WHERE state_name IN ('Montana', 'Wyoming')
					)
				)
ORDER BY park_name;