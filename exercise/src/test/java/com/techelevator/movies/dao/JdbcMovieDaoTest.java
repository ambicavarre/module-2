package com.techelevator.movies.dao;

import com.techelevator.movies.model.Movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class JdbcMovieDaoTest extends BaseDaoTest {

    private final Movie MOVIE_311 =
            new Movie(311,
                    "Once Upon a Time in America",
                    "A former Prohibition-era Jewish gangster returns to the Lower East Side of Manhattan over thirty years later, where he once again must confront the ghosts and regrets of his old life.",
                    "Crime, passion and lust for power.",
                    "https://image.tmdb.org/t/p/w500/i0enkzsL5dPeneWnjl1fCWm6L7k.jpg",
                    null,
                    LocalDate.of(1984, 5, 23),
                    229,
                    4385,
                    0
            );

    private JdbcMovieDao sut;

    @BeforeEach
    public void setup() {
        sut = new JdbcMovieDao(dataSource);
    }

    @Test
    public void getMovies_returns_correct_number_of_movies() {

        List<Movie> movies = sut.getMovies();
        assertNotNull(movies, "getMovies returned a null list of movies.");
        assertEquals(44, movies.size(), "getMovies returned the wrong number of movies in the list.");
    }

    @Test
    public void getMovieById_with_valid_id_returns_correct_movie() {

        Movie movie = sut.getMovieById(311);
        assertNotNull(movie, "getMovieById with valid id returned a null movie.");
        assertMoviesMatch(MOVIE_311, movie, "getMovieById with valid id returned the incorrect/incomplete movie.");
    }

    @Test
    public void getMovieById_with_invalid_id_returns_null_movie() {

        Movie movie = sut.getMovieById(0); // IDs begin with 1, cannot be 0
        assertNull(movie, "getMovieById with invalid id returned a movie rather than null.");
    }

    @Test
    public void getMoviesByTitle_with_full_title_exact_match_returns_correct_number_of_movies() {

        List<Movie> movies = sut.getMoviesByTitle("Once upon a time in America", false);
        assertNotNull(movies, "getMoviesByTitle with full title exact match returned a null list of movies.");
        assertEquals(1, movies.size(), "getMoviesByTitle with full title exact match returned the wrong number of movies in the list.");
    }

    @Test
    public void getMoviesByTitle_with_partial_title_exact_match_returns_correct_number_of_movies() {

        List<Movie> movies = sut.getMoviesByTitle("upon a time", false);
        assertNotNull(movies, "getMoviesByTitle with partial title exact match returned a null list of movies.");
        assertEquals(0, movies.size(),
                "getMoviesByTitle with partial title exact match returned the wrong number of movies in the list.");
    }

    @Test
    public void getMoviesByTitle_with_empty_title_exact_match_returns_correct_number_of_movies() {

        List<Movie> movies = sut.getMoviesByTitle("", false);
        assertNotNull(movies, "getMoviesByTitle with empty title exact match returned a null list of movies.");
        assertEquals(0, movies.size(),
                "getMoviesByTitle with empty title exact match returned the wrong number of movies in the list.");
    }

    @Test
    public void getMoviesByTitle_with_partial_title_wildcard_match_returns_correct_number_of_movies() {

        List<Movie> movies = sut.getMoviesByTitle("upon a time", true);
        assertNotNull(movies, "getMoviesByTitle with partial title wildcard match returned a null list of movies.");
        assertEquals(1, movies.size(),
                "getMoviesByTitle with partial title wildcard match returned the wrong number of movies in the list.");
    }

    @Test
    public void getMoviesByTitle_with_empty_title_wildcard_match_returns_correct_number_of_movies() {

        List<Movie> movies = sut.getMoviesByTitle("", true);
        assertNotNull(movies, "getMoviesByTitle with empty title wildcard match returned a null list of movies.");
        assertEquals(44, movies.size(),
                "getMoviesByTitle with empty title wildcard match returned the wrong number of movies in the list.");
    }

    @Test
    public void getMoviesByDirectorNameAndBetweenYears_with_valid_arguments_returns_correct_number_of_movies() {

        List<Movie> movies = sut.getMoviesByDirectorNameAndBetweenYears(
                "Alfred Hitchcock", 1950, 1959, true);
        assertNotNull(movies, "getMoviesByDirectorNameAndBetweenYears with valid arguments returned a null list of movies.");
        assertEquals(7, movies.size(),
                "getMoviesByDirectorNameAndBetweenYears with valid arguments returned the wrong number of movies in the list.");
    }

    @Test
    public void getMoviesByDirectorNameAndBetweenYears_with_valid_arguments_and_wildcard_returns_correct_number_of_movies() {

        List<Movie> movies = sut.getMoviesByDirectorNameAndBetweenYears(
                "Chris", 1980, 2020, true);
        assertNotNull(movies,"getMoviesByDirectorNameAndBetweenYears with valid arguments and wildcard returned a null list of movies.");
        assertEquals(2, movies.size(),
                "getMoviesByDirectorNameAndBetweenYears with valid arguments and wildcard returned the wrong number of movies in the list.");
    }

    private void assertMoviesMatch(Movie expected, Movie actual, String message) {

        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getTitle(), actual.getTitle(), message);
        assertEquals(expected.getTagline(), actual.getTagline(), message);
        assertEquals(expected.getPosterPath(), actual.getPosterPath(), message);
        assertEquals(expected.getHomePage(), actual.getHomePage(), message);
        assertEquals(expected.getReleaseDate(), actual.getReleaseDate(), message);
        assertEquals(expected.getLengthMinutes(), actual.getLengthMinutes(), message);
        assertEquals(expected.getDirectorId(), actual.getDirectorId(), message);
        assertEquals(expected.getCollectionId(), actual.getCollectionId(), message);
    }
}
