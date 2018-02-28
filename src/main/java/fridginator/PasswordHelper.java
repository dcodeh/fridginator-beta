package fridginator;

public class PasswordHelper {
    
    /**
     * Make the users follow the rules for username length!
     * @param username The username the user would like to validate
     * @return Whether or not the username is valid
     */
    public static boolean validUsername(String username) {
        if(username.length() < Constants.MIN_USERNAME_LENGTH ||
           username.length() > Constants.MAX_USERNAME_LENGTH) {
            System.out.println("Username must be between " +
                               Constants.MIN_USERNAME_LENGTH +
                               " and " +
                               Constants.MAX_USERNAME_LENGTH + 
                               " characters.");
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Make the users follow the standards for password length!
     * @param password The password the user thinks is long enough (mwa ha ha)
     * @return Whether or not the password is valid
     */
    public static boolean validPassword(String password) {
        if(password.length() < Constants.MIN_PASSWORD_LENGTH ||
           password.length() > Constants.MAX_PASSWORD_LENGTH) {
            System.out.println("Password must be between " +
                    Constants.MIN_PASSWORD_LENGTH +
                    " and " +
                    Constants.MAX_PASSWORD_LENGTH + 
                    " characters.");
            return false;
        } else {
            return true;
        }
    }
    
}
