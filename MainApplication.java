import java.util.*;

public class MainApplication implements MainInterface {
    private static LoginMethods loginMethods = new LoginMethods();
    private static searchMethods searchMethods = new searchMethods();
    private static directMessageMethods directMessageMethods = new directMessageMethods();


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); // Scanner
        while (true) {
            showLoginMenu(scan);
        } // Repeatedly show the login menu until the application is exited
    } //main()

    private static void showLoginMenu(Scanner scan) {
        // Presenting Login Menu Options
        System.out.println("\nLogin Menu");
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("Enter choice, or type 'exit' to close application:");

        String choice = scan.nextLine().trim(); // Getting user input, 'choice'

        if ("exit".equalsIgnoreCase(choice)) {
            System.out.println("Exiting application...");
            System.exit(0); // Exit the application
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
    } //showLoginMenu()

    static void loginProcess(Scanner scan) {
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

        if (loginMethods.validateLogin(username, password)) {
            User currentUser = new User(username); //Calls User(username) to read the user's file and create currentUser
            showMainMenu(currentUser, scan); // Transition to main menu after successful login
        }

    } //loginProcess()

    static void accountCreationProcess(Scanner scan) {
        System.out.println("Enter your desired username or enter 'back' to return to the Login Menu:");
        String username = scan.nextLine().trim();

        if ("back".equalsIgnoreCase(username)) {
            return; // Return to showLoginMenu
        }

        if (!(loginMethods.checkUsername(username))) {
            return; //if login methods returns false (the username already exits), program returns to showLoginMenu
            // method writes error to terminal.
        }

        System.out.println("Enter your desired password or enter 'back' to return to the Login Menu:");
        String password = scan.nextLine().trim();

        if ("back".equalsIgnoreCase(password)) {
            return; // Return to showLoginMenu
        }

        if (loginMethods.createAccount(username, password)) { //if the account is created successfully
            System.out.println("Account Created Successfully. Sign in using 'Login'");
            new User(username, password);
        } else {
            System.out.println("There was an error creating your account. Please try again.");
        }

    } //accountCreationProcess

    static void showMainMenu(User currentUser, Scanner scan) {
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
                    currentUser.writeToFile();
                    break; // breaks out of switch, re-displays menu options
                default: // if user inputs anything but the 3 choices
                    System.out.println("Invalid choice. Please try again.");
                    break; // breaks out of switch, re-displays menu options
            } // end switch

        } while (!choice.equals("3")); // This loop will continue until the user chooses "logout"
    } //showMainMenu

    public static void showAccountSettings(User currentUser, Scanner scan) {
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

    public static void changePasswordProcess(User currentUser, Scanner scan) {
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
        if (currentUser.writeToFile()) {
            System.out.println("Password changed successfully.\nReturning...");
        } else {
            // if there's an error writing to the user's file, change the password back to the old password in case
            // the user's file gets written again and IS successful.
            System.out.println("There was an error changing your password.\nTry again.");
            currentUser.setPassword(oldPassword);
        }

    } //changePasswordProcess()

    public static void changeDirectMessageSetting(User currentUser, Scanner scan) {
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
                    currentUser.setOpenMessaging(true);
                    if (!currentUser.writeToFile()) {
                        currentUser.setOpenMessaging(oldSetting);
                        System.out.println("An error occurred. Could not update your settings.");
                        break; // breaks out of switch, re displays the direct message options
                    } else {
                        System.out.println("Successfully updated your direct messaging to open to all users.");
                    }
                    return; //returns back to showAccountSettings
                case "2":
                    currentUser.setOpenMessaging(false);
                    if (!currentUser.writeToFile()) {
                        currentUser.setOpenMessaging(oldSetting);
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
    } //directMessageSettings

    public static void searchProcess(User currentUser, Scanner scan) {
        System.out.println("Search user or enter 'back' to return to main menu");
        String search = scan.nextLine().trim();

        if (search.equals("back")) {
            System.out.println("Returning...");
        } else { // if user enters anything but 'back'
            ArrayList<String> results = searchMethods.searchUsers(search);
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
                        searchedUser.displayProfile();
                        userViewerMenu(currentUser, searchedUser, scan);
                        break; //breaks out of while loop after user is done with userViewerMenu
                    } else {
                        // user picks number not in the list
                        System.out.println("Error: Selection out of range. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                }
            } //end while
        }
    } //searchProcess()

    public static void userViewerMenu(User currentUser, User searchedUser, Scanner scan) {
        String choice;
        do {
            // Showing user viewer options
            System.out.println("1. Add/Remove User as friend.");
            System.out.println("2. Block/Unblock User");
            System.out.println("3. Direct Message");
            System.out.println("4. Exit");
            System.out.println("Enter choice:");

            choice = scan.nextLine().trim();

            switch (choice) {
                case "1": // "Add/Remove" option.
                    ArrayList<String> currentUserBlocked = currentUser.getBlocked();
                    if (currentUserBlocked == null) { //currentUser doesnt have anyone blocked,
                        currentUserBlocked = new ArrayList<>(); // create an empty array to avoid null pointer
                    }

                    if (currentUserBlocked.contains(searchedUser.getUsername())) {
                        System.out.println("Cannot add " + searchedUser.getUsername() + " since they are blocked.");
                        break; //breaks out of switch block, re displays view user options
                    }

                    ArrayList<String> searchedUserBlocked = searchedUser.getBlocked();
                    if (searchedUserBlocked == null) { // searchedUser doesn't have anyone blocked,
                        searchedUserBlocked = new ArrayList<>(); //create an empty array to avoid null pointer
                    }

                    if (searchedUserBlocked.contains(currentUser.getUsername())) {
                        System.out.println("Cannot add " + searchedUser.getUsername() + " as a friend because you are blocked.");
                        break; //breaks out of switch block, re displays view user options
                    }

                    ArrayList<String> searchedFriends = searchedUser.getFriends();
                    if (searchedFriends == null) { //if searchedUser doesnt have friends
                        searchedFriends = new ArrayList<>(); //create empty array to avoid null pointer
                    }

                    ArrayList<String> currentFriends = currentUser.getFriends();
                    if (currentFriends == null) { //if currentUser doesnt have friends
                        currentFriends = new ArrayList<>(); //create empty array to avoid null pointer
                    }

                    if (currentFriends.contains(searchedUser.getUsername())) { // if they are friends
                        currentFriends.remove(searchedUser.getUsername());//remove searchedUser frm currentUser friends
                        if (!currentUser.writeToFile()) { //write to user, if fails, add searchedUser back
                            currentFriends.add(searchedUser.getUsername());
                            System.out.println("Action not completed");
                        } else {
                            System.out.println(searchedUser.getUsername() + " removed as a friend!");
                        }
                        break; //breaks out of switch block, re displays view user options
                    } else { // if they arent friends
                        currentFriends.add(searchedUser.getUsername()); // add searchedUser to currentUser friends
                        if (!currentUser.writeToFile()) { //write to user, if fails, remove searchedUser
                            currentFriends.remove(searchedUser.getUsername());
                            System.out.println("Action not completed");
                        } else {
                            System.out.println(searchedUser.getUsername() + " added as a friend!");
                        }
                        break; //breaks out of switch block, re displays view user options
                    }
                case "2": //"Block/Unblock" option
                    ArrayList<String> blocked = currentUser.getBlocked();
                    if (blocked == null) { // checking if blocked is null to avoid null pointer exception
                        blocked = new ArrayList<>();
                    }

                    ArrayList<String> friends = currentUser.getFriends();
                    if (friends == null) { // checking if blocked is null to avoid null pointer exception
                        friends = new ArrayList<>();
                    }

                    if (blocked.contains(searchedUser.getUsername())) { // already blocked
                        blocked.remove(searchedUser.getUsername()); // removed from blocked list
                        if (!currentUser.writeToFile()) { //write to currentUser file, if fails,
                            blocked.add(searchedUser.getUsername()); //add searchedUser back to currentUser blocked
                            System.out.println("Action not completed");
                            break; //breaks out of switch block, re displays view user options
                        }
                        System.out.println("Successfully unblocked " + searchedUser.getUsername());
                    } else {
                        blocked.add(searchedUser.getUsername()); // if not on list, block them
                        boolean removed = friends.remove(searchedUser.getUsername());
                        if (!currentUser.writeToFile()) { //write to currentUser file,
                            blocked.remove(searchedUser.getUsername()); // if fails, remove
                            if (removed) { // if searchedUser was removed from currentUser friends
                                friends.add(searchedUser.getUsername()); //add searchedUser back to currentUser friends
                            }
                            System.out.println("Action not completed");
                            break; //breaks out of switch block, re displays view user options
                        } else { // if writeToFile of current user didn't fail, update searchedUser friends
                            ArrayList<String> searchedFriends1 = searchedUser.getFriends(); // remove from friends list
                            if (searchedFriends1 != null && searchedFriends1.contains(currentUser.getUsername())) {
                                searchedFriends1.remove(currentUser.getUsername());
                                if (!searchedUser.writeToFile()) {
                                    searchedFriends1.add(currentUser.getUsername());
                                    System.out.println("Action not completed");
                                }
                            }
                            System.out.println(searchedUser.getUsername() + " successfully blocked.");
                            break; //breaks out of switch block, re displays view user options
                        }
                    }
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

    } //userViewerMenu

    public static void directMessageMenu(User currentUser, User searchedUser, Scanner scan) {
        ArrayList<String> currentUserBlocked = currentUser.getBlocked();
        if (currentUserBlocked == null) { //checking for null and re-assigning to avoid null pointers.
            currentUserBlocked = new ArrayList<>();
        }
        ArrayList<String> searchedUserBlocked = currentUser.getBlocked();
        if (searchedUserBlocked == null) { //checking for null and re-assigning to avoid null pointers.
            searchedUserBlocked = new ArrayList<>();
        }

        ArrayList<String> currentFriends = currentUser.getFriends();
        if (currentFriends == null) { //checking for null and re-assigning to avoid null pointers.
            currentFriends = new ArrayList<>();
        }
        ArrayList<String> searchedFriends = currentUser.getBlocked();
        if (searchedFriends == null) { //checking for null and re-assigning to avoid null pointers.
            searchedFriends = new ArrayList<>();
        }

        if (currentUserBlocked.contains(searchedUser.getUsername())) {
            // if currentUser has the searchedUser blocked.
            System.out.println("You cannot message this user. You have the recipient blocked.");
            return; //returns to userViewerMenu
        }
        if (searchedUserBlocked.contains(currentUser.getUsername())) {
            //if the searchedUser has the currentUser blocked
            System.out.println("You cannot message this user. The recipient has you blocked.");
            return; //returns to userViewerMenu
        }

        if (!searchedUser.isOpenMessaging() && (!currentFriends.contains(searchedUser.getUsername()) ||
                !searchedFriends.contains(currentUser.getUsername()))) {
            // if the searchedUser has open messaging off, and neither of them are on each other's friends list
            System.out.println("You cannot message this user. You are not mutually friends." +
                    " The recipient has open messaging disabled.");
            if (searchedFriends.contains(currentUser.getUsername())) {
                // if the searched user has the currentUser on their friends list
                System.out.println("The recipient has sent you a friend request.\nExit to the main menu to search " +
                        "and add " + searchedUser.getUsername() + " back.");
            }
            return; //returns to userViewerMenu
        }


        if (!currentUser.isOpenMessaging() && (!currentUser.getFriends().contains(searchedUser.getUsername()) ||
                !searchedUser.getFriends().contains(currentUser.getUsername()))) {
            // if the current has open messaging off, and neither of them are on each other's friends list
            System.out.println("You cannot message this user. You are not friends. You have open messaging disabled.");
            if (searchedUser.getFriends().contains(currentUser.getUsername())) {
                // iff the searched user has the currentUser on their friends list
                System.out.println("The recipient has sent you a friend request.\nExit to the main menu to search " +
                        "and add " + searchedUser.getUsername() + " back.");
            }
            return;
        }

        // if they are allowed to direct message! completes actions below.
        String choice;
        do {
            if (!directMessageMethods.openMessages(currentUser, searchedUser)) {
                System.out.println("Error creating message file.");
                break; //
            }
            List<String> messages = directMessageMethods.readMessages(currentUser, searchedUser);
            if (!directMessageMethods.displayMessages(messages)) {
                System.out.println("No messages yet.");
            }
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
                        if (!directMessageMethods.sendMessage(currentUser, searchedUser, message)) {
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
                    System.out.println("Enter the number next to the message you'd like to delete or enter 'back' to " +
                            "return:");
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
                    if (messageToDelete.contains(searchedUser.getUsername())) {
                        System.out.println("Cannot delete message that is not your own.");
                        break; //breaks out of switch case, re displaying direct message options
                    }
                    messages.remove(deleteIndex);
                    if (directMessageMethods.writeMessages(currentUser, searchedUser, messages)) {
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

        } while (!choice.equals("3"));
    } //directMessageMenu

} // End class
