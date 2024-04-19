import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectMessageMethods {
    public synchronized String getFileName(User currentUser, User searchedUser) {
        ArrayList<String> names = new ArrayList<>();
        names.add(currentUser.getUsername());
        names.add(searchedUser.getUsername());

        // Sort the names alphabetically
        Collections.sort(names);

        return names.get(0) + names.get(1) + "Messages.txt";
    } //getFileName
    public synchronized boolean openMessages(User currentUser, User searchedUser) {
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

    public synchronized List<String> readMessages(User currentUser, User searchedUser) {
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

    public synchronized String displayMessages(List<String> messages) {
        StringBuilder builder = new StringBuilder();
        if (messages.isEmpty()) {
            builder.append("No messages available.");
        } else {
            for (int i = 0; i < messages.size(); i++) {
                builder.append((i + 1)).append(". ").append(messages.get(i)).append("\n");
            }
        }
        return builder.toString();
    } //displayMessages


    public synchronized boolean sendMessage(User currentUser, User searchedUser, String message) {
        String formattedMessage = currentUser.getUsername() + ": " + message;
        String fileName = getFileName(currentUser, searchedUser);
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName, true))) {
            bfw.write(formattedMessage + "\n");
        } catch (IOException e) {
            return false;
        }
        return true;
    } //sendMessage()

    public synchronized boolean writeMessages(User currentUser, User searchedUser, List<String> messages) {
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
