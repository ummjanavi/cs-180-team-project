import static org.junit.Assert.*;
import org.junit.*;

public class AppServerTest {
    private static LoginMethods loginMethods = new LoginMethods();
    private static SearchMethods searchMethods = new SearchMethods();
    private static DirectMessageMethods directMessageMethods = new DirectMessageMethods();

    @Test
    public void testCheckUsernameValid() {
        assertTrue("Valid username should return true",
                loginMethods.checkUsername("user123"));
    }

    @Test
    public void testCheckUsernameInvalid() {
        assertFalse("Invalid username should return false",
                loginMethods.checkUsername("user123"));
    }

    @Test
    public void testCreateAccount() {
        assertTrue("Should create account with valid credentials",
                loginMethods.createAccount("user1234", "1234"));
    }

    @Test
    public void testValidateLoginTrue() {
        assertTrue("Should validate login with correct credentials",
                loginMethods.validateLogin("user1234", "1234"));
    }

    @Test
    public void testValidateLoginFalse() {
        assertFalse("Should not validate login with incorrect credentials",
                loginMethods.validateLogin("user1235", "1235"));
    }

    @Test
    public void testHandleClientValidInput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        String clientInput = "user:testUser|testPassword";
        assertDoesNotThrow(() -> AppServer.handleClient(new BufferedReader(new StringReader(clientInput)), writer));
    }

    @Test
    public void testHandleClientInvalidInput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        String clientInput = "invalidFormat";
        assertDoesNotThrow(() -> AppServer.handleClient(new BufferedReader(new StringReader(clientInput)), writer));
    }
}
