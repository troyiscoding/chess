package dataAccess;

import model.UserData;

public interface UserDAO {
    void createUser(UserData user);

    UserData getUser(String username);

    void updateUser(UserData user);

    void deleteUser(String username);
}
