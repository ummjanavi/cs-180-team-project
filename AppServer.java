import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppServer implements ServerInterface {

    private static final int DEFAULT_PORT = 4545;
    private static LoginMethods loginMethods = new LoginMethods();
    private static SearchMethods searchMethods = new SearchMethods();
    private static DirectMessageMethods directMessageMethods = new DirectMessageMethods();
    ; //Calls User(username) to read the user's file and create currentUser

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("Server started. Waiting for clients to connect...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create input/output streams for communication with the client
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                // Handle client requests
                handleClient(reader, writer);

                // Close the client socket
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(BufferedReader reader, PrintWriter writer) throws IOException {
        String input;
        do {
            input = reader.readLine();
            System.out.println("Received: " + input);
            if (input == null || input.equals("exit")) {
                break;
            }
            String username = "";
            System.out.println(input);
            if (input.equals("CHECK_USERNAME")) {
                username = reader.readLine();
                String output = String.valueOf(loginMethods.checkUsername(username));
                writer.write(output);
                writer.println();
                writer.flush();
            } else if (input.equals("CREATE_ACCOUNT")) {
                username = reader.readLine();
                String password = reader.readLine();
                String output = String.valueOf(loginMethods.createAccount(username, password));
                writer.write(output);
                writer.println();
                writer.flush();
            } else if (input.equals("VALIDATE_LOGIN")) {
                username = reader.readLine();
                String password = reader.readLine();
                String output = String.valueOf(loginMethods.validateLogin(username, password));
                writer.write(output);
                writer.println();
                writer.flush();
            } else if (input.equals("CHANGE_PASSWORD")) {
                username = reader.readLine();
                String password = reader.readLine();
                User currentUser = new User(username);
                currentUser.setPassword(password);
                currentUser.writeToFile();
                boolean output = currentUser.writeToFile();
                writer.write(String.valueOf(output));
                writer.println();
                writer.flush();
            } else if (input.equals("WRITE_TO_FILE")) {
                username = reader.readLine();
                System.out.println(username);
                User currentUser = new User(username);
                System.out.println(currentUser.getPassword());
                currentUser.writeToFile();
                boolean output = currentUser.writeToFile();
                writer.write(String.valueOf(output));
                System.out.println(currentUser.getPassword());
                writer.println();
                writer.flush();
            } else if (input.equals("SET_OPEN_MESSAGING_TRUE")) {
                username = reader.readLine();
                User currentUser = new User(username);
                currentUser.setOpenMessaging(true);
                currentUser.writeToFile();
                boolean output = currentUser.writeToFile();
                writer.write(String.valueOf(output));
                writer.println();
                writer.flush();
            } else if (input.equals("SET_OPEN_MESSAGING")) {
                username = reader.readLine();
                boolean oldSetting = Boolean.parseBoolean(reader.readLine());
                User currentUser = new User(username);
                currentUser.setOpenMessaging(oldSetting);
                currentUser.writeToFile();
                currentUser.writeToFile();
            } else if (input.equals("SET_OPEN_MESSAGING_FALSE")) {
                username = reader.readLine();
                User currentUser = new User(username);
                currentUser.setOpenMessaging(false);
                currentUser.writeToFile();
                boolean output = currentUser.writeToFile();
                writer.write(String.valueOf(output));
                writer.println();
                writer.flush();
            } else if (input.equals("ADD_FRIEND")) {

                String currentUserStr = reader.readLine();
                String searchedUserStr = reader.readLine();
                User currentUser = new User(currentUserStr);
                User searchedUser = new User(searchedUserStr);

                ArrayList<String> currentUserBlocked = currentUser.getBlocked();
                if (currentUserBlocked == null) { //currentUser doesnt have anyone blocked,
                    currentUserBlocked = new ArrayList<>(); // create an empty array to avoid null pointer
                }

                if (currentUserBlocked.contains(searchedUser.getUsername())) {
                    writer.write("Cannot add " + searchedUser.getUsername() + " since they are blocked.");
                    writer.println();
                    writer.flush();
                }

                ArrayList<String> searchedUserBlocked = searchedUser.getBlocked();
                if (searchedUserBlocked == null) {
                    //if (searchedUserBlocked == null) { // searchedUser doesn't have anyone blocked,
                    searchedUserBlocked = new ArrayList<>(); //create an empty array to avoid null pointer
                }

                if (searchedUserBlocked.contains(currentUser.getUsername())) {
                    writer.write("Cannot add " + searchedUser.getUsername() +
                            " as a friend because you are blocked.");
                    writer.println();
                    writer.flush();
                }
                if (!searchedUserBlocked.contains(currentUser.getUsername()) &&
                        !currentUserBlocked.contains(searchedUser.getUsername())) {
                    ArrayList<String> searchedFriends = searchedUser.getFriends();
                    if (searchedFriends == null) { //if searchedUser doesnt have friends
                        searchedFriends = new ArrayList<>(); //create empty array to avoid null pointer
                    }

                    ArrayList<String> currentFriends = currentUser.getFriends();
                    if (currentFriends == null) { //if currentUser doesnt have friends
                        currentFriends = new ArrayList<>(); //create empty array to avoid null pointer
                    }

                    if (currentFriends.contains(searchedUser.getUsername())) { // if they are friends
                        currentFriends.remove(searchedUser.getUsername());
                        //remove searchedUser frm currentUser friends
                        if (!currentUser.writeToFile()) { //write to user, if fails, add searchedUser back
                            currentFriends.add(searchedUser.getUsername());
                            writer.write("Action not completed");
                            writer.println();
                            writer.flush();
                        } else {
                            writer.write(searchedUser.getUsername() + " removed as a friend!");
                            writer.println();
                            writer.flush();
                        }
                    } else { // if they arent friends
                        currentFriends.add(searchedUser.getUsername()); // add searchedUser to currentUser friends
                        if (!currentUser.writeToFile()) { //write to user, if fails, remove searchedUser
                            currentFriends.remove(searchedUser.getUsername());
                            writer.write("Action not completed");
                            writer.println();
                            writer.flush();
                        } else {
                            writer.write(searchedUser.getUsername() + " added as a friend!");
                            writer.println();
                            writer.flush();
                        }
                    }
                }
            } else if (input.equals("BLOCK_USER")) {
                String currentUserStr = reader.readLine();
                String searchedUserStr = reader.readLine();
                User currentUser = new User(currentUserStr);
                User searchedUser = new User(searchedUserStr);

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
                        writer.write("Action not completed - 1");
                        writer.println();
                        writer.flush();
                    }
                    writer.write("Successfully unblocked " + searchedUser.getUsername());
                    writer.println();
                    writer.flush();
                } else {
                    blocked.add(searchedUser.getUsername()); // if not on list, block them
                    boolean removed = friends.remove(searchedUser.getUsername());
                    if (!currentUser.writeToFile()) { //write to currentUser file,
                        blocked.remove(searchedUser.getUsername()); // if fails, remove
                        if (removed) { // if searchedUser was removed from currentUser friends
                            friends.add(searchedUser.getUsername());
                            //add searchedUser back to currentUser friends
                        }
                        writer.write("Action not completed - 2");
                        writer.println();
                        writer.flush();
                    } else { // if writeToFile of current user didn't fail, update searchedUser friends
                        ArrayList<String> searchedFriends1 = searchedUser.getFriends();
                        // remove from friends list
                        if (searchedFriends1 != null && searchedFriends1.contains(currentUser.getUsername())) {
                            searchedFriends1.remove(currentUser.getUsername());
                            if (!searchedUser.writeToFile()) {
                                searchedFriends1.add(currentUser.getUsername());
                                writer.write("Action not completed - 3");
                                writer.println();
                                writer.flush();
                            }
                        }
                        writer.write(searchedUser.getUsername() + " successfully blocked.");
                        writer.println();
                        writer.flush();
                    }
                }
            } else if (input.equals("DISPLAY_PROFILE")) {
                String selectedUser = reader.readLine();
                User searchedUser = new User(selectedUser);
                writer.write(searchedUser.displayProfile());
                writer.println();
                writer.flush();

            } else if (input.equals("CHECK_DIRECT")) {
                String currentUserStr = reader.readLine();
                String searchedUserStr = reader.readLine();
                User currentUser = new User(currentUserStr);
                User searchedUser = new User(searchedUserStr);

                ArrayList<String> currentUserBlocked = currentUser.getBlocked();
                if (currentUserBlocked == null) { //checking for null and re-assigning to avoid null pointers.
                    currentUserBlocked = new ArrayList<>();
                }
                ArrayList<String> searchedUserBlocked = searchedUser.getBlocked();
                if (searchedUserBlocked == null) { //checking for null and re-assigning to avoid null pointers.
                    searchedUserBlocked = new ArrayList<>();
                }

                ArrayList<String> currentFriends = currentUser.getFriends();
                if (currentFriends == null) { //checking for null and re-assigning to avoid null pointers.
                    currentFriends = new ArrayList<>();
                }
                ArrayList<String> searchedFriends = searchedUser.getFriends();
                if (searchedFriends == null) { //checking for null and re-assigning to avoid null pointers.
                    searchedFriends = new ArrayList<>();
                }

                if (currentUserBlocked.contains(searchedUser.getUsername())) {
                    writer.write("You cannot message this user. You have the recipient blocked.");
                } else if (searchedUserBlocked.contains(currentUser.getUsername())) {
                    writer.write("You cannot message this user. The recipient has you blocked.");
                } else if (!searchedUser.isOpenMessaging() && (!currentFriends.contains(searchedUser.getUsername())
                        || !searchedFriends.contains(currentUser.getUsername()))) {
                    writer.write("You cannot message this user. You are not mutually friends. " +
                            "The recipient has open messaging disabled.");
                    if (searchedFriends.contains(currentUser.getUsername())) {
                        writer.write("The recipient has sent you a friend request. " +
                                "Exit to the main menu to search and add " + searchedUser.getUsername() + " back.");
                    }
                } else if (!currentUser.isOpenMessaging() &&
                        (!currentUser.getFriends().contains(searchedUser.getUsername())
                                || !searchedUser.getFriends().contains(currentUser.getUsername()))) {
                    writer.write("You cannot message this user. You are not friends. " +
                            "You have open messaging disabled.");
                    if (searchedUser.getFriends().contains(currentUser.getUsername())) {
                        writer.write("The recipient has sent you a friend request. " +
                                "Exit to the main menu to search and add " + searchedUser.getUsername() + " back.");
                    }
                } else {
                    writer.write("GOOD_TO_MESSAGE\n");

                }
                writer.println();
                writer.flush();
            } else if (input.equals("OPEN_MESSAGES")) {
                User currentUser = new User(reader.readLine());
                User searchedUser = new User(reader.readLine());
                boolean output = directMessageMethods.openMessages(currentUser, searchedUser);
                System.out.println("output " + output);
                writer.write(String.valueOf(output));
                writer.println();
                writer.flush();

            } else if (input.equals("READ_AND_DISPLAY_MESSAGES")) {
                User currentUser = new User(reader.readLine());
                User searchedUser = new User(reader.readLine());
                List<String> messages = directMessageMethods.readMessages(currentUser, searchedUser);
                writer.write("" + messages.size());
                writer.println();
                writer.flush();
                System.out.println(messages.size());
                String display = directMessageMethods.displayMessages(messages);
                System.out.println(display);
                writer.write(display);
                writer.println();
                writer.flush();
            } else if (input.equals("SEND_MESSAGE")) {
                User currentUser = new User(reader.readLine());
                User searchedUser = new User(reader.readLine());
                String message = reader.readLine();
                boolean output = directMessageMethods.sendMessage(currentUser, searchedUser, message);
                writer.write(String.valueOf(output));
                writer.println();
                writer.flush();
            } else if (input.equals("WRITE_MESSAGES")) {
                User currentUser = new User(reader.readLine());
                User searchedUser = new User(reader.readLine());
                List<String> messages = directMessageMethods.readMessages(currentUser, searchedUser);
                boolean output = directMessageMethods.writeMessages(currentUser, searchedUser, messages);
                writer.write(String.valueOf(output));
                writer.println();
                writer.flush();
            }
        } while (true);
    }
}
