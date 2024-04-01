import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * DirectMessageMethods.java
 * 
 * This class has all the direct message methods, used when a user wants to message another user.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */

public class DirectMessageMethods implements DirectMessageInterface {

    public String getFileName(User currentUser, User searchedUser) {
        ArrayList<String> names = new ArrayList<>();
        names.add(currentUser.getUsername());
        names.add(searchedUser.getUsername());

        // Sort the names alphabetically
        Collections.sort(names);

        return names.get(0) + names.get(1) + "Messages.txt";
    } //getFileName
    public boolean openMessages(User currentUser, User searchedUser) {
        String fileName = getFileName(currentUser, searchedUser);
        File messageFile = new File(fileName);

        if (!messageFile.exists()) {
            try {
                messageFile.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    } //openMessages

    public List<String> readMessages(User currentUser, User searchedUser) {
        String fileName = getFileName(currentUser, searchedUser);
        List<String> messages = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader((new FileReader(fileName)))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                messages.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading message file.");
        }
        return messages;
    } //readMessages

    public boolean displayMessages(List<String> messages) {
        if (messages.isEmpty()) {
            return false;
        } else {
            System.out.println("==================================");
            for (int i = 0; i < messages.size(); i++) {
                System.out.println((i+1) + "." + messages.get(i));
            }
            System.out.println("==================================");
        }
        return true;
    } //display messages

    public boolean sendMessage(User currentUser, User searchedUser, String message) {
        String formattedMessage = currentUser.getUsername() + ": " + message;
        String fileName = getFileName(currentUser, searchedUser);
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName, true))) {
            bfw.write(formattedMessage + "\n");
        } catch (IOException e) {
            return false;
        }
        return true;
    } //sendMessage()

    public boolean writeMessages(User currentUser, User searchedUser, List<String> messages) {
        String fileName = getFileName(currentUser, searchedUser);
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName))) {
            for (String message : messages) {
                bfw.write(message + "\n");
            }
        } catch (IOException e) {
            return false;
        }
       return true;
    } //writeMessages

} //end class
