package service;

import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;


public class ChessService {
    private final UserDAO userDAO;

    public ChessService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public AuthData register(UserData user) {
        String username = user.username();
        String password = user.password();
        String email = user.email();
        if (username == null || password == null || email == null) {//IMPLEMENT EXCEPTIONS
            return null;
        }
        if (this.userDAO.getUser(username) != null) {//IMPLEMENT EXCEPTIONS
            return null;
        }
        userDAO.createUser(user);

        return new AuthData("authToken", username);
    }

    //public AuthData login(UserData user) {
    //}

    //public void logout(UserData user) {
    //}

}
