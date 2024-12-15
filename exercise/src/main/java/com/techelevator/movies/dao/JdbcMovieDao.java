package com.techelevator.movies.dao;

import com.techelevator.movies.model.Movie;
import com.techelevator.movies.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcMovieDao implements MovieDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Movie> getMovies() {
        List<Movie> movieList = new ArrayList<>();
        String sql = " SELECT movie_id, title, overview, tagline, poster_path, home_page, release_date, length_minutes, director_id, collection_id\t" +
                " FROM movie;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            movieList.add(mapRowToMovie(results));
        }
        return movieList;
    }

    @Override
    public Movie getMovieById(int id) {
        Movie movie = null;
        String sql = "SELECT movie_id, title, overview, tagline, poster_path, home_page, release_date, length_minutes, director_id, collection_id\t" +
                " FROM movie " + " WHERE movie_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            movie = mapRowToMovie(results);
        }

        return movie;
    }

    @Override
    public List<Movie> getMoviesByTitle(String title, boolean useWildCard) {
        List<Movie> movieList = new ArrayList<>();
        String sql = "SELECT movie_id, title, overview, tagline, poster_path, home_page, release_date, length_minutes, director_id, collection_id\t" +
                " FROM movie " + " WHERE title ILIKE ?;";
        if (useWildCard) {
            title = "%" + title + "%";
        }
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, title);
        while (results.next()) {
            movieList.add(mapRowToMovie(results));
        }

        return movieList;
    }

    @Override
    public List<Movie> getMoviesByDirectorNameAndBetweenYears(String directorName, int startYear,
                                                              int endYear, boolean useWildCard) {
        List<Movie> movieList = new ArrayList<>();
        if (useWildCard) {
            directorName = "%" + directorName + "%";
        }
        String sql = "SELECT m.movie_id, title, overview, tagline, poster_path, m.home_page, release_date, length_minutes, director_id, collection_id\n" +
                "FROM movie m\n" +
                "JOIN person p ON m.director_id = p.person_id\n" +
                "WHERE p.person_name LIKE ? \n" +
                "  AND EXTRACT(YEAR FROM m.release_date) BETWEEN ? AND ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, directorName, startYear, endYear);
        while (results.next()) {
            movieList.add(mapRowToMovie(results));
        }

        return movieList;
    }

    public Movie mapRowToMovie(SqlRowSet results) {
        Movie movie = new Movie();
        movie.setId(results.getInt("movie_id"));
        movie.setTitle(results.getString("title"));
        movie.setOverview(results.getString("overview"));
        movie.setTagline(results.getString("tagline"));
        movie.setPosterPath(results.getString("poster_path"));
        movie.setHomePage(results.getString("home_page"));
        if (results.getDate("release_date") != null) {
            movie.setReleaseDate(results.getDate("release_date").toLocalDate());
        }

        movie.setLengthMinutes(results.getInt("length_minutes"));
        movie.setDirectorId(results.getInt("director_id"));
        movie.setCollectionId(results.getInt("collection_id"));

        return movie;
    }
}
