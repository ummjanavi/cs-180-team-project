import java.util.ArrayList;
/**
 * UserInterface.java
 * 
 * This is the interface for the user class.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */
public interface UserInterface {
    public String getUsername();
    public String getPassword();
    public void setPassword(String password);
    public String getProfilePic();
    public void setProfilePic(String uploadedFilePath);
    public boolean isOpenMessaging();
    public void setOpenMessaging(boolean openMessaging);
    public ArrayList<String> getFriends();
    public void setFriends(ArrayList<String> friends);
    public ArrayList<String> getBlocked();
    public void setBlocked(ArrayList<String> blocked);
    //public void addFriend(TeamProject.User user);
    //public void removeFriend(TeamProject.User user);
    //public void blockUser(TeamProject.User user);
    //public void unblockUser(TeamProject.User user);
    //public boolean equals(TeamProject.User user);
    public String toString(Boolean openMessaging);
    public String toString(ArrayList<String> usernames);
    public boolean writeToFile();
    public void displayProfile();
}
