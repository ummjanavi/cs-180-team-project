import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginMethodsTest {

    private LoginMethods loginMethods;

    @BeforeEach
    void setUp() {
        loginMethods = new LoginMethods();
    }

    @Test
    void testCheckUsername_ValidUsername() {
        // Create a temporary file to simulate an existing user
        String existingUsername = "existingUser";
        createTempUserFile(existingUsername);

        // Tests the checkUsername method with an existing username
        assertFalse(loginMethods.checkUsername(existingUsername));
    }

    @Test
    void testCheckUsername_NewUsername() {
        // Tests the checkUsername method with a new username
        String newUsername = "newUser";
        assertTrue(loginMethods.checkUsername(newUsername));
    }

    @Test
    void testCreateAccount_Success() {
        // Tests the createAccount method with valid username and password
        String username = "testUser";
        String password = "testPassword";
        assertTrue(loginMethods.createAccount(username, password));

        // Clean up: Delete the temporary user file created during testing
        deleteTempUserFile(username);
    }

    @Test
    void testCreateAccount_Failure() {
        // Tests the createAccount method with an invalid username (already exists)
        String existingUsername = "existingUser";
        String password = "testPassword";
        assertFalse(loginMethods.createAccount(existingUsername, password));
    }

    @Test
    void testValidateLogin_ValidCredentials() {
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
    void testValidateLogin_InvalidUsername() {
        // Tests the validateLogin method with an invalid username
        String invalidUsername = "invalidUser";
        String password = "testPassword";
        assertFalse(loginMethods.validateLogin(invalidUsername, password));
    }

    @Test
    void testValidateLogin_InvalidPassword() {
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
            assertTrue(userFile.createNewFile());
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
}
