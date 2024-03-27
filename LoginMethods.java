import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class LoginMethods {
    public boolean checkUsername(String username) {
        try {
            FileReader fr = new FileReader("allAccounts.txt");
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                if (line.equals(username)) {
                    //System.out.println("Username taken! Please chose another.");
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean createAccount(String username, String password) {
        try {
            FileWriter fw = new FileWriter(username);
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write(username);
            bfw.write(password);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean checkPassword(String username, String password) {
        try {
            FileReader fr = new FileReader(username);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            int lineCount = 0;
            while (line != null) {
                lineCount++;
                if (lineCount == 2) {
                    if (password != line) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean addProfilePicture(File pictureFile, String username) {
        try {
            BufferedImage image = ImageIO.read(pictureFile);
            FileWriter fw = new FileWriter(username);

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    fw.write(red + " " + green + " " + blue + "\n");
                }
            }
            fw.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
