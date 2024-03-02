package dataAccess;

import model.AuthData;

public class DatabaseAuthDAO implements AuthDAO {
    private static final String[] CREATE_TABLE_STATEMENTS = {
            """
        CREATE TABLE IF NOT EXISTS `Auth`(
            `authtoken` VARCHAR(255) NOT NULL,
            `username` VARCHAR(255) NOT NULL,
            PRIMARY KEY(`authtoken`)
        );"""
    };

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
