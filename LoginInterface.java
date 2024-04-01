/**
 * LoginInterface.java
 * 
 * Interface for login methods.
 *
 * @author Johanna Palomar, Janavi Munagavalasa, Arushi Chaudhary, Valeria Paulina Cordero Salinas, Corbett Papastathis,
 * Lecture 1, Lab 10
 * @version 3/25/2024
 */
public interface LoginInterface {
    public boolean checkUsername(String username);
    public boolean createAccount(String username, String password);
    public boolean validateLogin(String username, String password);
}
