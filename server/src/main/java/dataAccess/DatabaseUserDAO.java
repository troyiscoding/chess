package dataAccess;

import model.UserData;

import java.sql.SQLException;
import java.sql.Statement;

import static dataAccess.DatabaseManager.*;

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
        configureDB(CREATE_TABLE_STATEMENTS);
        //Implement password hashing
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String hashedPassword = encoder.encode(user.password());
        String sql = "INSERT INTO `User` (`username`, `password`, `email`) VALUES ('" + user.username() + "', '" + user.password() + "', '" + user.email() + "')";
        executeStatement(sql);

    }

    public UserData getUser(String username) throws DataAccessException {
        configureDB(CREATE_TABLE_STATEMENTS);
        try (Statement statement = getConnection().createStatement()) {
            var result = statement.executeQuery(
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
        configureDB(CREATE_TABLE_STATEMENTS);
        String sql = "DELETE FROM `User`;";
        executeStatement(sql);
    }

}
