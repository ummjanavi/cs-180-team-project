import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * SearchMethodsTest.java
 *
 * This class tests all the methods in SearchMethod.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */

public class SearchMethodsTest {

    @Test
    void searchUsersNoMatchesReturnsEmptyList() {
        // Setup
        SearchMethods searcher = new SearchMethods();

        // Execution
        ArrayList<String> result = searcher.searchUsers("xyz");

        // Assertion
        assertTrue(result.isEmpty());
    }

    @Test
    void searchUsersSingleMatchReturnsListWithOneUser() {
        SearchMethods searcher = new SearchMethods();
        createTestUserFile("me123.txt");

        ArrayList<String> result = searcher.searchUsers("123");

        // Assertion
        assertTrue(result.contains("me123"));

        // Cleanup
        deleteTestUserFile("me123.txt");
    }


    @Test
    void searchUsersMultipleMatchesReturnsListWithAllMatchedUsers() {
        SearchMethods searcher = new SearchMethods();
        createTestUserFile("bob123.txt");
        createTestUserFile("kevin456.txt");
        createTestUserFile("stuart789.txt");
        createTestUserFile("tom777.txt");
        createTestUserFile("jerry888.txt");

        ArrayList<String> result = searcher.searchUsers("r");

        // Assertion
        assertTrue(!result.isEmpty());
        assertTrue(result.contains("stuart789"));
        assertTrue(result.contains("jerry888"));

        // Cleanup
        deleteTestUserFile("bob123.txt");
        deleteTestUserFile("kevin456.txt");
        deleteTestUserFile("stuart789.txt");
        deleteTestUserFile("tom777.txt");
        deleteTestUserFile("jerry888.txt");
    }

    @Test
    void searchUsersEmptySearchStringReturnsAllUsers() {
        SearchMethods searcher = new SearchMethods();
        createTestUserFile("alvin.txt");
        createTestUserFile("simon.txt");
        createTestUserFile("theodore.txt");

        ArrayList<String> result = searcher.searchUsers("");

        // Assertion
        assertTrue(!result.isEmpty());
        assertTrue(result.contains("alvin"));
        assertTrue(result.contains("simon"));
        assertTrue(result.contains("theodore"));

        // Cleanup
        deleteTestUserFile("alvin.txt");
        deleteTestUserFile("simon.txt");
        deleteTestUserFile("theodore.txt");
    }

    @Test
    void searchUsersSearchStringWithMessagesUserReturnsEmptyList() {
        SearchMethods searcher = new SearchMethods();
        createTestUserFile("userWithMessages.txt");

        ArrayList<String> result = searcher.searchUsers("Messages");

        // Assertion
        assertTrue(result.isEmpty());

        // Cleanup
        deleteTestUserFile("userWithMessages.txt");
    }

    // Helper methods for test setup and cleanup
    private void createTestUserFile(String fileName) {
        try {
            Files.createFile(Paths.get(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTestUserFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchMethodsImplementsSearchInterface() {
        // Create an instance of SearchMethods
        SearchMethods searchMethods = new SearchMethods();

        // Check if SearchMethods implements SearchInterface
        assertTrue(searchMethods instanceof SearchInterface, "SearchMethods should implement SearchInterface");
    }
}
