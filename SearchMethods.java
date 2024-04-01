import java.io.File;
import java.util.ArrayList;
/**
 * SearchMethods.java
 * 
 * This class contains all the methods needed for searching a user.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */
public class SearchMethods implements SearchInterface {
    public ArrayList<String> searchUsers(String search) {
        File dir = new File("."); // Assuming user files are in the current directory
        File[] files = dir.listFiles();
        ArrayList<String> matchedUsers = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                // Get the file name and remove the .txt extension
                String fileName = file.getName();
                if (fileName.endsWith(".txt")) {
                    String username = fileName.substring(0, fileName.length() - 4);

                    if (username.contains(search) && !username.contains("Messages")) {
                        matchedUsers.add(username);
                    }
                }
            }
        }

        // Return the list of matched users directly
        return matchedUsers;
    } //searchUsers()
} //end class
