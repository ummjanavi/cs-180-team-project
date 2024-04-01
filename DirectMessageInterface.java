import java.util.List;
/**
 * DirectMessageInterface.java
 * 
 * Interface for direct message methods. 
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */
public interface DirectMessageInterface {
    public String getFileName(User currentUser, User searchedUser);
    public boolean openMessages(User currentUser, User searchedUser);
    public List<String> readMessages(User currentUser, User searchedUser);
    public boolean displayMessages(List<String> messages);
    public boolean sendMessage(User currentUser, User searchedUser, String message);
    public boolean writeMessages(User currentUser, User searchedUser, List<String> messages);
}
