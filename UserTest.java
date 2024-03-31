import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;


public class UserTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCaseUser.class);
        System.out.printf("Test Count: %d.\n", result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.printf("Excellent - all local tests ran successfully.\n");
        } else {
            System.out.printf("Tests failed: %d.\n", result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class TestCaseUser {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }


        @Test(timeout = 1000)
        // This will cover all the USER constructors
        // It will create a test user
        // this will cover getProfilePic and setProfilePic testing
        public void UserTest() {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");
                String formattedDateTime = LocalDateTime.now().format(formatter);
                String newUser = "Test" + formattedDateTime;

                // Set the input
                String input = "2" + "\n" + newUser + "\n" + "Test123\n";

                String expected = "Account Created Successfully. Sign in using 'Login'";
                // Runs the program with the input values
                receiveInput(input);
                MainApplication.main(new String[0]);

                // Retrieves the output from the program
                String output = getOutput();

                // Trims the output and verifies it is correct.
                expected = expected.replaceAll("\r\n", "\n");
                output = output.replaceAll("\r\n", "\n");
                assertEquals("\nLogin Menu\n" + "1. Login\n" + "2. Create Account\n" + "Enter choice, or type 'exit' to close application:",
                        expected.trim(), output.trim());
            } catch (Exception Ex) {
                String Message = "";
            }
        }

        @Test(timeout = 1000)
        // for Missing username test
        public void getUserNameTest() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            String newUser = "Test" + formattedDateTime;

            // Set the input
            String input = "1" + "\n" + newUser + "\n" + "Test123\n";

            String expected = "User does not exist." + System.lineSeparator() + "Returning to Login Menu";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("\nLogin Menu\n" + "1. Login\n" + "2. Create Account\n" + "Enter choice, or type 'exit' to close application:",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        // This will cover both the method testing
        // this will cover setPassword too
        public void getPasswordTest() {
            // Set the input
            String input = "1\narushichaudhary\nTest123\n2\n1\nTest123\nTest132\n";

            String expected = "Password changed successfully.\nReturning...";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("\nAccount Settings\n" + "1. Change account password\n" +
                            "2. Change direct messaging privacy\n" + "3. Return to Main Menu" + "Enter choice:",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        // This will cover both the method testing
        // this will cover getPassword too
        public void setPasswordTest() {
            // Set the input
            String input = "1\narushichaudhary\nTest132\n2\n1\nTest132\nTest123\n";

            String expected = "Password changed successfully.\nReturning...";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("\nAccount Settings\n" + "1. Change account password\n" +
                            "2. Change direct messaging privacy\n" + "3. Return to Main Menu" + "Enter choice:",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void addFriendTest() {
            // Set the input
            String input = "1\narushichaudhary\nTest123\n1\nTest03312024023437\n1\n1\n";

            String expected = "Test03312024023437 added as a friend!";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);
            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("1. Add/Remove User as friend.\n" + "2. Block/Unblock User\n" +
                            "3. Direct Message\n4. Exit\nEnter choice:\n",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void removeFriendTest() {
            // Set the input
            String input = "1\narushichaudhary\nTest123\n1\nTest03312024023437\n1\n1\n";

            String expected = "Test03312024023437 removed as a friend!";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);
            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("1. Add/Remove User as friend.\n" + "2. Block/Unblock User\n" +
                            "3. Direct Message\n4. Exit\nEnter choice:\n",
                    expected.trim(), output.trim());
        }

        // Similar method as add/remove friends
        // as getFriends will be called during add/remove friends
        @Test(timeout = 1000)
        public void getFriendsTest() {
            // Set the input
            String input = "1\narushichaudhary\nTest123\n1\nTest03312024023437\n1\n1\n";

            String expected = "Test03312024023437 removed as a friend!";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);
            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("1. Add/Remove User as friend.\n" + "2. Block/Unblock User\n" +
                            "3. Direct Message\n4. Exit\nEnter choice:\n",
                    expected.trim(), output.trim());
        }

        // this will cover setBlocked too
        @Test(timeout = 1000)
        public void blockUserTest() {
            // Set the input
            String input = "1\narushichaudhary\nTest123\n1\nTest03312024023437\n1\n2\n";

            String expected = "Test03312024023437 successfully blocked.";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);
            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("1. Add/Remove User as friend.\n" + "2. Block/Unblock User\n" +
                            "3. Direct Message\n4. Exit\nEnter choice:\n",
                    expected.trim(), output.trim());
        }

        // this will cover setBlocked too
        @Test(timeout = 1000)
        public void unblockUserTest() {
            // Set the input
            String input = "1\narushichaudhary\nTest123\n1\nTest03312024023437\n1\n2\n";

            String expected = "Successfully unblocked Test03312024023437";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);
            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("1. Add/Remove User as friend.\n" + "2. Block/Unblock User\n" +
                            "3. Direct Message\n4. Exit\nEnter choice:\n",
                    expected.trim(), output.trim());
        }


        // this sequence will cover the following methods
        //  isOpenMessaging
        // setOpenMessaging
        //  displayProfile

        @Test(timeout = 1000)
        public void isOpenMessagingTest() {
            // Set the input
            String input = "1\narushichaudhary\nTest123\n1\nTest03312024023437\n1\n3\n1\nhello again\n";

            String expected = "1. arushichaudhary: hello again";
            // Runs the program with the input values
            receiveInput(input);
            MainApplication.main(new String[0]);
            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Direct Message Options:\n" + "1. Send Message\n" +
                            "2. Delete Message\n" +
                            "3. Exit\n",
                    expected.trim(), output.trim());
        }
    }
}
