package com.techelevator.dao;

import com.techelevator.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcParkDao implements ParkDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcParkDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getParkCount() {
        int parkCount = 0;
        String sql = "SELECT COUNT(*) as count FROM park;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()){
            parkCount = results.getInt("count");
        }
        return parkCount;
    }
    
    @Override
    public LocalDate getOldestParkDate() {
        LocalDate dateEstablished = null;
        String sql = "SELECT MIN(date_established) as date_established FROM park;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()){
            dateEstablished = results.getDate("date_established").toLocalDate();
        }
        return dateEstablished;

    }
    
    @Override
    public double getAverageParkArea() {
        double avgArea =  0.0;
        String sql = "SELECT AVG(area) as avg_area FROM park;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()){
            avgArea = results.getDouble("avg_area");
        }
        return avgArea;
    }
    
    @Override
    public List<String> getParkNames() {
        List<String> parkNames = new ArrayList<>();
        String sql = "SELECT park_name FROM park ORDER BY park_name ASC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            parkNames.add(results.getString("park_name"));
        }

        return parkNames;
    }
    
    @Override
    public Park getRandomPark() {
        Park park = null;
        String sql = "SELECT park_id, park_name, date_established, area, has_camping\n" +
                     " FROM park ORDER BY RANDOM() LIMIT 1;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()){
            park = mapRowToPark(results);
        }
        return park;
    }

    @Override
    public List<Park> getParksWithCamping() {
        List<Park> parkList = new ArrayList<>();
        String sql = "SELECT park_id, park_name, date_established, area, has_camping\n" +
                     "FROM park WHERE has_camping = true;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            parkList.add(mapRowToPark(results));
        }
        return parkList;
    }

    @Override
    public Park getParkById(int parkId) {
        Park park = null;
        String sql = "SELECT park_id, park_name, date_established, area, has_camping\n" +
                     "FROM park WHERE park_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
        if (results.next()){
            park = mapRowToPark(results);
        }
        return park;
    }

    @Override
    public List<Park> getParksByState(String stateAbbreviation) {
        List<Park> parkList = new ArrayList<>();
        String sql = "SELECT park.park_id, park_name, date_established, area, has_camping\n" +
                "FROM park INNER JOIN park_state ON park.park_id = park_state.park_id\n" +
                "WHERE state_abbreviation = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, stateAbbreviation);
        while (results.next()){
            parkList.add(mapRowToPark(results));
        }
        return parkList;
    }

    @Override
    public List<Park> getParksByName(String name, boolean useWildCard) {
        List<Park> parkList = new ArrayList<>();

        String sql = "SELECT park.park_id, park_name, date_established, area, has_camping\n" +
                "FROM park\n" +
                "WHERE park_name ILIKE ?;";

        if (useWildCard){
            name = "%" + name + "%";
        }

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);

        while (result.next()){
            parkList.add(mapRowToPark(result));
        }

        return parkList;
    }

    private Park mapRowToPark(SqlRowSet rowSet) {
        Park park = new Park();
        park.setParkId(rowSet.getInt("park_id"));
        park.setParkName(rowSet.getString("park_name"));
        park.setDateEstablished(rowSet.getDate("date_established").toLocalDate());
        park.setArea(rowSet.getDouble("area"));
        park.setHasCamping(rowSet.getBoolean("has_camping"));
        return park;
    }
}
