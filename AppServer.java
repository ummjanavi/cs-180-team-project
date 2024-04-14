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
                    //ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());

                    String searchQuery = reader.readLine();
                    System.out.println("From Client -> " + searchQuery);
                    //writer.writeObject(" From Server Hello");
                    /*
                      send data to server like
                      user:username|password|createaccount
                    */
                    String result = parseData(searchQuery,writer);
                    if(!Objects.equals(result, ""))
                        writer.println(result);
                        //writer.writeObject(result);
                    writer.flush();
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

    private static String parseData(String clientInput, PrintWriter writer) throws IOException {
        // check for user
        if(clientInput.contains("user:")) {
            clientInput = clientInput.replace("user:","");
            String username, password;
            username = clientInput.split("|")[0];
            password = clientInput.split("|")[1];
            accountCreation(username, password);
        }
        // search for user
        else if (clientInput.contains("searchuser:")){
            String currentUser = clientInput.replace("searchuser:","");
            ArrayList<String> fullResult = searchMethods.searchUsers(currentUser);
            for(String result: fullResult) {
                writer.println(result);
            }
        }
        return "";
    }
    private static void accountCreation(String username, String password) {
        if (loginMethods.createAccount(username, password)) { //if the account is created successfully
            System.out.println("Account Created Successfully. Sign in using 'Login'");
            new User(username, password);
        } else {
            System.out.println("There was an error creating your account. Please try again.");
        }
    }
}
