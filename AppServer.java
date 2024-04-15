import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppServer implements ServerInterface {

    private static final int Default_Port = 4545;
    private static LoginMethods loginMethods = new LoginMethods();
    private static SearchMethods searchMethods = new SearchMethods();
    private static DirectMessageMethods directMessageMethods = new DirectMessageMethods();
    ; //Calls User(username) to read the user's file and create currentUser


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            ServerSocket serverSocket = new ServerSocket(Default_Port);
            System.out.println("Waiting for the client to connect...");

            try {
                while(true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    boolean exit = false;
                    do {
                        String username = "";
                        String input = reader.readLine();
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
                        } else if (input.equals("SET_OPEN_MESSAGING_TRUE")){
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

                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
