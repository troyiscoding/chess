package dataAccess;

import model.UserData;

import java.sql.SQLException;

public class DatabaseUserDAO implements UserDAO {

    private static final String[] CREATE_TABLE_STATEMENTS = {
            """
CREATE TABLE IF NOT EXISTS `User`(
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`username`)
);"""
    };

    public void createUser(UserData user) throws DataAccessException {
        DatabaseManager.configureDB(CREATE_TABLE_STATEMENTS);
        try {
            DatabaseManager.getConnection().createStatement().executeUpdate(
                    "INSERT INTO `User` (`username`, `password`, `email`) VALUES ('" + user.username() + "', '" + user.password() + "', '" + user.email() + "');"
            );
        } catch (SQLException e) {
            throw new DataAccessException("Error: failed to create user");
        }

    }

    public UserData getUser(String username) throws DataAccessException {
        DatabaseManager.configureDB(CREATE_TABLE_STATEMENTS);
        try {
            var result = DatabaseManager.getConnection().createStatement().executeQuery(
                    "SELECT * FROM `User` WHERE `username` = '" + username + "';");
            if (result.next()) {
                return new UserData(result.getString("username"), result.getString("password"), result.getString("email"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error: failed to get user");
        }
        return null;
    }

    public void clear() throws DataAccessException {
        DatabaseManager.configureDB(CREATE_TABLE_STATEMENTS);
        try {
            DatabaseManager.getConnection().createStatement().executeUpdate("DELETE FROM `User`;");
        } catch (SQLException e) {
            throw new DataAccessException("Error: failed to clear users");
        }
    }


}
