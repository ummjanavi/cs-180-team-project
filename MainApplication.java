import java.util.*;

public class MainApplication {
    private static LoginMethods loginMethods = new LoginMethods();
    private static searchMethods searchMethods = new searchMethods();
    private static directMessageMethods directMessageMethods = new directMessageMethods();


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // Repeatedly show the login menu until the application is exited
        while (true) {
            showLoginMenu(scan);
        }
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
            case "1":
                loginProcess(scan);
                break;
            case "2":
                accountCreationProcess(scan);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break; // Remains in the Login Menu, Re-displaying the options
        }
    } //showLoginMenu()

    private static void loginProcess(Scanner scan) {
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
            User currentUser = new User(username);
            showMainMenu(currentUser, scan); // Transition to main menu after successful login
        } else {
            return;
        }

    } //loginProcess()

    private static void accountCreationProcess(Scanner scan) {
        System.out.println("Enter your desired username or enter 'back' to return to the Login Menu:");
        String username = scan.nextLine().trim();

        if ("back".equalsIgnoreCase(username)) {
            return; // Return to showLoginMenu
        }

        if (!(loginMethods.checkUsername(username))) {
            return;
        }

        System.out.println("Enter your desired password or enter 'back' to return to the Login Menu:");
        String password = scan.nextLine().trim();

        if ("back".equalsIgnoreCase(password)) {
            return; // Return to showLoginMenu
        }

        if (loginMethods.createAccount(username, password)) {
            System.out.println("Account Created Successfully. Sign in using 'Login'");
        } else {
            System.out.println("There was an error creating your account. Please try again.");
        }

    } //accountCreationProcess

    private static void showMainMenu(User currentUser, Scanner scan) {
        String choice;
        do {
            System.out.println("\nMain Menu");
            System.out.println("1. Search for a user");
            System.out.println("2. Account settings");
            System.out.println("3. Logout");
            System.out.println("Enter choice:");

            choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    searchProcess(currentUser, scan);
                    break;
                case "2":
                    // Placeholder for account settings functionality
                    showAccountSettings(currentUser, scan);
                    break;
                case "3":
                    System.out.println("Logging out...");
                    currentUser.writeToFile();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!choice.equals("3")); // This loop will continue until the user chooses to logout
    } //showMainMenu

    public static void showAccountSettings(User currentUser, Scanner scan) {
        String choice;
        do {
            System.out.println("\nAccount Settings");
            System.out.println("1. Change account password");
            System.out.println("2. Change direct messaging privacy");
            System.out.println("3. Return to Main Menu");
            System.out.println("Enter choice:");

            choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    changePasswordProcess(currentUser, scan);
                    break;
                case "2":
                    changeDirectMessageSetting(currentUser, scan);
                    break;
                case "3":
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!choice.equals("3"));

    } //showAccountSettings

    public static void changePasswordProcess(User currentUser, Scanner scan) {
        System.out.println("To change your password, enter your old password.");
        System.out.println("or enter 'back' to return");
        String oldPassword = scan.nextLine().trim();

        if ("back".equalsIgnoreCase(oldPassword)) {
            return;
        }

        if (!(oldPassword.equals(currentUser.getPassword()))) {
            System.out.println("Incorrect Password. Try again");
            return;
        }
        System.out.println("Enter your new password");
        System.out.println("or enter 'back' to return");

        String newPassword = scan.nextLine().trim();
        if ("back".equalsIgnoreCase(newPassword)) {
            return;
        }

        currentUser.setPassword(newPassword);
        if (currentUser.writeToFile()) {
            System.out.println("Password changed successfully.\nReturning...");
        } else {
            System.out.println("There was an error changing your password.\nTry again.");
            currentUser.setPassword(oldPassword);
        }
    } //changePasswordProcess()

    public static void changeDirectMessageSetting(User currentUser, Scanner scan) {
        String choice;
        do {
            System.out.println("Direct Messaging Privacy Choices:");
            System.out.println("1. Open to everyone");
            System.out.println("2. Open to just your friends");
            System.out.println("3. Cancel");
            System.out.println("Enter choice:");

            choice = scan.nextLine().trim();

            boolean oldSetting = currentUser.isOpenMessaging();
            switch (choice) {
                case "1":
                    currentUser.setOpenMessaging(true);
                    if (!currentUser.writeToFile()) {
                        currentUser.setOpenMessaging(oldSetting);
                        System.out.println("An error occurred.");
                        break;
                    }
                    return;
                case "2":
                    currentUser.setOpenMessaging(false);
                    if (!currentUser.writeToFile()) {
                        currentUser.setOpenMessaging(oldSetting);
                        System.out.println("An error occurred.");
                        break;
                    }
                    return;
                case "3":
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }
        } while (!choice.equals("3"));
    } //directMessageSettings

    public static void searchProcess(User currentUser, Scanner scan) {
        System.out.println("Search user or enter 'back' to return to main menu");
        String search = scan.nextLine().trim();
        if (search.equals("back")) {
            System.out.println("Returning...");
        } else {
            ArrayList<String> results = searchMethods.searchUsers(search);
            results.remove(currentUser.getUsername());
            if (results.isEmpty()) {
                System.out.println("No matched users");
                return;
            } else {
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
                        break;
                    } else {
                        // Number not in the list
                        System.out.println("Error: Selection out of range. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                }
            }
        }
    } //searchProcess()

    public static void userViewerMenu(User currentUser, User searchedUser, Scanner scan) {
        String choice;
        do {
            System.out.println("1. Add/Remove User as friend.");
            System.out.println("2. Block/Unblock User");
            System.out.println("3. Direct Message");
            System.out.println("4. Exit");
            System.out.println("Enter choice:");

            choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    if (currentUser.getBlocked().contains(searchedUser.getUsername())) {
                        System.out.println("Cannot add " + searchedUser.getUsername() + " since they are blocked.");
                        break;
                    }
                    ArrayList<String> searchedUserBlocked = searchedUser.getBlocked();
                    if (searchedUserBlocked != null && searchedUserBlocked.contains(currentUser.getUsername())) {
                        System.out.println("Cannot add " + searchedUser.getUsername() + " as a friend because you are blocked.");
                        break;
                    }
                    ArrayList<String> searchedFriends = searchedUser.getFriends();
                    if (searchedFriends == null) {
                        searchedFriends = new ArrayList<>();
                    }
                    ArrayList<String> currentFriends = currentUser.getFriends();
                    if (currentFriends == null) {
                        currentFriends = new ArrayList<>();
                    }
                    if (currentFriends.contains(searchedUser.getUsername())) {
                        currentFriends.remove(searchedUser.getUsername());
                        if (!currentUser.writeToFile()) {
                            currentFriends.add(searchedUser.getUsername());
                            System.out.println("Action not completed");
                        } else {
                            System.out.println(searchedUser.getUsername() + " removed as a friend!");
                        }
                        break;
                    } else {
                        currentFriends.add(searchedUser.getUsername());
                        if (!currentUser.writeToFile()) {
                            currentFriends.remove(searchedUser.getUsername());
                            System.out.println("Action not completed");
                        } else {
                            System.out.println(searchedUser.getUsername() + " added as a friend!");
                        }
                        break;
                    }
                case "2":
                    ArrayList<String> blocked = currentUser.getBlocked();
                    if (blocked == null) {
                        blocked = new ArrayList<>();
                    }
                    if (blocked.contains(searchedUser.getUsername())) { // already blocked
                        blocked.remove(searchedUser.getUsername()); // removed from blocked list
                        if (!currentUser.writeToFile()) {
                            blocked.add(searchedUser.getUsername());
                            System.out.println("Action not completed");
                            break;
                        }
                        System.out.println("Successfully unblocked " + searchedUser.getUsername());
                    } else {
                        blocked.add(searchedUser.getUsername()); // if not on list, block them
                        currentUser.getFriends().remove(searchedUser.getUsername());
                        if (!currentUser.writeToFile()) {
                            blocked.remove(searchedUser.getUsername()); // if fails, remove
                            currentUser.getFriends().add(searchedUser.getUsername());
                            System.out.println("Action not completed");
                            break;
                        } else { // if it didnt fail
                            ArrayList<String> searchedFriends1 = searchedUser.getFriends(); // remove from friends list
                            if (searchedFriends1 != null && searchedFriends1.contains(currentUser.getUsername())) {
                                searchedFriends1.remove(currentUser.getUsername());
                                if (!searchedUser.writeToFile()) {
                                    searchedFriends1.add(currentUser.getUsername());
                                    System.out.println("Action not completed");
                                }
                            }
                            System.out.println(searchedUser.getUsername() + " successfully blocked.");
                            break;
                        }
                    }
                    break;
                case "3":
                    directMessageMenu(currentUser, searchedUser, scan);
                    break;
                case "4":
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!choice.equals("4"));

    } //userViewerMenu

    public static void directMessageMenu(User currentUser, User searchedUser, Scanner scan) {
        ArrayList<String> currentUserBlocked = currentUser.getBlocked();
        if (currentUserBlocked == null) {
            currentUserBlocked = new ArrayList<>();
        }
        ArrayList<String> searchedUserBlocked = currentUser.getBlocked();
        if (searchedUserBlocked == null) {
            searchedUserBlocked = new ArrayList<>();
        }

        if (currentUserBlocked.contains(searchedUser.getUsername())) {
            System.out.println("You cannot message this user. You have the recipient blocked.");
            return;
        }
        if (searchedUserBlocked.contains(currentUser.getUsername())) {
            System.out.println("You cannot message this user. The recipient has you blocked.");
            return;
        }

        if (!searchedUser.isOpenMessaging() && (!currentUser.getFriends().contains(searchedUser.getUsername()) || !searchedUser.getFriends().contains(currentUser.getUsername()))) {
            System.out.println("You cannot message this user. You are not friends. The recipient has open messaging disabled.");
            if (searchedUser.getFriends().contains(currentUser.getUsername())) {
                System.out.println("The recipient has sent you a friend request.\nExit to the main menu to search and add " + searchedUser.getUsername() + " back.");
            }
            return;
        }


        if (!currentUser.isOpenMessaging() && (!currentUser.getFriends().contains(searchedUser.getUsername()) || !searchedUser.getFriends().contains(currentUser.getUsername()))) {
            System.out.println("You cannot message this user. You are not friends. You have open messaging disabled.");
            if (searchedUser.getFriends().contains(currentUser.getUsername())) {
                System.out.println("The recipient has sent you a friend request.\nExit to the main menu to search and add " + searchedUser.getUsername() + " back.");
            }
            return;
        }

        String choice;
        do {
            if (!directMessageMethods.openMessages(currentUser, searchedUser)) {
                System.out.println("Error creating message file.");
                break;
            }
            List<String> messages = directMessageMethods.readMessages(currentUser, searchedUser);
            if (!directMessageMethods.displayMessages(messages)) {
                System.out.println("No messages yet.");
            }
            System.out.println("Direct Message Options:");
            System.out.println("1. Send Message");
            System.out.println("2. Delete Message");
            System.out.println("3. Exit");

            choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("Enter your message or enter 'back' to return:");
                    String message = scan.nextLine();
                    if ("back".equalsIgnoreCase(message)) {
                        return; // Return to showLoginMenu
                    } else {
                        if (!directMessageMethods.sendMessage(currentUser, searchedUser, message)) {
                            System.out.println("Error sending message.");
                            break;
                        }
                    }
                    break;
                case "2":
                    if (messages.isEmpty()) {
                        System.out.println("There are no messages to delete.");
                        break;
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
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid input. Please enter a number.");
                        break;
                    }
                    String messageToDelete = messages.get(deleteIndex);
                    if (messageToDelete.contains(searchedUser.getUsername())) {
                        System.out.println("Cannot delete message that is not your own.");
                        break;
                    }
                    messages.remove(deleteIndex);
                    if (directMessageMethods.writeMessages(currentUser, searchedUser, messages)) {
                    System.out.println("Message deleted successfully");
                    } else {
                        System.out.println("Could not delete message");
                    }
                    break;
                case "3":
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (!choice.equals("3"));
    }
} // End class
