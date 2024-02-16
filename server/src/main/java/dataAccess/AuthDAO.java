package dataAccess;

import model.AuthData;

public interface AuthDAO {
    void insertAuth(AuthData auth);

    AuthData findAuth(String authToken);

    void updateAuth(AuthData auth);

    void deleteAuth(String authToken);
}
