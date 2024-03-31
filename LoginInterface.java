public interface LoginInterface {
    public boolean checkUsername(String username);
    public boolean createAccount(String username, String password);
    public boolean validateLogin(String username, String password);
}
