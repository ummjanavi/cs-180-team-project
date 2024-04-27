import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MainApplication.java
 * <p>
 * This class runs the app, Friendify. This is the main application.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * <p>
 * Lecture 1, Lab 10
 * @version 4/27/2024
 */
public class MainApplication extends Thread implements MainInterface {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public MainApplication(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred during login: " + ex.getMessage());
        }
    } // constructor

    public static void main(String[] args) {
        // Connect to the server and create reader/writer as before
        // Create MainApplication instance
        MainApplication client = null;
        try {
            client = new MainApplication(new Socket("localhost", 4545));
            client.start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error occurred during login: " + ex.getMessage());
        }
        // Start the thread
    }


    @Override
    public void run() {
        try (Scanner scan = new Scanner(System.in)) {
            showLoginMenu(scan);
        }
    } //main()

    private void showLoginMenu(Scanner scan) {
        JFrame frame = new JFrame("Welcome to Friendify");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(216,220,255));
        JLabel welcomeLabel = new JLabel("Please login to an existing account or create a new one");
        panel.add(welcomeLabel);
        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");
        JButton exitButton = new JButton("Exit");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.dispose();
                    loginProcess(scan);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                accountCreationProcess(scan);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        panel.add(loginButton);
        panel.add(createAccountButton);
        panel.add(exitButton);
        frame.add(panel);
        frame.setSize(400, 130);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } //showLoginMenu()

    private void loginProcess(Scanner scan) throws IOException {
        JFrame frame = new JFrame("Login");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(216,220,255));
        JLabel welcomeLabel = new JLabel("Enter Username");
        panel.add(welcomeLabel);
        JTextField usernameField = new JTextField(30);
        panel.add(usernameField);
        JLabel passwordLabel = new JLabel("Enter Password");
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(30);
        panel.add(passwordField);

        JButton enterButton = new JButton("Login");
        panel.add(enterButton);
        JButton backButton = new JButton("Back");
        panel.add(backButton);

        JLabel welcome = new JLabel("Please enter username and password above.\n");
        panel.add(welcome);

        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                try {
                    writer.write("VALIDATE_LOGIN");
                    writer.println();
                    writer.write(username);
                    writer.println();
                    writer.write(password);
                    writer.println();
                    writer.flush();

                    if ((reader.readLine()).equals("true")) {
                        JOptionPane.showMessageDialog(frame, "Login Successful!");
                        User currentUser = new User(username);
                        //Calls User(username) to read the user's file and create currentUser
                        frame.dispose();
                        showMainMenu(currentUser, scan); // Transition to main menu after successful login
                        // Close the login window after successful login
                    } else {
                        JOptionPane.showMessageDialog(frame, "Login failed. Incorrect username or password.");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error occurred during login: " + ex.getMessage());
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showLoginMenu(scan);
                // Close the login window when returning to the login menu
            }
        });

        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } //loginProcess()

    private void accountCreationProcess(Scanner scan) {
        JFrame frame = new JFrame("Create Account");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(216,220,255));
        JLabel welcomeLabel = new JLabel("Create Username");
        panel.add(welcomeLabel);
        JTextField usernameField = new JTextField(20);
        panel.add(usernameField);
        JLabel passwordLabel = new JLabel("Create Password");
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField);

        JButton enterButton = new JButton("Create Account");
        panel.add(enterButton);
        JButton backButton = new JButton("Back");
        panel.add(backButton);

        JLabel welcome = new JLabel("Please enter username and password above.\n");
        panel.add(welcome);


        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                try {
                    writer.write("CHECK_USERNAME");
                    writer.println();
                    writer.write(username);
                    writer.println();
                    writer.flush();
                    // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
                    if ((reader.readLine()).equals("false")) {
                        JOptionPane.showMessageDialog(frame,
                                "Username taken! Please try again with another.");
                        //if login methods returns false (the username already exits), program returns to showLoginMenu
                        // method writes error to terminal.
                    } else {
                        writer.write("CREATE_ACCOUNT");
                        writer.println();
                        writer.write(username);
                        writer.println();
                        writer.write(password);
                        writer.println();
                        writer.flush();
                        // THIS PROCESS NEEDS SERVER CLIENT INTERACTION
                        if ((reader.readLine()).equals("true")) { //if the account is created successfully
                            frame.dispose();
                            JOptionPane.showMessageDialog(frame, "Account Created Successfully. " +
                                    "Sign in using 'Login'.");
                            new User(username, password);
                            showLoginMenu(scan);
                        } else {
                            JOptionPane.showMessageDialog(frame, "There was an error creating your account." +
                                    " Please try again.");

                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error occurred during account creation: "
                            + ex.getMessage());
                }
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose(); // Close the login window when returning to the login menu
            showLoginMenu(scan);
        });


        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } //accountCreationProcess

    private void showMainMenu(User currentUser, Scanner scan) {
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(216,220,255));
        JLabel messageLabel = new JLabel("           Please select an option:               ");
        JButton searchButton = new JButton("Search for a user");
        JButton accountSettingsButton = new JButton("Account settings");
        JButton logoutButton = new JButton("Logout");

        panel.add(messageLabel);
        panel.add(searchButton);
        panel.add(accountSettingsButton);
        panel.add(logoutButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchProcess(currentUser, scan);
                frame.dispose();
            }
        });

        accountSettingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAccountSettings(currentUser, scan);
                frame.dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the main menu window
                showLoginMenu(scan);
            }
        });

        frame.add(panel);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } //showMainMenu

    private void showAccountSettings(User currentUser, Scanner scan) {
        JFrame frame = new JFrame("Account Settings");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(216,220,255));
        JLabel messageLabel = new JLabel("           Please select an option:          ");
        JButton changePassword = new JButton("Change account password");
        JButton dMPrivacy = new JButton("Change direct messaging privacy");
        JButton retToMain = new JButton("Return to main menu");

        panel.add(messageLabel);
        panel.add(changePassword);
        panel.add(dMPrivacy);
        panel.add(retToMain);

        changePassword.addActionListener(e -> {
            changePasswordProcess(currentUser, scan);
            frame.dispose();
        });
        dMPrivacy.addActionListener(e -> {
            changeDirectMessageSetting(currentUser, scan);
            frame.dispose();
        });
        retToMain.addActionListener(e -> {
            showMainMenu(currentUser, scan);
            frame.dispose(); // Close the settings menu window
        });

        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } //showAccountSettings

    private void changePasswordProcess(User currentUser, Scanner scan) {
        JFrame frame = new JFrame("Change Password");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(216,220,255));
        JLabel welcome = new JLabel("To change your password, enter your old password.\n");
        panel.add(welcome);
        JLabel welcomeLabel = new JLabel("Old Password");
        panel.add(welcomeLabel);
        JTextField usernameField = new JTextField(30);
        panel.add(usernameField);
        JLabel passwordLabel = new JLabel("New Password");
        panel.add(passwordLabel);
        JTextField updatedPassword = new JPasswordField(30);
        panel.add(updatedPassword);

        JButton enterButton = new JButton("Enter");
        panel.add(enterButton);
        JButton cancelButton = new JButton("Cancel");
        panel.add(cancelButton);

        // if cancel button pressed
        cancelButton.addActionListener(e -> {
            frame.dispose();
            showAccountSettings(currentUser, scan);
        });
        enterButton.addActionListener(e -> {
            String oldPass = usernameField.getText().trim();
            String password = updatedPassword.getText().trim();

            if (!(oldPass.equals(currentUser.getPassword()))) {
                JOptionPane.showMessageDialog(frame, "Incorrect old password");
            } else {
                try {
                    currentUser.setPassword(password);
                    writer.write("CHANGE_PASSWORD");
                    writer.println();
                    writer.write(currentUser.getUsername());
                    writer.println();
                    writer.write(password);
                    writer.println();
                    writer.flush();

                    if ((reader.readLine()).equals("true")) {
                        JOptionPane.showMessageDialog(frame, "Password change Successful!");
                        frame.dispose();
                        showAccountSettings(currentUser, scan);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Password change failed.");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error occurred during change: " + ex.getMessage());
                }
            }
        });
        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } //changePasswordProcess()

    private void changeDirectMessageSetting(User currentUser, Scanner scan) {
        try {
            JFrame frame = new JFrame("Change DM settings");
            JPanel panel = new JPanel();
            panel.setBackground(new Color(216,220,255));
            JLabel welcome = new JLabel("            Direct Messaging Privacy Choices:                ");
            panel.add(welcome);
            JButton toPublic = new JButton("Open to everyone");
            panel.add(toPublic);
            JButton toPrivate = new JButton("Open to friends only");
            panel.add(toPrivate);
            JButton cancelButton = new JButton("Cancel");
            panel.add(cancelButton);
            boolean oldSetting = currentUser.isOpenMessaging();
            toPublic.addActionListener(new ActionListener() { // if cancel button pressed
                public void actionPerformed(ActionEvent e) {
                    try {
                        writer.write("SET_OPEN_MESSAGING_TRUE");
                        writer.println();
                        writer.write(currentUser.getUsername());
                        writer.println();
                        writer.flush();
                        if (reader.readLine().equals("false")) {
                            //currentUser.setOpenMessaging(oldSetting);
                            writer.write("SET_OPEN_MESSAGING");
                            writer.println();
                            writer.write(currentUser.getUsername());
                            writer.println();
                            writer.write(String.valueOf(oldSetting));
                            writer.println();
                            writer.flush();
                            JOptionPane.showMessageDialog(frame, "Setting update failed for some reason");
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Successfully updated to all users.\n");
                            frame.dispose();
                            showAccountSettings(currentUser, scan);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame,
                                "Error occurred during change: " + ex.getMessage());
                    }
                }
            });
            toPrivate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
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
                            JOptionPane.showMessageDialog(frame, "Setting update failed for some reason");
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Successfully updated to friends only.\n");
                            frame.dispose();
                            showAccountSettings(currentUser, scan);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame,
                                "Error occurred during change: " + ex.getMessage());
                    }
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    showAccountSettings(currentUser, scan);
                }
            });
            frame.add(panel);
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error occurred during change: " + e.getMessage());
        }
    } //directMessageSettings


    private void searchProcess(User currentUser, Scanner scan) {
        // Setup JFrame for GUI
        JFrame frame = new JFrame("Friendify - Search Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        // Header panel with title
        JLabel headerLabel = new JLabel("FRIENDIFY", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(216,220,255));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 125, 10));

        // Dropdown to display results
        JComboBox<String> resultComboBox = new JComboBox<>();
        resultComboBox.setEditable(false);
        resultComboBox.setPreferredSize(new Dimension(250, 25));

        // Input area for searching
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(new Color(216,220,255));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(resultComboBox, BorderLayout.CENTER);

        JButton selectButton = new JButton("Select");
        JButton backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(216,220,255));
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
                        JOptionPane.showMessageDialog(frame, "Try Again. No Matched Users",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int numResults = Integer.parseInt(results);
                        for (int i = 0; i < numResults; i++) {
                            String user = reader.readLine();
                            resultComboBox.addItem(user);
                        }
                    }

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error communicating with server",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(e -> {
            showMainMenu(currentUser, scan);
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
        headerPanel.setBackground(new Color(216,220,255));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Profile panel with username and picture
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(new Color(216,220,255));
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
        buttonPanel.setBackground(new Color(216,220,255));
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
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(frame, status); // Show the status message in a dialog box
        });

        directMessageButton.addActionListener(e -> {
            // Direct message logic here
            writer.write("CHECK_DIRECT\n"); //sends command to check if they can direct message
            writer.write(currentUser.getUsername() + "\n");
            writer.write(searchedUser.getUsername() + "\n");
            writer.flush();

            String status = null;
            try {
                status = reader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (!status.equals("GOOD_TO_MESSAGE")) {
                JOptionPane.showMessageDialog(frame, status, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                directMessageMenu(currentUser, searchedUser, scan);
                frame.dispose();
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            searchProcess(currentUser, scan);
        });
    } //userViewerMenu

    private void directMessageMenu(User currentUser, User searchedUser, Scanner scan) {
        try {
            // if they are allowed to direct message! completes actions below.
            writer.write("OPEN_MESSAGES");
            writer.println();
            writer.write(currentUser.getUsername());
            writer.println();
            writer.write(searchedUser.getUsername());
            writer.println();
            writer.flush();

            String opened = reader.readLine();
            if (opened.equals("false")) {
                JOptionPane.showMessageDialog(null,
                        "An error occurred trying to access your messages",
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
                headerPanel.setBackground(new Color(216,220,255));
                headerPanel.add(headerLabel, BorderLayout.CENTER);
                headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                frame.add(headerPanel, BorderLayout.NORTH);

                JPanel sidePanel = new JPanel();
                sidePanel.setBackground(new Color(216,220,255));
                sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
                JTextPane deleteInstructions = new JTextPane();
                deleteInstructions.setBackground(new Color(216,220,255));
                deleteInstructions.setEditable(false);
                deleteInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);  // Align centrally horizontally
                deleteInstructions.setText("Select a message, then press \nthe 'Delete Message' button.");
                sidePanel.add(deleteInstructions);
                JButton backButton = new JButton("Back");
                JButton deleteMessageButton = new JButton("Delete Message");
                deleteMessageButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Align centrally horizontally
                sidePanel.add(deleteMessageButton);
                sidePanel.add(backButton);
                frame.add(sidePanel, BorderLayout.EAST);

                DefaultListModel<String> listModel = new DefaultListModel<>();
                JList<String> messageList = new JList<>(listModel);
                JScrollPane scrollPane = new JScrollPane(messageList);
                frame.add(scrollPane, BorderLayout.CENTER);

                JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                inputPanel.setBackground(new Color(216,220,255));
                JButton sendMessageButton = new JButton("Send Message");
                JTextField messageField = new JTextField(50);
                messageField.setBackground(new Color(216,220,255));
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
                    int selectedIndex = messageList.getSelectedIndex();
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

                backButton.addActionListener(e -> {
                    userViewerMenu(currentUser, searchedUser, scan);
                    frame.dispose();
                });

            } //end inner else
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error communicating with server.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    } //directMessageMenu

} // End class
