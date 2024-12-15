package com.techelevator.dao;

import com.techelevator.model.State;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcStateDao implements StateDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcStateDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public State getStateByAbbreviation(String stateAbbreviation) {
        State state = null;

        String sql = "SELECT state_abbreviation, state_name FROM state WHERE state_abbreviation = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, stateAbbreviation);

        if (results.next()) {
            state = mapRowToState(results);
        }
        return state;
    }

    @Override
    public State getStateByAbbreviationConcatenation(String stateAbbreviation) {
        State state = null;

        String sql = "SELECT state_abbreviation, state_name FROM state WHERE state_abbreviation = '" + stateAbbreviation + "';";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        if (results.next()) {
            state = mapRowToState(results);
        }
        return state;
    }

    @Override
    public List<State> getStatesByName(String stateName) {
        List<State> states = new ArrayList<>();

        String sql = "SELECT state_abbreviation, state_name FROM state WHERE state_name LIKE '%" + stateName + "%';";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            State state = mapRowToState(results);
            states.add(state);
        }
        return states;
    }

    private State mapRowToState(SqlRowSet rowSet) {
        State state = new State();
        state.setStateAbbreviation(rowSet.getString("state_abbreviation"));
        state.setStateName(rowSet.getString("state_name"));
        return state;
    }
}
