package dataAccess;

import model.UserData;

public interface UserDAO {
    void insertUser(UserData user);

    UserData findUser(String username);

    void updateUser(UserData user);

    void deleteUser(String username);
}
