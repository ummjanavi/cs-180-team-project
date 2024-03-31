import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainApplicationTest {

    private final InputStream systemIn = System.in;
    private ByteArrayInputStream testIn;

    @BeforeEach
    public void setUpInput() {

        System.setIn(testIn);
    }

    @AfterEach
    public void restoreSystemInputOutput() {

        System.setIn(systemIn);
    }

    @Test
    void testLoginProcess_ValidCredentials() {
        // Simulates user input for login
        String input = "testUser\n" + "testPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute login process
        assertDoesNotThrow(() -> MainApplication.loginProcess());
    }

    @Test
    void testLoginProcess_InvalidCredentials() {
        // Simulates user input for login with invalid password
        String input = "testUser\n" + "wrongPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute login process
        assertDoesNotThrow(() -> MainApplication.loginProcess());
    }

    @Test
    void testAccountCreationProcess_Success() {
        // Simulates user input for creating an account
        String input = "newUser\n" + "newPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute account creation process
        assertDoesNotThrow(() -> MainApplication.accountCreationProcess());
    }

    @Test
    void testAccountCreationProcess_Failure() {
        // Simulates user input for creating an account with existing username
        String input = "existingUser\n" + "newPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute account creation process
        assertDoesNotThrow(() -> MainApplication.accountCreationProcess());
    }

    @Test
    void testShowMainMenu_ValidChoice() {
        // Simulates user input for main menu choice
        String input = "1\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute show main menu method
        assertDoesNotThrow(() -> MainApplication.showMainMenu(new User("testUser")));
    }

    @Test
    void testChangePasswordProcess_Success() {
        // Simulates user input for changing password
        String input = "testPassword\n" + "newPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertDoesNotThrow(() -> MainApplication.changePasswordProcess(new User("testUser")));
    }

    @Test
    void testChangePasswordProcess_Failure() {
        // Simulates user input for changing password with incorrect old password
        String input = "wrongPassword\n" + "newPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertDoesNotThrow(() -> MainApplication.changePasswordProcess(new User("testUser")));
    }

    @Test
    void testDirectMessageMenu_ValidChoice() {
        // Simulates user input for direct message menu choice
        String input = "1\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute direct message menu method
        assertDoesNotThrow(() -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser")));
    }

    @Test
    void testShowLoginMenu_ExitOption() {
        // Simulates user input to exit the application
        String input = "exit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the main application
        assertDoesNotThrow(MainApplication::main);
    }

    @Test
    void testShowLoginMenu_InvalidOption() {
        // Simulates user input for an invalid option
        String input = "invalid\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the main application
        assertDoesNotThrow(MainApplication::main);
    }

    @Test
    void testChangeDirectMessageSetting_ValidChoice() {
        // Simulates user input for changing direct message privacy
        String input = "1\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.changeDirectMessageSetting(new User("testUser")));
    }

    @Test
    void testChangeDirectMessageSetting_InvalidChoice() {
        // Simulates user input for an invalid choice
        String input = "invalid\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.changeDirectMessageSetting(new User("testUser")));
    }

    @Test
    void testSearchProcess_NoMatch() {
        // Simulates user input for searching with no match
        String input = "nonexistentUser\nback\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.searchProcess(new User("testUser")));
    }

    @Test
    void testUserViewerMenu_InvalidChoice() {
        // Simulates user input for an invalid choice in user viewer menu
        String input = "invalid\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.userViewerMenu(new User("testUser"), new User("anotherUser")));
    }

    @Test
    void testLoginProcess_UsernameBackOption() {
        // Simulates user input for login with 'back' option for username
        String input = "back\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute login process
        assertDoesNotThrow(() -> MainApplication.loginProcess());
    }

    @Test
    void testLoginProcess_PasswordBackOption() {
        // Simulates user input for login with 'back' option for password
        String input = "testUser\nback\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute login process
        assertDoesNotThrow(() -> MainApplication.loginProcess());
    }

    @Test
    void testAccountCreationProcess_UsernameBackOption() {
        // Simulates user input for creating an account with 'back' option for username
        String input = "back\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute account creation process
        assertDoesNotThrow(() -> MainApplication.accountCreationProcess());
    }

    @Test
    void testAccountCreationProcess_PasswordBackOption() {
        // Simulates user input for creating an account with 'back' option for password
        String input = "newUser\nback\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute account creation process
        assertDoesNotThrow(() -> MainApplication.accountCreationProcess());
    }

    @Test
    void testShowMainMenu_InvalidChoice() {
        // Simulates user input for main menu with invalid choice
        String input = "invalid\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute show main menu method
        assertDoesNotThrow(() -> MainApplication.showMainMenu(new User("testUser")));
    }

    @Test
    void testChangePasswordProcess_OldPasswordBackOption() {
        // Simulates user input for changing password with 'back' option for old password
        String input = "back\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertDoesNotThrow(() -> MainApplication.changePasswordProcess(new User("testUser")));
    }

    @Test
    void testChangePasswordProcess_NewPasswordBackOption() {
        // Simulates user input for changing password with 'back' option for new password
        String input = "testPassword\nback\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertDoesNotThrow(() -> MainApplication.changePasswordProcess(new User("testUser")));
    }

    @Test
    void testDirectMessageMenu_SendMessageBackOption() {
        // Simulates user input for direct message menu with 'back' option for sending a message
        String input = "1\nback\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute direct message menu method
        assertDoesNotThrow(() -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser")));
    }

    @Test
    void testDirectMessageMenu_DeleteMessageBackOption() {
        // Simulates user input for direct message menu with 'back' option for deleting a message
        String input = "2\nback\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute direct message menu method
        assertDoesNotThrow(() -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser")));
    }
}
