package com.techelevator.movies.dao;

import com.techelevator.movies.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcPersonDao implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPersonDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Person> getPersons() {
        List<Person> personList = new ArrayList<>();
        String sql = "SELECT person_id, person_name, birthday, deathday, biography, profile_path, home_page\n" +
                " FROM person;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            personList.add(mapRowToPerson(results));
        }

        return personList;
    }

    @Override
    public Person getPersonById(int id) {
        Person person = null;
        String sql = "SELECT person_id, person_name, birthday, deathday, biography, profile_path, home_page\t" +
                " FROM person " + "WHERE person_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            person = mapRowToPerson(results);
        }
        return person;
    }

    @Override
    public List<Person> getPersonsByName(String name, boolean useWildCard) {
        List<Person> personList = new ArrayList<>();
        String sql = "SELECT person_id, person_name, birthday, deathday, biography, profile_path, home_page\t" +
                " FROM person " + "WHERE person_name ILIKE ?;";
        if (useWildCard) {
            name = "%" + name + "%";
        }
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
        while (results.next()) {
            personList.add(mapRowToPerson(results));
        }
        return personList;
    }

    @Override
    public List<Person> getPersonsByCollectionName(String collectionName, boolean useWildCard) {
        List<Person> personList = new ArrayList<>();
        String sql = "SELECT DISTINCT  person_id, person_name, birthday, deathday, biography, profile_path, person.home_page\t" +
                " FROM person " +
                " JOIN movie_actor ON person.person_id = movie_actor.actor_id " +
                " JOIN movie ON movie_actor.movie_id = movie.movie_id " +
                " JOIN collection ON movie.collection_id = collection.collection_id " +
                " WHERE  collection.collection_name ILIKE ?;";
        if (useWildCard) {
            collectionName = "%" + collectionName + "%";
        }
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionName);
        while (results.next()) {
            personList.add(mapRowToPerson(results));
        }

        return personList;
    }

    public Person mapRowToPerson(SqlRowSet results) {
        Person person = new Person();
        person.setId(results.getInt("person_id"));
        person.setName(results.getString("person_name"));

        if (results.getDate("birthday") != null) {
            person.setBirthday(results.getDate("birthday").toLocalDate());
        }
        if (results.getDate("deathday") != null) {
            person.setDeathDate(results.getDate("deathday").toLocalDate());
        }

        person.setBiography(results.getString("biography"));
        person.setProfilePath(results.getString("profile_path"));
        person.setHomePage(results.getString("home_page"));

        return person;
    }
}
