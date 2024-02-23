package service;


import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;


public class ChessService {
    private final static UserDAO userDAO = new MemoryUserDAO();

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
        String hello = UUID.randomUUID().toString();
        return new AuthData(hello, username);
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
        String hello = UUID.randomUUID().toString();
        return new AuthData(hello, username);
    }

    public void logout(UserData user) {
        // Logout

    }

}
