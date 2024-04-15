package TeamProject;

import static org.junit.Assert.*;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class MainApplicationTest {

    private final InputStream systemIn = System.in;
    private ByteArrayInputStream testIn;

    @Before
    public void setUpInput() {
        testIn = new ByteArrayInputStream("".getBytes());
        System.setIn(testIn);
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
    }

    @Test
    public void testLoginProcessValidCredentials() {
        // Simulates user input for login
        String input = "testUser\n" + "testPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute login process
        try {
            MainApplication.loginProcess(new Scanner(System.in));
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void testLoginProcessInvalidCredentials() {
        // Simulates user input for login with invalid password
        String input = "testUser\n" + "wrongPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute login process
        assertDoesNotThrow(() -> MainApplication.loginProcess(new Scanner(System.in)));
    }

    @Test
    public void testAccountCreationProcessSuccess() {
        // Simulates user input for creating an account
        String input = "newUser\n" + "newPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute account creation process
        assertDoesNotThrow(() -> MainApplication.accountCreationProcess(new Scanner(System.in)));
    }

    @Test
    public void testAccountCreationProcessFailure() {
        // Simulates user input for creating an account with existing username
        String input = "existingUser\n" + "newPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute account creation process
        assertDoesNotThrow(() -> MainApplication.accountCreationProcess(new Scanner(System.in)));
    }

    @Test
    public void testShowMainMenuValidChoice() {
        // Simulates user input for main menu choice
        String input = "1\nback\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute show main menu method
        assertDoesNotThrow(() -> MainApplication.showMainMenu(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testChangePasswordProcessSuccess() {
        // Simulates user input for changing password
        String input = "testPassword\n" + "newPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertDoesNotThrow(() -> MainApplication.changePasswordProcess(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testChangePasswordProcessFailure() {
        // Simulates user input for changing password with incorrect old password
        String input = "wrongPassword\n" + "newPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertDoesNotThrow(() -> MainApplication.changePasswordProcess(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testDirectMessageMenuValidChoice() {
        // Simulates user input for direct message menu choice
        String input = "1\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute direct message menu method
        assertDoesNotThrow(() -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testChangeDirectMessageSettingValidChoice() {
        // Simulates user input for changing direct message privacy
        String input = "1\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.changeDirectMessageSetting(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testChangeDirectMessageSettingInvalidChoice() {
        // Simulates user input for an invalid choice
        String input = "invalid\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.changeDirectMessageSetting(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testSearchProcessNoMatch() {
        // Simulates user input for searching with no match
        String input = "nonexistentUser\nback\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.searchProcess(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testUserViewerMenuInvalidChoice() {
        // Simulates user input for an invalid choice in user viewer menu
        String input = "invalid\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.userViewerMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testLoginProcessUsernameBackOption() {
        // Simulates user input for "back" option during username input
        String input = "back\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute login process
        assertDoesNotThrow(() -> MainApplication.loginProcess(new Scanner(System.in)));
    }

    @Test
    public void testLoginProcessPasswordBackOption() {
        // Simulates user input for login with 'back' option for password
        String input = "testUser\nback\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute login process
        assertDoesNotThrow(() -> MainApplication.loginProcess(new Scanner(System.in)));
    }

    @Test
    public void testAccountCreationProcessUsernameBackOption() {
        // Simulates user input for creating an account with 'back' option for username
        String input = "back\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute account creation process
        assertDoesNotThrow(() -> MainApplication.accountCreationProcess(new Scanner(System.in)));
    }

    @Test
    public void testAccountCreationProcessPasswordBackOption() {
        // Simulates user input for creating an account with 'back' option for password
        String input = "newUser\nback\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute account creation process
        assertDoesNotThrow(() -> MainApplication.accountCreationProcess(new Scanner(System.in)));
    }

    @Test
    public void testShowMainMenuInvalidChoice() {
        // Simulates user input for main menu with invalid choice
        String input = "invalid\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute show main menu method
        assertDoesNotThrow(() -> MainApplication.showMainMenu(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testChangePasswordProcessOldPasswordBackOption() {
        // Simulates user input for changing password with 'back' option for old password
        String input = "back\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertDoesNotThrow(() -> MainApplication.changePasswordProcess(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testChangePasswordProcessNewPasswordBackOption() {
        // Simulates user input for changing password with 'back' option for new password
        String input = "testPassword\nback\nexit\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertDoesNotThrow(() -> MainApplication.changePasswordProcess(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testDirectMessageMenuSendMessageBackOption() {
        // Simulates user input for direct message menu with 'back' option for sending a message
        String input = "1\nback\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute direct message menu method
        assertDoesNotThrow(() -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testDirectMessageMenuDeleteMessageBackOption() {
        // Simulates user input for direct message menu with 'back' option for deleting a message
        String input = "2\nback\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute direct message menu method
        assertDoesNotThrow(() -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testChangeDirectMessageSettingCancel() {
        // Simulates user input for changing direct message privacy and then canceling
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.changeDirectMessageSetting(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testSearchProcessBackOption() {
        // Simulates user input for searching and then choosing 'back'
        String input = "back\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.searchProcess(new User("testUser"), new Scanner(System.in)));
    }

    @Test
    public void testUserViewerMenuExit() {
        // Simulates user input for user viewer menu and then choosing 'exit'
        String input = "4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.userViewerMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testUserViewerMenuAddRemoveFriendSuccess() {
        // Simulates user input for adding and then removing a friend
        String input = "1\n1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.userViewerMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testUserViewerMenuAddBlockedUserSuccess() {
        // Simulates user input for adding a blocked user
        String input = "2\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.userViewerMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testUserViewerMenuRemoveBlockedUserSuccess() {
        // Simulates user input for removing a blocked user
        String input = "2\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.userViewerMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testDirectMessageMenuSendMessageSuccess() {
        // Simulates user input for sending a message
        String input = "1\nhello\nback\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertDoesNotThrow(() -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testDirectMessageMenuDeleteMessageSuccess() {
        // Simulates user input for deleting a message
        String input = "2\n1\nback\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Executes the method
        assertDoesNotThrow(() -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testLoginProcessNullScanner() {
        // Tests when the scanner passed to loginProcess is null
        assertThrows(NullPointerException.class, () -> MainApplication.loginProcess(null));
    }

    @Test
    public void testAccountCreationProcessNullScanner() {
        // Tests when the scanner passed to accountCreationProcess is null
        assertThrows(NullPointerException.class, () -> MainApplication.accountCreationProcess(null));
    }

    @Test
    public void testShowMainMenuNullUser() {
        // Tests when the user passed to showMainMenu is null
        String input = "1\nback\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute show main menu method
        assertThrows(NullPointerException.class, () -> MainApplication.showMainMenu(null, new Scanner(System.in)));
    }

    @Test
    public void testShowMainMenuNullScanner() {
        // Tests when the scanner passed to showMainMenu is null
        assertThrows(NullPointerException.class, () -> MainApplication.showMainMenu(new User("testUser"), null));
    }

    @Test
    public void testChangePasswordProcessNullUser() {
        // Tests when the user passed to changePasswordProcess is null
        String input = "testPassword\nnewPassword\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute change password process
        assertThrows(NullPointerException.class, () -> MainApplication.changePasswordProcess(null, new Scanner(System.in)));
    }

    @Test
    public void testChangePasswordProcessNullScanner() {
        // Tests when the scanner passed to changePasswordProcess is null
        assertThrows(NullPointerException.class, () -> MainApplication.changePasswordProcess(new User("testUser"), null));
    }

    @Test
    public void testDirectMessageMenuNullSender() {
        // Tests when the sender passed to directMessageMenu is null
        String input = "1\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute direct message menu method
        assertThrows(NullPointerException.class, () -> MainApplication.directMessageMenu(null, new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testDirectMessageMenuNullReceiver() {
        // Tests when the receiver passed to directMessageMenu is null
        String input = "1\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Execute direct message menu method
        assertThrows(NullPointerException.class, () -> MainApplication.directMessageMenu(new User("testUser"), null, new Scanner(System.in)));
    }

    @Test
    public void testDirectMessageMenuNullScanner() {
        // Tests when the scanner passed to directMessageMenu is null
        assertThrows(NullPointerException.class, () -> MainApplication.directMessageMenu(new User("testUser"), new User("anotherUser"), null));
    }

    @Test
    public void testChangeDirectMessageSettingNullUser() {
        // Tests when the user passed to changeDirectMessageSetting is null
        assertThrows(NullPointerException.class, () -> MainApplication.changeDirectMessageSetting(null, new Scanner(System.in)));
    }

    @Test
    public void testChangeDirectMessageSettingNullScanner() {
        // Tests when the scanner passed to changeDirectMessageSetting is null
        assertThrows(NullPointerException.class, () -> MainApplication.changeDirectMessageSetting(new User("testUser"), null));
    }

    @Test
    public void testSearchProcessNullUser() {
        // Tests when the user passed to searchProcess is null
        String input = "nonexistentUser\nback\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertThrows(NullPointerException.class, () -> MainApplication.searchProcess(null, new Scanner(System.in)));
    }

    @Test
    public void testSearchProcessNullScanner() {
        // Tests when the scanner passed to searchProcess is null
        assertThrows(NullPointerException.class, () -> MainApplication.searchProcess(new User("testUser"), null));
    }

    @Test
    public void testUserViewerMenu_NullViewer() {
        // Tests when the viewer passed to userViewerMenu is null
        String input = "invalid\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertThrows(NullPointerException.class, () -> MainApplication.userViewerMenu(null, new User("anotherUser"), new Scanner(System.in)));
    }

    @Test
    public void testUserViewerMenuNullUser() {
        // Tests when the user passed to userViewerMenu is null
        String input = "invalid\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Execute the method
        assertThrows(NullPointerException.class, () -> MainApplication.userViewerMenu(new User("testUser"), null, new Scanner(System.in)));
    }

    @Test
    public void testUserViewerMenuNullScanner() {
        // Tests when the scanner passed to userViewerMenu is null
        assertThrows(NullPointerException.class, () -> MainApplication.userViewerMenu(new User("testUser"), new User("anotherUser"), null));
    }
}
