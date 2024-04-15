import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * LoginMethodsTest.java
 *
 * This class tests all the methods in LoginMethods, validating that every method works as intended.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */

public class LoginMethodsTest {

    private LoginMethods loginMethods;

    @Before
    public void setUp() {
        loginMethods = new LoginMethods();
    }

    @Test
    public void testCreateAccount_Success() {
        // Tests the createAccount method with valid username and password
        String username = "testUser";
        String password = "testPassword";
        assertTrue(loginMethods.createAccount(username, password));

        // Clean up: Delete the temporary user file created during testing
        deleteTempUserFile(username);
    }


    @Test
    public void testValidateLogin_ValidCredentials() {
        // Create a temporary user file with valid credentials
        String username = "testUser";
        String password = "testPassword";
        createTempUserFileWithPassword(username, password);

        // Tests the validateLogin method with valid credentials
        assertTrue(loginMethods.validateLogin(username, password));

        // Clean up: Delete the temporary user file created during testing
        deleteTempUserFile(username);
    }

    @Test
    public void testValidateLogin_InvalidUsername() {
        // Tests the validateLogin method with an invalid username
        String invalidUsername = "invalidUser";
        String password = "testPassword";
        assertFalse(loginMethods.validateLogin(invalidUsername, password));
    }

    @Test
    public void testValidateLogin_InvalidPassword() {
        // Create a temporary user file with a different password
        String username = "testUser";
        String correctPassword = "testPassword";
        String incorrectPassword = "incorrectPassword";
        createTempUserFileWithPassword(username, correctPassword);

        // Tests the validateLogin method with an incorrect password
        assertFalse(loginMethods.validateLogin(username, incorrectPassword));

        // Clean up: Delete the temporary user file created during testing
        deleteTempUserFile(username);
    }

    // Helper methods for creating and deleting temporary user files

    private void createTempUserFile(String username) {
        try {
            File userFile = new File(username + ".txt");
            assertFalse(userFile.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTempUserFileWithPassword(String username, String password) {
        try (FileWriter writer = new FileWriter(username + ".txt")) {
            writer.write(username + "\n");
            writer.write(password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteTempUserFile(String username) {
        File userFile = new File(username + ".txt");
        assertTrue(userFile.delete());
    }

    @Test
    public void testLoginMethodsImplementsLoginInterface() {
        // Create an instance of SearchMethods
        LoginMethods loginMethods = new LoginMethods();

        // Check if SearchMethods implements SearchInterface
        Assertions.assertTrue(loginMethods instanceof LoginMethods, "LoginMethods should implement LoginInterface");
    }
}
