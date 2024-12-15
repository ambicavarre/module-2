package com.techelevator.movies.dao;

import com.techelevator.movies.model.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class JdbcPersonDaoTest extends BaseDaoTest {

    private static final Person PERSON_1 =
            new Person(1,
                    "George Lucas",
                    LocalDate.of(1944, 5, 14),
                    null,
                    null,
                    "https://image.tmdb.org/t/p/w185/WCSZzWdtPmdRxH9LUCVi2JPCSJ.jpg",
                    null
            );

    private JdbcPersonDao sut;

    @BeforeEach
    public void setup() {
        sut = new JdbcPersonDao(dataSource);
    }

    @Test
    public void getPersons_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersons();
        assertNotNull(persons, "getPersons returned a null list of persons.");
        assertEquals(331, persons.size(),"getPersons returned the wrong number of persons in the list.");
    }

    @Test
    public void getPersonById_with_valid_id_returns_correct_person() {

        Person person = sut.getPersonById(1);
        assertNotNull(person, "getPersonById with valid id returned a null person.");
        assertPersonsMatch(PERSON_1, person, "getPersonById with valid id returned the incorrect/incomplete person.");
    }

    @Test
    public void getPersonById_with_invalid_id_returns_null_person() {

        Person person = sut.getPersonById(0); // IDs begin with 1, cannot be 0
        assertNull(person, "getPersonById with invalid id returned a person rather than null.");
    }

    @Test
    public void getPersonsByName_with_full_name_exact_match_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersonsByName("george lucas", false);
        assertNotNull(persons, "getPersonsByName with full name exact match returned a null list of persons.");
        assertEquals(1, persons.size(),
                "getPersonsByName with full name exact match returned the wrong number of persons in the list.");
    }

    @Test
    public void getPersonsByName_with_partial_name_exact_match_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersonsByName("rge Luc", false);
        assertNotNull(persons, "getPersonsByName with partial name exact match returned a null list of persons.");
        assertEquals(0, persons.size(),
                "getPersonsByName with partial name exact match returned the wrong number of persons in the list.");
    }

    @Test
    public void getPersonsByName_with_empty_name_exact_match_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersonsByName("", false);
        assertNotNull(persons, "getPersonsByName with empty name exact match returned a null list of persons.");
        assertEquals(0, persons.size(), "getPersonsByName with empty name exact match returned the wrong number of persons in the list.");
    }

    @Test
    public void getPersonsByName_with_partial_name_wildcard_match_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersonsByName("rge Luc", true);
        assertNotNull(persons, "getPersonsByName with partial name wildcard match returned a null list of persons.");
        assertEquals(1, persons.size(),
                "getPersonsByName with partial name wildcard match returned the wrong number of persons in the list.");
    }

    @Test
    public void getPersonsByName_with_empty_name_wildcard_match_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersonsByName("", true);
        assertNotNull(persons, "getPersonsByName with empty name wildcard match returned a null list of persons.");
        assertEquals(331, persons.size(),
                "getPersonsByName with empty name wildcard match returned the wrong number of persons in the list.");
    }

    @Test
    public void getPersonsByCollectionName_with_full_collection_name_exact_match_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersonsByCollectionName("Star Wars Collection", false);
        assertNotNull(persons, "getPersonsByCollectionName with full collection name exact match returned a null list of persons.");
        assertEquals(25, persons.size(),
                "getPersonsByCollectionName with full collection name exact match returned the wrong number of persons in the list.");
    }

    @Test
    public void getPersonsByCollectionName_with_partial_collection_name_exact_match_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersonsByCollectionName("Star", false);
        assertNotNull(persons, "getPersonsByCollectionName with partial name exact match returned a null list of persons.");
        assertEquals(0, persons.size(),
                "getPersonsByCollectionName with partial name exact match returned the wrong number of persons in the list.");
    }

    @Test
    public void getPersonsByCollectionName_with_partial_collection_name_wildcard_match_returns_correct_number_of_persons() {

        List<Person> persons = sut.getPersonsByCollectionName("F", true);
        assertNotNull(persons, "getPersonsByCollectionName with partial name wildcard match returned a null list of persons.");
        assertEquals(29, persons.size(),
                "getPersonsByCollectionName with partial name wildcard match returned the wrong number of persons in the list.");
    }

    private void assertPersonsMatch(Person expected, Person actual, String message) {

        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getName(), actual.getName(), message);
        assertEquals(expected.getBirthday(), actual.getBirthday(), message);
        assertEquals(expected.getDeathDate(), actual.getDeathDate(), message);
        assertEquals(expected.getBiography(), actual.getBiography(), message);
        assertEquals(expected.getProfilePath(), actual.getProfilePath(), message);
        assertEquals(expected.getHomePage(), actual.getHomePage(), message);
    }
}
