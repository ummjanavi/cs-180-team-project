import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


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
    //private static LoginMethods loginMethods = new LoginMethods();
    //private static SearchMethods searchMethods = new SearchMethods();
    //private static DirectMessageMethods directMessageMethods = new DirectMessageMethods();
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
            // NEED SOMETHING IN HERE
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
            // NEED SOMETHING IN HERE
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
            // NEED SOMETHING IN HERE
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
            // NEED SOMETHING IN HERE
        }
    } //directMessageSettings
    private void searchProcess(User currentUser, Scanner scan) {
        // Setup JFrame for GUI
        JFrame frame = new JFrame("Friendify - Search Users");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        // Header panel with title
        JLabel headerLabel = new JLabel("FRIENDIFY", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 125, 10));

        // Dropdown to display results
        JComboBox<String> resultComboBox = new JComboBox<>();
        resultComboBox.setEditable(false);
        resultComboBox.setPreferredSize(new Dimension(250, 25));

        // Input area for searching
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(resultComboBox, BorderLayout.CENTER);

        JButton selectButton = new JButton("Select");
        JButton backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);

        // Add panels to frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Show the frame
        frame.setLocationRelativeTo(null); // CENTERS THE WINDOW
        frame.setResizable(false); // USER CANNOT RESIZE THE WINDOW
        frame.setVisible(true);

        // Action Listeners
        searchButton.addActionListener(e -> {
            String search = searchField.getText().trim();
            resultComboBox.removeAllItems();
            if (!search.isEmpty()) {
                try {
                    writer.write("SEARCH_METHODS\n");
                    writer.write(search + "\n");
                    writer.write(currentUser.getUsername() + "\n");
                    writer.flush();

                    String results = reader.readLine().trim();
                    if (results.equals("No results")) {
                        //resultComboBox.addItem("No matched users");
                        JOptionPane.showMessageDialog(frame, "Try Again. No Matched Users", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int numResults = Integer.parseInt(results);
                        for (int i = 0; i < numResults; i++) {
                            String user = reader.readLine();
                            resultComboBox.addItem(user);
                        }
                    }

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error communicating with server", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(e -> {
            //showMainMenu(currentUser, scan); // NEEDS TO RE OPEN MAIN MENU
            frame.dispose();
        });

        selectButton.addActionListener(e -> {
            String selectedUser = (String) resultComboBox.getSelectedItem();
            if (selectedUser != null && !selectedUser.equals("No matched users")) {
                frame.dispose(); // Close frame after selection
                userViewerMenu(currentUser, new User(selectedUser), scan);
            }
        });
    } // searchProcess()

    private void userViewerMenu(User currentUser, User searchedUser, Scanner scan) {
        JFrame frame = new JFrame("Friendify - View User");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        // Header panel with title
        JLabel headerLabel = new JLabel("FRIENDIFY", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Profile panel with username and picture
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.PAGE_AXIS));

        // Label for the username
        JLabel usernameLabel = new JLabel(searchedUser.getUsername(), SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Profile picture
        ImageIcon profilePic = new ImageIcon("default.jpg");  // Replace with the actual image or ImageIcon instance
        JLabel profilePicLabel = new JLabel(profilePic);
        profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the profile panel
        profilePanel.add(Box.createVerticalGlue());
        profilePanel.add(usernameLabel);
        profilePanel.add(profilePicLabel);
        profilePanel.add(Box.createVerticalGlue());

        // Buttons for user interactions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addFriendButton = new JButton("Add/Remove Friend");
        JButton blockUserButton = new JButton("Block/Unblock");
        JButton directMessageButton = new JButton("Direct Message");
        JButton backButton = new JButton("Back");

        // Adding buttons to the panel
        buttonPanel.add(addFriendButton);
        buttonPanel.add(blockUserButton);
        buttonPanel.add(directMessageButton);
        buttonPanel.add(backButton);

        // Adding components to the main frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(profilePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Show the frame
        frame.setLocationRelativeTo(null); // CENTERS THE WINDOW
        frame.setResizable(false); // USER CANNOT RESIZE THE WINDOW
        frame.setVisible(true);
        // Add action listeners to buttons
        addFriendButton.addActionListener(e -> {
            // Add/remove friend logic here
            writer.write("ADD_FRIEND");
            writer.println();
            writer.write(currentUser.getUsername());
            writer.println();
            writer.write(searchedUser.getUsername());
            writer.println();
            writer.flush();

            String status = null;
            try {
                status = reader.readLine();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(frame, status); // Show the status message in a dialog box
        });

        blockUserButton.addActionListener(e -> {
            // Block/unblock user logic here
            writer.write("BLOCK_USER");
            writer.println();
            writer.write(currentUser.getUsername());
            writer.println();
            writer.write(searchedUser.getUsername());
            writer.println();
            writer.flush();

            String status = null;
            try {
                status = reader.readLine();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(frame, status); // Show the status message in a dialog box
        });

        directMessageButton.addActionListener(e -> {
            // Direct message logic here
            directMessageMenu(currentUser, searchedUser, scan);
        });

        backButton.addActionListener(e -> {
            searchProcess(currentUser, scan);
            frame.dispose();
        });
    } //userViewerMenu

    private void directMessageMenu(User currentUser, User searchedUser, Scanner scan) {
            try {
                writer.write("CHECK_DIRECT\n"); //sends command to check if they can direct message
                writer.write(currentUser.getUsername() + "\n");
                writer.write(searchedUser.getUsername() + "\n");
                writer.flush();

                String status = reader.readLine();

                if (!status.equals("GOOD_TO_MESSAGE")) {
                    JOptionPane.showMessageDialog(null, status, "Error", JOptionPane.ERROR_MESSAGE);
                } else { // if they are allowed to direct message! completes actions below.

                    writer.write("OPEN_MESSAGES");
                    writer.println();
                    writer.write(currentUser.getUsername());
                    writer.println();
                    writer.write(searchedUser.getUsername());
                    writer.println();
                    writer.flush();

                    String opened = reader.readLine();
                    if (opened.equals("false")) {
                        JOptionPane.showMessageDialog(null, "An error occurred trying to access your messages",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else { // if no error occurred while opening messages
                        JFrame frame = new JFrame("Friendify - Direct Message");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setSize(700, 500);
                        frame.setLayout(new BorderLayout());

                        // Header panel with title
                        JLabel headerLabel = new JLabel("FRIENDIFY\n", SwingConstants.CENTER);
                        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
                        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                        headerPanel.add(headerLabel, BorderLayout.CENTER);
                        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        frame.add(headerPanel, BorderLayout.NORTH);

                        JPanel sidePanel = new JPanel();
                        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
                        JTextPane deleteInstructions = new JTextPane();
                        deleteInstructions.setEditable(false);
                        deleteInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);  // Align centrally horizontally
                        deleteInstructions.setText("Select a message,then press \nthe 'Delete Message' button.");
                        sidePanel.add(deleteInstructions);
                        JButton deleteMessageButton = new JButton("Delete Message");
                        deleteMessageButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Align centrally horizontally
                        sidePanel.add(deleteMessageButton);
                        frame.add(sidePanel, BorderLayout.EAST);

                        DefaultListModel<String> listModel = new DefaultListModel<>();
                        JList<String> messageList = new JList<>(listModel);
                        JScrollPane scrollPane = new JScrollPane(messageList);
                        frame.add(scrollPane, BorderLayout.CENTER);

                        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                        JButton sendMessageButton = new JButton("Send Message");
                        JTextField messageField = new JTextField(50);
                        inputPanel.add(messageField);
                        inputPanel.add(sendMessageButton);
                        frame.add(inputPanel, BorderLayout.SOUTH);

                        frame.setLocationRelativeTo(null); // CENTERS THE WINDOW
                        frame.setResizable(false); // USER CANNOT RESIZE THE WINDOW
                        frame.setVisible(true);

                        writer.write("READ_AND_DISPLAY_MESSAGES");
                        writer.println();
                        writer.write(currentUser.getUsername());
                        writer.println();
                        writer.write(searchedUser.getUsername());
                        writer.println();
                        writer.flush();

                        String results = reader.readLine().trim();
                        ArrayList<String> messages = new ArrayList<>();
                        if (results.equals("No Messages")) {
                            messages.add("No messages to display");
                        } else {
                            int numResults = Integer.parseInt(results);
                            for (int i = 0; i < numResults; i++) {
                                String message = reader.readLine();
                                messages.add(message);
                            }
                        }
                        messages.forEach(listModel::addElement);

                        sendMessageButton.addActionListener(e -> {
                            String message = messageField.getText().trim();
                            if (message.isEmpty()) {
                                JOptionPane.showMessageDialog(frame, "Message cannot be empty. Try again.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                writer.write("SEND_MESSAGE");
                                writer.println();
                                writer.write(currentUser.getUsername());
                                writer.println();
                                writer.write(searchedUser.getUsername());
                                writer.println();
                                writer.write(message);
                                writer.println();
                                writer.flush();
                                try {
                                    if (reader.readLine().equals("false")) {
                                        JOptionPane.showMessageDialog(frame, "Error sending message. Try again.",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        listModel.clear(); //reset messageDisplay
                                        writer.write("READ_AND_DISPLAY_MESSAGES"); // fetch messages again
                                        writer.println();
                                        writer.write(currentUser.getUsername());
                                        writer.println();
                                        writer.write(searchedUser.getUsername());
                                        writer.println();
                                        writer.flush();

                                        String r = reader.readLine().trim();
                                        ArrayList<String> m = new ArrayList<>();
                                        if (r.equals("No Messages")) {
                                            m.add("No messages to display");
                                        } else {
                                            int numResults = Integer.parseInt(r);
                                            for (int i = 0; i < numResults; i++) {
                                                String ms = reader.readLine();
                                                m.add(ms);
                                            }
                                        }
                                        m.forEach(listModel::addElement);
                                    }
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(frame, "Error communicating with server.",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                messageField.setText("");
                            }
                        });

                        deleteMessageButton.addActionListener(e -> {
                            int selectedIndex  = messageList.getSelectedIndex();
                            if (selectedIndex != -1) {
                                if (!listModel.getElementAt(selectedIndex).startsWith(currentUser.getUsername())) {
                                    JOptionPane.showMessageDialog(frame, "You can only delete your own messages",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    listModel.remove(selectedIndex);
                                    writer.write("WRITE_MESSAGES");
                                    writer.println();
                                    writer.write(currentUser.getUsername() + "\n");
                                    writer.write(searchedUser.getUsername() + "\n");
                                    writer.write(listModel.getSize() + "\n");
                                    writer.flush();
                                    for (int i = 0; i < listModel.getSize(); i++) {
                                        writer.write(listModel.getElementAt(i));
                                        writer.println();
                                        writer.flush();
                                    }
                                    try {
                                        if (reader.readLine().equals("false")) {
                                            JOptionPane.showMessageDialog(frame, "Error Deleting Message.",
                                                    "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } catch (IOException ex) {
                                        JOptionPane.showMessageDialog(frame, "Error communicating with server.",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        });

                    } //end inner else
                }//end outer ELSE
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error communicating with server.",
                        "Error", JOptionPane.ERROR_MESSAGE);            }
    } //directMessageMenu

} // End class
