import java.util.List;
public interface DirectMessageInterface {
    public String getFileName(User currentUser, User searchedUser);
    public boolean openMessages(User currentUser, User searchedUser);
    public List<String> readMessages(User currentUser, User searchedUser);
    public boolean displayMessages(List<String> messages);
    public boolean sendMessage(User currentUser, User searchedUser, String message);
    public boolean writeMessages(User currentUser, User searchedUser, List<String> messages);
}
