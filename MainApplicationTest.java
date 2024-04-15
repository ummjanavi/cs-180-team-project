import static org.junit.Assert.assertEquals;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MainApplicationTest {

    private InputStream originalSystemIn;
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream outputStream;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public void setUp() {
        // Initialize necessary resources
        originalSystemIn = System.in;
        originalSystemOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Initialize socket, reader, and writer (mocks or stubs)
        // Initialize the socket, reader, and writer as needed for testing
    }

    public void tearDown() {
        // Clean up resources
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testShowLoginMenu_Exit() {
        // Prepare test data
        String input = "exit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
            MainApplication mainApp = new MainApplication(null);

            // Call the method being tested
            mainApp.showLoginMenu(new Scanner(System.in));

            // Verify the output
            String expectedOutput = "\nLogin Menu\n1. Login\n2. Create Account\nEnter choice, or type 'exit' to close application:\nExiting application...\n";
            assertEquals(expectedOutput, outputStream.toString());

        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }


    public void testShowLoginMenu_Login() {
        // Prepare test data
        String input = "1\nusername\npassword\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Save the original System.in and System.out
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        try {
            // Redirect System.in and System.out
            System.setIn(inputStream);
            System.setOut(new PrintStream(outputStream));

            // Create MainApplication instance
            MainApplication mainApp = new MainApplication(null);

            // Call the method being tested
            mainApp.showLoginMenu(new Scanner(System.in));

            // Verify the output
            String expectedOutput = "\nLogin Menu\n1. Login\n2. Create Account\nEnter choice, or type 'exit' to close application:\nEnter username or enter 'back' to return to the Login Menu:\nEnter password or enter 'back' to return to the Login Menu:\nExiting application...\n";
            assertEquals(expectedOutput, outputStream.toString());

        } finally {
            // Restore the original System.in and System.out
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    public void testShowLoginMenu_CreateAccount() {
        // Prepare test data
        String input = "2\nusername\npassword\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
            MainApplication mainApp = new MainApplication(null);

            // Call the method being tested
            mainApp.showLoginMenu(new Scanner(System.in));

            // Verify the output
            String expectedOutput = "\nLogin Menu\n1. Login\n2. Create Account\nEnter choice, or type 'exit' to close application:\nEnter your desired username or enter 'back' to return to the Login Menu:\nEnter your desired password or enter 'back' to return to the Login Menu:\nAccount Created Successfully. Sign in using 'Login'\nExiting application...\n";
            assertEquals(expectedOutput, outputStream.toString());

        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }

    public void testLoginProcess_Successful() {
        // Prepare test data
        String input = "username\npassword\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
            MainApplication mainApp = new MainApplication(null);

            // Call the method being tested
            mainApp.loginProcess(new Scanner(System.in));

            // Verify the output
            String expectedOutput = "Login Successful!\n";
            assertEquals(expectedOutput, outputStream.toString());

        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }

    public void testLoginProcess_Failed() {
        // Prepare test data
        String input = "username\npassword\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
            MainApplication mainApp = new MainApplication(null);

            // Call the method being tested
            mainApp.loginProcess(new Scanner(System.in));

            // Verify the output
            String expectedOutput = "Login failed. Incorrect username or password.\nReturning to Login Menu\n";
            assertEquals(expectedOutput, outputStream.toString());

        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }
}    
    
