import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class LoginMethods implements LoginInterface {
//        public boolean checkUsername(String username) {
//            try {
//                FileReader fr = new FileReader("ummjanavi.txt");
//                BufferedReader bfr = new BufferedReader(fr);
//                String line = bfr.readLine();
//                while (line != null) {
//                    if (line.equals(username)) {
//                        //System.out.println("Username taken! Please choose another.");
//                        return false;
//                    }
//                }
//            } catch (Exception e) {
//                return false;
//            }
//            return true;
//        }

    public boolean checkUsername(String username) {
        File userFile = new File(username + ".txt");
        // in the future, we need to specify a specific directory where
        // we will store user text files on our server.
        if (!(userFile.exists())) {
            return true;
        } else {
            System.out.println ("Username taken! Please try again with another.");
            return false;
        }
        // I changed this because the previous code was checking
        // the user profile, but we decided that we would check
        // if the user exists based on if they have a file created.
    } // checkUsername()

    public boolean createAccount(String username, String password) {
        File userFile = new File(username + ".txt");
        // again need to change to a specific directory in the future
        try (FileWriter fw = new FileWriter(userFile); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(username + "\n"); // added new line
            bw.write(password + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the user account.\nReturning to Login Menu.");
            return false;
        }
        return true; // Account creation was successful
    } //createAccount()

//    public boolean checkPassword(String username, String password) {
//        try {
//            FileReader fr = new FileReader(username + ".txt");
//            BufferedReader bfr = new BufferedReader(fr);
//            String line;
//            int lineCount = 0;
//            while ((line = bfr.readLine()) != null) {
//                lineCount++;
//                if (lineCount == 2) {
//                    if (!password.equals(line)) {
//                        System.out.println("wrong");
//                        return false;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("exception");
//            return false;
//        }
//        System.out.println("correct");
//        return true;
//    }

    public boolean validateLogin(String username, String password) {
        // changed the method name since this is doing more than checking the password.
        File userFile = new File(username + ".txt");
        if (!(userFile.exists())) {
            System.out.println("User does not exist.\nReturning to Login Menu");
            return false;
        } // need to check if the user exists before checking password

        try (BufferedReader bfr = new BufferedReader(new FileReader(userFile))) {
            bfr.readLine(); // skipping the first line of the userFile
            String storedPassword = bfr.readLine(); // reading the second line w pass

            if (password.equals(storedPassword)) {
                System.out.println("Login Successful!");
                return true;
            } else {
                System.out.println("Login failed. Incorrect username or password.\nReturning to Login Menu");
                return false;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while logging in.\nReturning to Login Menu.");
            return false;
        }
    } //validate login
} // end class
