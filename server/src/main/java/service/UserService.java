package service;


import dataAccess.*;
import model.AuthData;
import model.UserData;

import java.util.UUID;


public class UserService {
    private final static UserDAO userDAO = new MemoryUserDAO();
    private final static AuthDAO authDAO = new MemoryAuthDAO();

    public AuthData register(UserData user) throws ResponseException, DataAccessException {
        String username = user.username();
        String password = user.password();
        String email = user.email();
        if (username == null || password == null || email == null) {
            throw new ResponseException(400, "{ \"message\": \"Error: bad request\" }");
        }
        if (userDAO.getUser(username) != null) {
            throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
        }
        userDAO.createUser(user);
        AuthData authData = new AuthData(UUID.randomUUID().toString(), username);
        authDAO.createAuth(authData);
        return authData;
    }

    public AuthData login(UserData user) throws ResponseException, DataAccessException {
        String username = user.username();
        String password = user.password();
        if (username == null || password == null) {
            throw new ResponseException(401, "{ \"message\": \"Error: Invalid Request\" }");
        }
        UserData userFromDB = userDAO.getUser(username);
        if (userFromDB == null || !userFromDB.password().equals(password)) {
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        }
        AuthData authData = new AuthData(UUID.randomUUID().toString(), username);
        authDAO.createAuth(authData);
        return authData;
    }

    public boolean logout(String authToken) throws ResponseException, DataAccessException {
        // Logout
        if (authToken == null || authToken.isEmpty()) {
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        }
        AuthData authData = authDAO.getAuth(authToken);
        if (authData == null) {
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        }
        authDAO.deleteAuth(authToken);
        return true;
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
    }
}
