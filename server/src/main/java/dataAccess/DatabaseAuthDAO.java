package dataAccess;

import model.AuthData;

public class DatabaseAuthDAO implements AuthDAO {
    public void createAuth(AuthData auth) throws DataAccessException {

    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    public void deleteAuth(String authToken) throws DataAccessException {

    }

    public void clear() throws DataAccessException {

    }
}
