import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class SearchMethodsTest {

    @Test
    void searchUsers_NoMatches_ReturnsEmptyList() {
        // Setup
        searchMethods searcher = new searchMethods();

        // Execution
        ArrayList<String> result = searcher.searchUsers("xyz");

        // Assertion
        assertTrue(result.isEmpty());
    }

    @Test
    void searchUsers_SingleMatch_ReturnsListWithOneUser() {
        searchMethods searcher = new searchMethods();
        createTestUserFile("me123.txt");

        ArrayList<String> result = searcher.searchUsers("123");

        // Assertion
        assertTrue(result.contains("me123"));

        // Cleanup
        deleteTestUserFile("me123.txt");
    }


    @Test
    void searchUsers_MultipleMatches_ReturnsListWithAllMatchedUsers() {
        searchMethods searcher = new searchMethods();
        createTestUserFile("bob123.txt");
        createTestUserFile("kevin456.txt");
        createTestUserFile("stuart789.txt");
        createTestUserFile("tom777.txt");
        createTestUserFile("jerry888.txt");

        ArrayList<String> result = searcher.searchUsers("7");

        // Assertion
        assertEquals(2, result.size());
        assertTrue(result.contains("tom777"));
        assertTrue(result.contains("jerry888"));

        // Cleanup
        deleteTestUserFile("bob123.txt");
        deleteTestUserFile("kevin456.txt");
        deleteTestUserFile("stuart789.txt");
        deleteTestUserFile("tom777.txt");
        deleteTestUserFile("jerry888.txt");
    }

    @Test
    void searchUsers_EmptySearchString_ReturnsAllUsers() {
        searchMethods searcher = new searchMethods();
        createTestUserFile("alvin.txt");
        createTestUserFile("simon.txt");
        createTestUserFile("theodore.txt");

        ArrayList<String> result = searcher.searchUsers("");

        // Assertion
        assertEquals(3, result.size());
        assertTrue(result.contains("alvin"));
        assertTrue(result.contains("simon"));
        assertTrue(result.contains("theodore"));

        // Cleanup
        deleteTestUserFile("alvin.txt");
        deleteTestUserFile("simon.txt");
        deleteTestUserFile("theodore.txt");
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
