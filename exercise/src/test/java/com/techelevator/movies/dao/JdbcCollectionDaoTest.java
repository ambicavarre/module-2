package com.techelevator.movies.dao;

import com.techelevator.movies.model.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class JdbcCollectionDaoTest extends BaseDaoTest {

    private final Collection COLLECTION_86311 =
            new Collection(86311, "The Avengers Collection");

    private JdbcCollectionDao sut;

    @BeforeEach
    public void setup() {
        sut = new JdbcCollectionDao(dataSource);
    }

    @Test
    public void getCollections_returns_correct_number_of_collections() {

        List<Collection> collections = sut.getCollections();
        assertNotNull(collections, "getCollections returned a null list of collections.");
        assertEquals(6, collections.size(), "getCollections returned the wrong number of collections in the list.");
    }

    @Test
    public void getCollectionById_with_valid_id_returns_correct_collection() {

        Collection collection = sut.getCollectionById(86311);
        assertNotNull(collection, "getCollectionById with valid id returned a null collection.");
        assertCollectionsMatch(COLLECTION_86311, collection, "getCollectionById with valid id returned the incorrect/incomplete collection.");
    }

    @Test
    public void getCollectionById_with_invalid_id_returns_null_collection() {

        Collection collection = sut.getCollectionById(0); // IDs begin with 1, cannot be 0
        assertNull(collection, "getCollectionById with invalid id returned a collection rather than null.");
    }

    @Test
    public void getCollectionsByName_with_full_name_exact_match_returns_correct_number_of_collections() {

        List<Collection> collections = sut.getCollectionsByName("the avengers collection", false);
        assertNotNull(collections, "getCollectionsByName with full name exact match returned a null list of collections.");
        assertEquals(1, collections.size(),
                "getCollectionsByName with full name exact match returned the wrong number of collections in the list.");
    }

    @Test
    public void getCollectionsByName_with_partial_name_exact_match_returns_correct_number_of_collections() {

        List<Collection> collections = sut.getCollectionsByName("e avengers c", false);
        assertNotNull(collections, "getCollectionsByName with partial name exact match returned a null list of collections.");
        assertEquals(0, collections.size(),
                "getCollectionsByName with partial name exact match returned the wrong number of collections in the list.");
    }

    @Test
    public void getCollectionsByName_with_empty_name_exact_match_returns_correct_collections() {

        List<Collection> collections = sut.getCollectionsByName("", false);
        assertNotNull(collections, "getCollectionsByName with empty name exact match returned a null list of collections.");
        assertEquals(0, collections.size(),
                "getCollectionsByName with empty name exact match returned the wrong number of collections in the list.");
    }

    @Test
    public void getCollectionsByName_with_partial_name_wildcard_match_returns_correct_number_of_collections() {

        List<Collection> collections = sut.getCollectionsByName("e avengers c", true);
        assertNotNull(collections, "getCollectionsByName with partial name wildcard match returned a null list of collections.");
        assertEquals(1, collections.size(),
                "getCollectionsByName with partial name wildcard match returned the wrong number of collections in the list.");
    }

    @Test
    public void getCollectionsByName_with_empty_name_wildcard_match_returns_correct_number_of_collections() {

        List<Collection> collections = sut.getCollectionsByName("", true);
        assertNotNull(collections,"getCollectionsByName with empty name wildcard match returned a null list of collections.");
        assertEquals(6, collections.size(),
                "getCollectionsByName with empty name wildcard match returned the wrong number of collections in the list.");
    }

    private void assertCollectionsMatch(Collection expected, Collection actual, String message) {

        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getName(), actual.getName(), message);
    }
}
