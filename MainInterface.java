import java.util.Scanner;
/**
 * MainInterface.java
 *
 * This is the interface for MainApplication.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 4/27/2024
 */
public interface MainInterface {

    public static void main(String[] args) {
    }
    public default void run() {
    }
    private static void showLoginMenu() {
    }
    private static void loginProcess(Scanner scan) {
    }
    private static void accountCreationProcess(Scanner scan) {
    }
    private static void showMainMenu(User currentUser, Scanner scan) {
    }
    public static void showAccountSettings(User currentUser, Scanner scan) {
    }
    public static void changePasswordProcess(User currentUser, Scanner scan) {
    }
    public static void changeDirectMessageSetting(User currentUser, Scanner scan) {
    }
    public static void searchProcess(User currentUser, Scanner scan) {
    }
    public static void userViewerMenu(User currentUser, User searchedUser, Scanner scan) {
    }
    public static void directMessageMenu(User currentUser, User searchedUser, Scanner scan) {
    }
}
