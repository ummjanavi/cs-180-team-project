import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class AppServer implements ServerInterface {

    private static final int Default_Port = 4545;
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
                    String searchQuery = reader.readLine();
                    System.out.println("From Client -> " + searchQuery);
                    writer.println("Data");
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
}
