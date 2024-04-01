import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class SearchMethodsTest {

    @Test
    // Create new user
    void searchUsers_NoMatches_ReturnsEmptyList() {
        // Setup
        searchMethods searcher = new searchMethods();

        // Execution
        ArrayList<String> result = searcher.searchUsers("xyz");

        // Assertion
        assertTrue(result.isEmpty());
    }

    @Test
    // Check new user
    void searchUsers_SingleMatch_ReturnsListWithOneUser() {
        searchMethods searcher = new searchMethods();
        createTestUserFile("testUser123.txt");

        ArrayList<String> result = searcher.searchUsers("123");

        // Assertion
        assertEquals(1, result.size());
        assertTrue(result.contains("testUser123"));

        // Cleanup
        deleteTestUserFile("testUser123.txt");
    }

    @Test
    // Delete user
    void searchUsers_MultipleMatches_ReturnsListWithAllMatchedUsers() {
        searchMethods searcher = new searchMethods();
        createTestUserFile("user123.txt");
        createTestUserFile("user456.txt");

        ArrayList<String> result = searcher.searchUsers("user");

        // Assertion
        assertEquals(2, result.size());
        assertTrue(result.contains("user123"));
        assertTrue(result.contains("user456"));

        // Cleanup
        deleteTestUserFile("user123.txt");
        deleteTestUserFile("user456.txt");
    }

    @Test
    // Check deleted user
    void searchUsers_EmptySearchString_ReturnsAllUsers() {
        searchMethods searcher = new searchMethods();
        createTestUserFile("user1.txt");
        createTestUserFile("user2.txt");
        createTestUserFile("user3.txt");

        ArrayList<String> result = searcher.searchUsers("");

        // Assertion
        assertEquals(3, result.size());
        assertTrue(result.contains("user1"));
        assertTrue(result.contains("user2"));
        assertTrue(result.contains("user3"));

        // Cleanup
        deleteTestUserFile("user1.txt");
        deleteTestUserFile("user2.txt");
        deleteTestUserFile("user3.txt");
    }

    @Test
    void searchUsers_SearchStringWithMessagesUser_ReturnsEmptyList() {
        searchMethods searcher = new searchMethods();
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
            File file = new File(fileName);
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTestUserFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
