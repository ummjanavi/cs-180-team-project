import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
    private String username; // username of this user
    private String password; // password of this user
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
        this.writeToFile();
    }

    public User(String username) {  // called to load an existing user
        File file = new File(username + ".txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                switch (lineCount) {
                    case 1:
                        this.username = line;
                        break;
                    case 2:
                        this.password = line;
                        break;
                    case 3:
                        this.profilePic = line;
                        break;
                    case 4:
                        this.openMessaging = Boolean.parseBoolean(line);
                        break;
                    case 5:
                        if (!line.isEmpty()) { // Ensure there are friends listed before splitting
                            String[] friendsArray = line.split(",");
                            this.friends = new ArrayList<>(Arrays.asList(friendsArray));
                        } else {
                            this.friends = new ArrayList<>();
                        }
                        break;
                    case 6:
                        if (!line.isEmpty()) { // Ensure there are users listed before splitting
                            String[] blockedArray = line.split(",");
                            this.blocked = new ArrayList<>(Arrays.asList(blockedArray));
                        } else {
                            this.blocked = new ArrayList<>();
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File for user " + username + " does not exist.");
        } catch (IOException e) {
            System.out.println("An IO error occurred while reading user " + username + ".");
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
    public void setProfilePic(String uploadedFilePath) {
        this.profilePic = uploadedFilePath;
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.username + ".txt", false))) {
            bw.write(this.username + '\n'); // I need these new like characters right?
            bw.write(this.password + '\n');
            if (!this.profilePic.equals("default.jpg")) {
                bw.write(this.profilePic + "\n");
            } else {
                bw.write("default.jpg" + "\n");
            }
            bw.write(Boolean.toString(this.openMessaging) + '\n'); // is this the only setting in the settings menu?
            String friendsStr;
            if (this.friends == null) {
                friendsStr = "";
            } else {
                friendsStr = String.join(",", this.friends);
            }
            String blockedStr;
            if (this.blocked == null) {
                blockedStr = "";
            } else {
                blockedStr = String.join(",", this.blocked);
            }
            bw.write(friendsStr + '\n');
            bw.write(blockedStr + '\n');
            bw.flush();
            return true;
        } catch (IOException e) {
            // System.out.println("Could not save " + this.username + "'s data to his/her file");
            return false;
        }
    } //writeToFile

    public String displayProfile() {     // could change to return boolean
        return ("==================================\n" + this.getUsername() + "\n==================================");
    } //displayProfile

}


