package TeamProject;

import com.sun.jdi.request.WatchpointRequest;
import com.sun.tools.javac.Main;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ScatteringByteChannel;
import java.sql.SQLOutput;
import java.util.*;

/**
 * MainApplication.java
 * <p>
 * This class runs the app, Friendify. This is the main application.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */
public class MainApplication extends Thread {
    private static LoginMethods loginMethods = new LoginMethods();
    private static SearchMethods searchMethods = new SearchMethods();
    private static DirectMessageMethods directMessageMethods = new DirectMessageMethods();
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public MainApplication(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    } // constructor

    public static void main(String[] args) {
        // Connect to the server and create reader/writer as before
        // Create MainApplication instance
        MainApplication client = null;
        try {
            client = new MainApplication(new Socket("localhost", 4545));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Start the thread
        client.start();
    }


    @Override
    public void run() {
        try (Scanner scan = new Scanner(System.in)) {
            while (!this.socket.isClosed()) {
                showLoginMenu(scan);
            }
        } finally {
            try {
                if (writer != null) writer.close();
                if (reader != null) reader.close();
                if (socket != null) socket.close();
            } catch (IOException f) {
                System.out.println("Failed to close writer, reader, or socket.");
            }
        }

        // Repeatedly show the login menu until the application is exited
    } //main()

    private void showLoginMenu(Scanner scan) {
        try {
            System.out.println("\nLogin Menu");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("Enter choice, or type 'exit' to close application:");

            String choice = scan.nextLine().trim();// Getting user input, 'choice'

            if ("exit".equalsIgnoreCase(choice)) {
                System.out.println("Exiting application...");
                socket.close();
                return;
            } // if they type exit, ignores case

            switch (choice) {
                case "1": // "Login" option
                    loginProcess(scan);
                    break;
                case "2": // "Create Account" option
                    accountCreationProcess(scan);
                    break;
                default: // Neither option.
                    System.out.println("Invalid choice. Please try again.");
                    break; // Remains in the Login Menu, Re-displaying the options
            }
        } catch (IOException e) {

        }
        // Presenting Login Menu Options

    } //showLoginMenu()

    private void loginProcess(Scanner scan) throws IOException {

        System.out.println("Enter username or enter 'back' to return to the Login Menu:");
        String username = scan.nextLine().trim();
        if ("back".equalsIgnoreCase(username)) {
            return; // Return to showLoginMenu
        }

        System.out.println("Enter password or enter 'back' to return to the Login Menu:");
        String password = scan.nextLine().trim();
        if ("back".equalsIgnoreCase(password)) {
            return; // Return to showLoginMenu
        }
        writer.write("VALIDATE_LOGIN");
        writer.println();
        writer.write(username);
        writer.println();
        writer.write(password);
        writer.println();
        writer.flush();

        //// THIS PROCESS NEEDS SERVER CLIENT INTERACTION
        if ((reader.readLine()).equals("true")) {
            System.out.println("Login Successful!");
            User currentUser = new User(username); //Calls User(username) to read the user's file and create currentUser
            showMainMenu(currentUser, scan); // Transition to main menu after successful login
        } else {
            System.out.println("Login failed. Incorrect username or password.\nReturning to Login Menu");
            return;
        }
    } //loginProcess()

    private void accountCreationProcess(Scanner scan) {
        try {
            System.out.println("Enter your desired username or enter 'back' to return to the Login Menu:");
            String username = scan.nextLine().trim();
            if ("back".equalsIgnoreCase(username)) {
                return; // Return to showLoginMenu
            }
            writer.write("CHECK_USERNAME");
            writer.println();
            writer.write(username);
            writer.println();
            writer.flush();
            // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
            if ((reader.readLine()).equals("false")) {
                System.out.println("Username taken! Please try again with another.");
                return; //if login methods returns false (the username already exits), program returns to showLoginMenu
                // method writes error to terminal.
            }
            System.out.println("Enter your desired password or enter 'back' to return to the Login Menu:");
            String password = scan.nextLine().trim();
            if ("back".equalsIgnoreCase(password)) {
                return; // Return to showLoginMenu
            }
            writer.write("CREATE_ACCOUNT");
            writer.println();
            writer.write(username);
            writer.println();
            writer.write(password);
            writer.println();
            writer.flush();
            // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
            if ((reader.readLine()).equals("true")) { //if the account is created successfully
                System.out.println("Account Created Successfully. Sign in using 'Login'");
                new User(username, password);
            } else {
                System.out.println("There was an error creating your account. Please try again.");
            }
        } catch (IOException e) {

        }

    } //accountCreationProcess

    private void showMainMenu(User currentUser, Scanner scan) {
        String choice;
        do {
            // Showing main menu options
            System.out.println("\nMain Menu");
            System.out.println("1. Search for a user");
            System.out.println("2. Account settings");
            System.out.println("3. Logout");
            System.out.println("Enter choice:");

            choice = scan.nextLine().trim();

            switch (choice) {
                case "1": // "Search for a user" option
                    searchProcess(currentUser, scan);
                    break; // breaks out of switch, re-displays menu options
                case "2": // "Account settings" option
                    // Placeholder for account settings functionality
                    showAccountSettings(currentUser, scan);
                    break; // breaks out of switch, re-displays menu options
                case "3": // "Logout" option.
                    System.out.println("Logging out...");
                    //currentUser.writeToFile();
                    break; // breaks out of switch, re-displays menu options
                default: // if user inputs anything but the 3 choices
                    System.out.println("Invalid choice. Please try again.");
                    break; // breaks out of switch, re-displays menu options
            } // end switch

        } while (!choice.equals("3"));
        // This loop will continue until the user chooses "logout"
    } //showMainMenu

    private void showAccountSettings(User currentUser, Scanner scan) {
        // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
        String choice;
        do {
            // Showing account settings options
            System.out.println("\nAccount Settings");
            System.out.println("1. Change account password");
            System.out.println("2. Change direct messaging privacy");
            System.out.println("3. Return to Main Menu");
            System.out.println("Enter choice:");

            choice = scan.nextLine().trim();
            switch (choice) {
                case "1": // "Change account password" option.
                    changePasswordProcess(currentUser, scan);
                    break; //breaks out of switch, re displaying account settings options.
                case "2": // "Change direct messaging privacy" option.
                    changeDirectMessageSetting(currentUser, scan);
                    break; //breaks out of switch, re displaying account settings options.
                case "3": //"Return to Main Menu"
                    System.out.println("Returning...");
                    break; //breaks out of switch, re displaying account settings options.
                default: // if choice is anything but the three options
                    System.out.println("Invalid choice. Please try again.");
                    break; //breaks out of switch, re displaying account settings options.
            } //end switch
        } while (!choice.equals("3"));
    } //showAccountSettings

    private void changePasswordProcess(User currentUser, Scanner scan) {
        try {
            // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
            System.out.println("To change your password, enter your old password.");
            System.out.println("or enter 'back' to return");
            String oldPassword = scan.nextLine().trim();

            if ("back".equalsIgnoreCase(oldPassword)) {
                return; // returns to showAccountSettings
            }
            if (!(oldPassword.equals(currentUser.getPassword()))) {
                System.out.println("Incorrect Password. Try again");
                return; // returns to showAccountSettings
            }
            System.out.println("Enter your new password");
            System.out.println("or enter 'back' to return");

            String newPassword = scan.nextLine().trim();
            if ("back".equalsIgnoreCase(newPassword)) {
                return; // returns to showAccountSettings
            }
            currentUser.setPassword(newPassword);
            writer.write("CHANGE_PASSWORD");
            writer.println();
            writer.write(currentUser.getUsername());
            writer.println();
            writer.write(newPassword);
            writer.println();
            writer.flush();
            //if (currentUser.writeToFile()) {
            if (reader.readLine().equals("true")) {
                System.out.println("Password changed successfully.\nReturning...");
            } else {
                // if there's an error writing to the user's file, change the password back to the old password in case
                // the user's file gets written again and IS successful.
                System.out.println("There was an error changing your password.\nTry again.");
                currentUser.setPassword(oldPassword);
            }
        } catch (IOException e) {

        }

    } //changePasswordProcess()

    private void changeDirectMessageSetting(User currentUser, Scanner scan) {
        try {
            // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
            String choice;
            do {
                // Showing direct messaging setting options
                System.out.println("Direct Messaging Privacy Choices:");
                System.out.println("1. Open to everyone");
                System.out.println("2. Open to just your friends");
                System.out.println("3. Cancel");
                System.out.println("Enter choice:");

                choice = scan.nextLine().trim();
                boolean oldSetting = currentUser.isOpenMessaging();
                switch (choice) {
                    case "1": //"Open to everyone"
                        //currentUser.setOpenMessaging(true);
                        writer.write("SET_OPEN_MESSAGING_TRUE");
                        writer.println();
                        writer.write(currentUser.getUsername());
                        writer.println();
                        writer.flush();
                        //if (!currentUser.writeToFile()) {
                        if (reader.readLine().equals("false")) {
                            //currentUser.setOpenMessaging(oldSetting);
                            writer.write("SET_OPEN_MESSAGING");
                            writer.println();
                            writer.write(currentUser.getUsername());
                            writer.println();
                            writer.write(String.valueOf(oldSetting));
                            writer.println();
                            writer.flush();
                            System.out.println("An error occurred. Could not update your settings.");
                            break; // breaks out of switch, re displays the direct message options
                        } else {
                            System.out.println("Successfully updated your direct messaging to open to all users.");
                        }
                        return; //returns back to showAccountSettings
                    case "2":
                        currentUser.setOpenMessaging(false);
                        writer.write("SET_OPEN_MESSAGING_FALSE");
                        writer.println();
                        writer.write(currentUser.getUsername());
                        writer.println();
                        writer.flush();
                        if (reader.readLine().equals("false")) {
                            currentUser.setOpenMessaging(oldSetting);
                            writer.write("SET_OPEN_MESSAGING");
                            writer.println();
                            writer.write(String.valueOf(oldSetting));
                            writer.println();
                            writer.flush();
                            System.out.println("An error occurred. Could not update your settings.");
                            break; // breaks out of switch, re displays the direct message options
                        } else {
                            System.out.println("Successfully updated your direct messaging to open to only friends.");
                        }
                        return; //returns back to showAccountSettings
                    case "3":
                        System.out.println("Returning...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break; // breaks out of switch, re displays the direct message options
                }
            } while (!choice.equals("3"));
        } catch (IOException e) {

        }
    } //directMessageSettings

    private void searchProcess(User currentUser, Scanner scan) {
        // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
        System.out.println("Search user or enter 'back' to return to main menu");
        String search = scan.nextLine().trim();
        if (search.equals("back")) {
            System.out.println("Returning...");
        } else { // if user enters anything but 'back'
            writer.write("SEARCH_METHODS");
            writer.println();
            writer.write(search);
            writer.println();
            writer.flush();
            ArrayList<String> results = searchMethods.searchUsers(search);
            //how do i do client server for this
            results.remove(currentUser.getUsername()); // removes currentUser from list = does not display currentUser
            if (results.isEmpty()) {
                System.out.println("No matched users");
                return;
            } else { //prints results in a numbered list
                for (int i = 0; i < results.size(); i++) {
                    System.out.println((i + 1) + ". " + results.get(i));
                }
            }

            while (true) {
                System.out.println("Enter the number of the user you want to select, or type 'back' to return:");
                String userNumberStr = scan.nextLine().trim();

                if ("back".equalsIgnoreCase(userNumberStr)) {
                    return;
                }
                try {
                    int userNumber = Integer.parseInt(userNumberStr) - 1; // Convert to 0-based index
                    if (userNumber >= 0 && userNumber < results.size()) {
                        // Valid selection, proceed with userViewer or similar
                        String selectedUser = results.get(userNumber);
                        User searchedUser = new User(selectedUser);
                        writer.write("DISPLAY_PROFILE");
                        writer.println();
                        writer.write(selectedUser);
                        writer.println();
                        writer.flush();
                        System.out.println(reader.readLine());
                        System.out.println(reader.readLine());
                        System.out.println(reader.readLine());
                        userViewerMenu(currentUser, searchedUser, scan);
                        break; //breaks out of while loop after user is done with userViewerMenu
                    } else {
                        // user picks number not in the list
                        System.out.println("Error: Selection out of range. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid input. Please enter a number");
                } catch (IOException f) {
                    System.out.println("Error reading from server");
                }
            } //end while
        }
    } //searchProcess()

    private void userViewerMenu(User currentUser, User searchedUser, Scanner scan) {
        try {
            // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
            String choice;
            do {
                // Showing user viewer options
                System.out.println("1. Add/Remove User as friend.");
                System.out.println("2. Block/Unblock User");
                System.out.println("3. Direct Message");
                System.out.println("4. Exit");
                System.out.println("Enter choice:");

                choice = scan.nextLine().trim();
                String status = "";
                switch (choice) {
                    case "1": // "Add/Remove" option. THIS NEEDS TO BE SYNCHRONIZED
                        writer.write("ADD_FRIEND");
                        writer.println();
                        writer.write(currentUser.getUsername());
                        writer.println();
                        writer.write(searchedUser.getUsername());
                        writer.println();
                        writer.flush();

                        status = reader.readLine();
                        System.out.println(status);
                        break;
                    case "2": //"Block/Unblock" option
                        writer.write("BLOCK_USER");
                        writer.println();
                        writer.write(currentUser.getUsername());
                        writer.println();
                        writer.write(searchedUser.getUsername());
                        writer.println();
                        writer.flush();

                        status = reader.readLine();
                        System.out.println(status);
                        break; //breaks out of switch block, re displays view user options
                    case "3": //"Direct message" option.
                        directMessageMenu(currentUser, searchedUser, scan);
                        break; //breaks out of switch block, re displays view user options
                    case "4": //"Exit"
                        System.out.println("Returning...");
                        break; //breaks out of switch block, re displays view user options
                    default: // if user inputs anything but the choices
                        System.out.println("Invalid choice. Please try again.");
                        break; //breaks out of switch block, re displays view user options
                } //end switch block
            } while (!choice.equals("4"));
        } catch (IOException e) {

        }

    } //userViewerMenu

    private void directMessageMenu(User currentUser, User searchedUser, Scanner scan) {
        int num = 0;
        int flag1 = 1;
        try {
            writer.write("CHECK_DIRECT");
            writer.println();
            writer.write(currentUser.getUsername());
            writer.println();
            writer.write(searchedUser.getUsername());
            writer.println();
            writer.flush();

            String status = reader.readLine();
            if (!status.equals("GOOD_TO_MESSAGE")) {
                System.out.println(status);
                return;
            } else {
                // if they are allowed to direct message! completes actions below.
                String choice = null;
                String answer = reader.readLine(); // needed to read empty line
                do {
                    writer.write("OPEN_MESSAGES");
                    writer.println();
                    writer.write(currentUser.getUsername());
                    writer.println();
                    writer.write(searchedUser.getUsername());
                    writer.println();
                    writer.flush();
                    String answer2 = reader.readLine();
                    //System.out.println("first " + answer2);
                    if (answer2.equals("false")) {
                        //if (!directMessageMethods.openMessages(currentUser, searchedUser)) {
                        System.out.println("Error creating message file.");
                        break; //
                    }
                    List<String> messages = directMessageMethods.readMessages(currentUser, searchedUser);
                    writer.write("READ_AND_DISPLAY_MESSAGES");
                    writer.println();
                    writer.write(currentUser.getUsername());
                    writer.println();
                    writer.write(searchedUser.getUsername());
                    writer.println();
                    writer.flush();


                    for (int j = 1; j < flag1; j++) {
                        if (choice.equals("1")) {
                            String h = reader.readLine();
                            //System.out.println(h);
                        }
                    }
                    /*
                    String yes = reader.readLine();
                    yes = reader.readLine();
                    yes = reader.readLine(); */

                    num = Integer.parseInt(reader.readLine());

                    System.out.println("==================================");
                    if (flag1 == 1) {
                        flag1++;
                    }
                    for (int i = 0; i < num; i++) {
                        System.out.println(reader.readLine());
                    }
                    System.out.println("==================================");

                    // showing direct message options
                    System.out.println("Direct Message Options:");
                    System.out.println("1. Send Message");
                    System.out.println("2. Delete Message");
                    System.out.println("3. Exit");
                    choice = scan.nextLine().trim();
                    switch (choice) {
                        case "1": //"Send Message" option.
                            System.out.println("Enter your message or enter 'back' to return:");
                            String message = scan.nextLine();
                            if ("back".equalsIgnoreCase(message)) {
                                return; // Return to showLoginMenu
                            } else { //anything but 'back'
                                writer.write("SEND_MESSAGE");
                                writer.println();
                                writer.write(currentUser.getUsername());
                                writer.println();
                                writer.write(searchedUser.getUsername());
                                writer.println();
                                writer.write(message);
                                writer.println();
                                writer.flush();
                                if (reader.readLine().equals("false")) {
                                    //if (!directMessageMethods.sendMessage(currentUser, searchedUser, message)) {
                                    System.out.println("Error sending message.");
                                    break;
                                }
                            }
                            break;
                        case "2": //"Delete Message" option
                            if (messages.isEmpty()) {
                                System.out.println("There are no messages to delete.");
                                break; //breaks out of switch case, re displaying direct message options
                            }
                            System.out.println("Enter the number next to the message you'd like to delete " +
                                    "or enter 'back' to return:");
                            String deleteIndexStr = scan.nextLine().trim();
                            if ("back".equalsIgnoreCase(deleteIndexStr)) {
                                return;
                            }

                            int deleteIndex;
                            try {
                                deleteIndex = Integer.parseInt(deleteIndexStr) - 1;
                                if (deleteIndex < 0 || deleteIndex >= messages.size()) {
                                    System.out.println("Invalid message number. Please try again.");
                                    break; //breaks out of switch case, re displaying direct message options
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Invalid input. Please enter a number.");
                                break; //breaks out of switch case, re displaying direct message options
                            }

                            String messageToDelete = messages.get(deleteIndex);
                            //this might be a problem if user writes the searched users name in their message
                            if (messageToDelete.contains(searchedUser.getUsername() + ":")) {
                                System.out.println("Cannot delete message that is not your own.");
                                break; //breaks out of switch case, re displaying direct message options
                            }
                            messages.remove(deleteIndex);
                            //System.out.println("deleted " + messageToDelete);
                            //messages.remove(messages.get(deleteIndex));

                            writer.write("WRITE_MESSAGES");
                            writer.println();
                            writer.write(currentUser.getUsername());
                            writer.println();
                            writer.write(searchedUser.getUsername());
                            writer.println();
                            //writer.write(deleteIndex);
                            //writer.println();
                            writer.flush();
                            String first = reader.readLine();
                            String second = reader.readLine();
                            //System.out.println("Second " + second);
                            if (second.equals("true")) {
                                //if (directMessageMethods.writeMessages(currentUser, searchedUser, messages)) {
                                System.out.println("Message deleted successfully");
                            } else {
                                System.out.println("Could not delete message");
                            }
                            break; //breaks out of switch case, re displaying direct message options
                        case "3":
                            System.out.println("Returning...");
                            break; //breaks out of switch case, re displaying direct message options
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break; //breaks out of switch case, re displaying direct message options
                    }
                    //break;
                } while (!choice.equals("3"));
                writer.write("exit");
                writer.println();
                writer.flush();
            }
        } catch(IOException e) {
            System.out.println("IO Error Occurred");
        }
    } //directMessageMenu
} // End class
