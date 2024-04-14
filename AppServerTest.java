import org.junit.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppServerTest {

    // Tests case for creating an instance of the AppServer
    @Test
    public void testServerInitialization() {
        assertDoesNotThrow(AppServer::new);
    }

    // Tests case for parsing client input with valid username and password
    @Test
    public void testParseDataValidAccountCreation() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        String clientInput = "user:testUser|testPassword";
        assertDoesNotThrow(() -> AppServer.parseData(clientInput, writer));
    }

    // Tests case for parsing client input with invalid format
    @Test
    public void testParseDataInvalidFormat() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        String clientInput = "invalidFormat";
        assertDoesNotThrow(() -> AppServer.parseData(clientInput, writer));
    }

    // Tests case for account creation method with valid username and password
    @Test
    public void testAccountCreationSuccess() {
        assertDoesNotThrow(() -> AppServer.accountCreation("testUser", "testPassword"));
    }

    // Tests case for account creation method with existing username
    @Test
    public void testAccountCreationExistingUser() {
        // Assuming "testUser" already exists in the database
        assertDoesNotThrow(() -> AppServer.accountCreation("testUser", "testPassword"));
    }

    // Tests case for account creation method with invalid username
    @Test
    public void testAccountCreationInvalidUsername() {
        // Assuming the username is empty
        assertDoesNotThrow(() -> AppServer.accountCreation("", "testPassword"));
    }

    // Tests case for account creation method with invalid password
    @Test
    public void testAccountCreationInvalidPassword() {
        // Assuming the password is empty
        assertDoesNotThrow(() -> AppServer.accountCreation("testUser", ""));
    }

    // Tests case for searching user with valid input
    @Test
    public void testSearchUserValidInput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        String clientInput = "searchuser:testUser";
        assertDoesNotThrow(() -> AppServer.parseData(clientInput, writer));
    }

    // Tests case for searching user with invalid input
    @Test
    public void testSearchUserInvalidInput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        String clientInput = "searchuser:";
        assertDoesNotThrow(() -> AppServer.parseData(clientInput, writer));
    }

    @Test
    public void testParseDataEmptyInput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        String clientInput = "";
        assertDoesNotThrow(() -> AppServer.parseData(clientInput, writer));
    }
}
