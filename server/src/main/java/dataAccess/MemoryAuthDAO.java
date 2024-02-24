package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    final static private Map<String, AuthData> authentication = new HashMap<>();

    public void createAuth(AuthData auth) throws DataAccessException {
        authentication.put(auth.authToken(), auth);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return authentication.get(authToken);
    }


    public void deleteAuth(String authToken) throws DataAccessException {
        authentication.remove(authToken);
    }

    public void clear() throws DataAccessException {
        authentication.clear();
    }

}
