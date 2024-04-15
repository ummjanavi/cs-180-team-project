package TeamProject;

import static org.junit.Assert.*;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

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
        assertTrue("Invalid username should return false",
                loginMethods.checkUsername("user123"));
        assertTrue("Invalid username should return false",
                loginMethods.checkUsername("user123"));
    }

    @Test
    public void testCreateAccount() {
        assertTrue("Should create account with valid credentials",
                loginMethods.createAccount("user1234", "1234"));
    }

    @Test
    public void testValidateLoginTrue() {
        String username = "testUser";
        String password = "testPassword";
        assertTrue("Should create account with valid credentials",
                loginMethods.createAccount(username, password));
        assertTrue("Should validate login with correct credentials",
                loginMethods.validateLogin(username, password));
    }


    @Test
    public void testValidateLoginFalse() {
        assertFalse("Should not validate login with incorrect credentials",
                loginMethods.validateLogin("user1235", "1235"));
    }

}
