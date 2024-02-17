package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {
    final private Map<String, UserData> users = new HashMap<>();

    public void createUser(UserData user) {
        users.put(user.username(), user);
    }


    public UserData getUser(String username) {
        return users.getOrDefault(username, null);
    }


    public void updateUser(UserData user) {
        users.put(user.username(), user);
    }

    public void deleteUser(String username) {
        users.remove(username);
    }


}
