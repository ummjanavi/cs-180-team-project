import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
        boolean exit = false;
        do {
            String username = "";
            String input = reader.readLine();
            System.out.println("GOT HERE");
            if (input.equals("CHECK_USERNAME")) {
                username = reader.readLine();
                String output = String.valueOf(loginMethods.checkUsername(username));
                writer.write(output);
                writer.println();
                writer.flush();
            } else if (input.equals("VALIDATE_LOGIN")) {
                String password = reader.readLine();
                String output = String.valueOf(loginMethods.validateLogin(username, password));
                writer.write(output);
                writer.println();
                writer.flush();
            } else if (input.equals("WRITE_TO_FILE")) {
                username = reader.readLine();
                User currentUser = new User(username);
                Boolean output = currentUser.writeToFile();
                writer.write(String.valueOf(output));
                writer.println();
                writer.flush();
            } else if (input.equals("SET_OPEN_MESSAGING_TRUE")) {
                username = reader.readLine();
                User currentUser = new User(username);
                currentUser.setOpenMessaging(true);
            } else if (input.equals("SET_OPEN_MESSAGING")) {
                username = reader.readLine();
                boolean oldSetting = Boolean.parseBoolean(reader.readLine());
                User currentUser = new User(username);
                currentUser.setOpenMessaging(oldSetting);
            } else if (input.equals("SET_OPEN_MESSAGING_FALSE")) {
                username = reader.readLine();
                User currentUser = new User(username);
                currentUser.setOpenMessaging(false);
            } else if (input.equals("DISPLAY_PROFILE")) {
                String selectedUser = reader.readLine();
                User searchedUser = new User(selectedUser);
                searchedUser.displayProfile();
            } else if (input.equals("OPEN_MESSAGES")) {
                User currentUser = new User(reader.readLine());
                User searchedUser = new User(reader.readLine());
                boolean output = directMessageMethods.openMessages(currentUser, searchedUser);
                writer.write(String.valueOf(output));
                writer.println();
                writer.flush();
            } else if (input.equals("READ_MESSAGES")) {
                User currentUser = new User(reader.readLine());
                User searchedUser = new User(reader.readLine());
                List<String> messages = directMessageMethods.readMessages(currentUser, searchedUser);
                boolean output = directMessageMethods.displayMessages(messages);
                writer.write(String.valueOf(output));
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
            } else if (input.equals("exit")) {
                exit = true;
            }
        } while (!exit);
    }
}
