package stock_trading.user;

import stock_trading.exception.UserAlreadyExists;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static List<User> users = new ArrayList<>();
    static public User generateUser(String username,String password) throws UserAlreadyExists{
        User user = users.stream().filter(u->u.getUsername().equals(username)).findFirst().orElse(null);

        if( null!=user )    throw new UserAlreadyExists();
        User newUser = new User(username,password);
        users.add( newUser );
        return newUser;
    }
    static User findUser(String username,String password){
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst().orElse(null);
    }
    public static UserSession verifyUser(String username, String password) {
        User user = findUser(username,password);
        if( null!=user ){
            return new UserSession(user);
        }
        return null;
    }
}
