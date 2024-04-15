import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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

            MainApplication m = new MainApplication(new Socket("localhost",4545));
            m.start();

            try {
                while(true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    while (true) {
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
                            User currentUser = new User(username);
                            currentUser.setOpenMessaging(true);
                        }
                    }

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
