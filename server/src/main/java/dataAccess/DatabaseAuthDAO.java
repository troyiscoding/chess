package dataAccess;

import model.AuthData;

import static dataAccess.DatabaseManager.*;

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
        configureDB(CREATE_TABLE_STATEMENTS);
        String sql = "INSERT INTO `Auth` (`authtoken`, `username`) VALUES ('" + auth.authToken() + "', '" + auth.username() + "');";
        executeStatement(sql);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        AuthData authReturn = null;
        configureDB(CREATE_TABLE_STATEMENTS);
        try (var statement = getConnection().createStatement()) {
            var result = statement.executeQuery(
                    "SELECT * FROM `Auth` WHERE `authtoken` = '" + authToken + "'");
            if (result.next()) {
                authReturn = new AuthData(result.getString("authtoken"), result.getString("username"));
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: failed to get auth");
        }
        return authReturn;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        configureDB(CREATE_TABLE_STATEMENTS);
        String sql = "DELETE FROM `Auth` WHERE `authtoken` = '" + authToken + "'";
        executeStatement(sql);
    }

    public void clear() throws DataAccessException {
        configureDB(CREATE_TABLE_STATEMENTS);
        String sql = "DELETE FROM `Auth`;";
        executeStatement(sql);
    }
}
