import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// I have questions
// 1: Do the removeFriend and removeBlocked methods work? seems to easy
// 2: I assumed that while being stored in the txt file, the friends/blocked will be separated by a comma, but if something else was already decided its an easy change
// 3: We need those new line characters in WriteToFile right?
// 4: We can't actually display the pfp yet right

public class User {
    private String username;
    private String password;
    private String profilePic;  // file name of pfp
    private boolean openMessaging;  // true/false user can receive messages from anyone
    private ArrayList<String> friends;  // String arraylist of friend's usernames
    private ArrayList<String> blocked;  // String arraylist of blocked people's usernames

    public User(String username, String password) {  // called when creating a new user
        this.username = username;
        this.password = password;
        this.profilePic = "default.jpg";
        this.openMessaging = false;
        this.friends = new ArrayList<>();
        this.blocked = new ArrayList<>();
    }

    public User(String username) {  // called to load an existing user
        try {
            BufferedReader br = new BufferedReader(new FileReader(username + ".txt"));
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount == 1) {
                    this.username = line;
                } else if (lineCount == 2) {
                    this.password = line;
                } else if (lineCount == 3) {
                    this.profilePic = line;
                } else if (lineCount == 4) {
                    this.openMessaging = Boolean.parseBoolean(line);
                } else if (lineCount == 5) {
                    String[] f = line.split(",");
                    this.friends = new ArrayList<>(Arrays.asList(f));
                } else if (lineCount == 6) {
                    String[] f = line.split(",");
                    this.blocked = new ArrayList<>(Arrays.asList(f));
                }
                br.close();
            }
        } catch (IOException e) {
            System.out.println("User " + username + " does not exist");
        } catch (Exception e) {
            System.out.println("This should never print, something went very wrong");
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
    public boolean isOpenMessaging() {
        return openMessaging;
    }
    public void setOpenMessaging(boolean openMessaging) {
        this.openMessaging = openMessaging;
    }
    public ArrayList<String> getFriends() {
        return friends;
    }
    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
    public ArrayList<String> getBlocked() {
        return blocked;
    }
    public void setBlocked(ArrayList<String> blocked) {
        this.blocked = blocked;
    }

    // we could change these next 4 to return boolean but idt we need to
    // we could also pass in a username (string) to these next 4 if it makes sense
    public void addFriend(User user) {
        this.friends.add(user.getUsername());
    }

    public void removeFriend(User user) {
        this.friends.remove(user.getUsername());
    }

    public void blockUser(User user) {
        this.blocked.add(user.getUsername());
    }

    public void unblockUser(User user) {
        this.friends.remove(user.getUsername());
    }

    public boolean equals(User user) {
        return (this.username.equals(user.getUsername()));
    }

    // these toString methods make the WriteToFile method much cleaner
    public String toString(Boolean openMessaging) {
        if (openMessaging) {
            return "true";
        } else {
            return "false";
        }
    }
    public String toString(ArrayList<String> usernames) {
        String retThis = "";
        for (String current : usernames) {
            retThis += current + ",";
        }
        retThis = retThis.substring(0, retThis.length() - 1); // to remove last comma
        return retThis;
    }
    public boolean writeToFile() { // writes user data to their file. returns false if failed
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(username + ".txt", false));
            bw.write(this.username + '\n'); // I need these new like characters right?
            bw.write(this.password + '\n');
            bw.write(this.profilePic + '\n');
            bw.write(toString(this.openMessaging) + '\n'); // is this the only setting in the settings menu?
            bw.write(toString(this.friends) + '\n');
            bw.write(toString(this.blocked) + '\n');
            bw.flush();
            bw.close();
            return true;
        } catch (IOException e) {
            System.out.println("Could not save " + this.username + "'s data to his/her file");
            return false;
        }
    }

    public void displayProfile() {  // could change to return boolean
        System.out.println(this.profilePic); // actually display pfp here later
        System.out.println(this.username);
    }
}
