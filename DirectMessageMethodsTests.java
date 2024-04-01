import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
/**
 * DirectMessageMethodsTests.java
 * 
 * This class tests the direct message methods validating that the methods work as intended.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */
@RunWith(Enclosed.class)
public class DirectMessageMethodsTests {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
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

    public static class TestCase {
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

        // this will cover getFileName too
        // Message will be read via file
        @Test(timeout = 1000)
        public void openMessagesTest() {
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

        @Test(timeout = 1000)
        public void readMessagesTest() {
            try {
                // Set the input
                String input = "1\narushichaudhary\nTest123\n1\nTest03312024023437\n1\n3\n1\ntesting\n";

                String expected = "2. arushichaudhary: testing";
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
            } catch (Exception Ex) {
                String resutl = Ex.toString();
            }
        }

        // this will cover all the test cases or methods
        @Test(timeout = 1000)
        public void sendMessagesTest() {
            try {
                // Set the input
                String input = "1\narushichaudhary\nTest123\n1\nTest03312024023437\n1\n3\n1\nSending a new message\n";

                String expected = "3. arushichaudhary: Sending a new message";
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
            } catch (Exception Ex) {
                String result = Ex.toString();
            }
        }
        
        @Test
        public void testDirectMessageMethodsImplementsDirectMessageInterface() {
        // Create an instance of SearchMethods
        DirectMessageMethods directMessageMethods = new DirectMessageMethods();

        // Check if SearchMethods implements SearchInterface
        assertTrue(directMessageMethods instanceof DirectMessageInterface, "DirectMessageMethods should implement DirectMessagInterface");
    }
    }
}
