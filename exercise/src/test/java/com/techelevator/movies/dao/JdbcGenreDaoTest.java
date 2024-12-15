package com.techelevator.movies.dao;

import com.techelevator.movies.model.Genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class JdbcGenreDaoTest extends BaseDaoTest {

    private static final Genre GENRE_878 =
            new Genre(878, "Science Fiction");

    private JdbcGenreDao sut;

    @BeforeEach
    public void setup() {
        sut = new JdbcGenreDao(dataSource);
    }

    @Test
    public void getGenres_returns_correct_number_of_genres() {

        List<Genre> genres = sut.getGenres();
        assertNotNull(genres, "getGenres returned a null list of genres.");
        assertEquals(19, genres.size(), "getGenres returned the wrong number of genres in the list.");
    }

    @Test
    public void getGenreById_with_valid_id_returns_correct_genre() {

        Genre genre = sut.getGenreById(878);
        assertNotNull(genre, "getGenreById with valid id returned a null genre.");
        assertGenresMatch(GENRE_878, genre, "getGenreById with valid id returned the incorrect/incomplete genre.");
    }

    @Test
    public void getGenreById_with_invalid_id_returns_null_genre() {

        Genre genre = sut.getGenreById(0); // IDs begin with 1, cannot be 0
        assertNull(genre, "getGenreById with invalid id returned a genre rather than null.");
    }

    @Test
    public void getGenresByName_with_full_name_exact_match_returns_correct_number_of_genres() {

        List<Genre> genres = sut.getGenresByName("Science fiction", false);
        assertNotNull(genres, "getGenresByName with full name exact match returned a null list of genres.");
        assertEquals(1, genres.size(),
                "getGenresByName with full name exact match returned the wrong number of genres in the list.");
    }

    @Test
    public void getGenresByName_with_partial_name_exact_match_returns_correct_number_of_genres() {

        List<Genre> genres = sut.getGenresByName("ience fict", false);
        assertNotNull(genres, "getGenresByName with partial name exact match returned a null list of genres.");
        assertEquals(0, genres.size(),
                "getGenresByName with partial name exact match returned the wrong number of genres in the list.");
    }

    @Test
    public void getGenresByName_with_empty_name_exact_match_returns_correct_number_of_genres() {

        List<Genre> genres = sut.getGenresByName("", false);
        assertNotNull(genres, "getGenresByName with empty name exact match returned a null list of genres.");
        assertEquals(0, genres.size(), "getGenresByName with empty name exact match returned the wrong number of genres in the list.");
    }

    @Test
    public void getGenresByName_with_partial_name_wildcard_match_returns_correct_number_of_genres() {

        List<Genre> genres = sut.getGenresByName("ience fict", true);
        assertNotNull(genres, "getGenresByName with partial name wildcard match returned a null list of genres.");
        assertEquals(1, genres.size(), "getGenresByName with partial name wildcard match returned the wrong number of genres in the list.");
    }

    @Test
    public void getGenresByName_with_empty_name_wildcard_match_returns_correct_number_of_genres() {

        List<Genre> genres = sut.getGenresByName("", true);
        assertNotNull(genres, "getGenresByName with empty name wildcard match returned a null list of genres.");
        assertEquals(19, genres.size(),
                "getGenresByName with empty name wildcard match returned the wrong number of genres in the list.");
    }

    private void assertGenresMatch(Genre expected, Genre actual, String message) {

        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getName(), actual.getName(), message);
    }
}
