package passoffTests.dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.DatabaseUserDAO;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserDatabaseTest {
    private DatabaseUserDAO databaseUserDAO;

    @BeforeAll
    static void startServer() {
        //start my server
    }

    @BeforeEach
    void setUp() throws DataAccessException {
        databaseUserDAO = new DatabaseUserDAO();
        databaseUserDAO.clear();
    }

    @AfterEach
    void cleanup() throws DataAccessException {
        databaseUserDAO.clear();
    }

    @Test
    void createUserSuccess() {
        UserData user = new UserData("troy", "password", "gmail@gmail.com");
        Assertions.assertDoesNotThrow(() -> databaseUserDAO.createUser(user));
        UserData fetchUser = assertDoesNotThrow(() -> databaseUserDAO.getUser("troy"));
        Assertions.assertEquals(user.username(), fetchUser.username());
        Assertions.assertEquals(user.password(), fetchUser.password());
        Assertions.assertEquals(user.email(), fetchUser.email());
    }

    @Test
    void createUserFailure() {
        UserData user = new UserData("MagnusCarlsen", "password", "gmail@gmail.com");
        Assertions.assertDoesNotThrow(() -> databaseUserDAO.createUser(user));
        Assertions.assertThrows(Exception.class, () -> databaseUserDAO.createUser(user));
    }

    @Test
    void getUserSuccess() {
        UserData user = new UserData("ChessMaster", "secret", "worthen@byu.edu");
        Assertions.assertDoesNotThrow(() -> databaseUserDAO.createUser(user));
        UserData fetchUser = assertDoesNotThrow(() -> databaseUserDAO.getUser("ChessMaster"));
        Assertions.assertEquals(user.username(), fetchUser.username());
        Assertions.assertEquals(user.password(), fetchUser.password());
        Assertions.assertEquals(user.email(), fetchUser.email());

        //Try another user registration
        UserData user2 = new UserData("TestingCenter", "github", "why");
        Assertions.assertDoesNotThrow(() -> databaseUserDAO.createUser(user2));
        UserData fetchUser2 = assertDoesNotThrow(() -> databaseUserDAO.getUser("TestingCenter"));
        Assertions.assertEquals(user2.username(), fetchUser2.username());
        Assertions.assertEquals(user2.password(), fetchUser2.password());
        Assertions.assertEquals(user2.email(), fetchUser2.email());
    }

    @Test
    void getUserFailure() throws DataAccessException {
        Assertions.assertNull(databaseUserDAO.getUser("NOBODY"));
    }


    @Test
    void clearSuccess() throws DataAccessException {
        UserData user = new UserData("cleared", "admin", "email");
        Assertions.assertDoesNotThrow(() -> databaseUserDAO.createUser(user));
        Assertions.assertDoesNotThrow(() -> databaseUserDAO.clear());
        Assertions.assertNull(databaseUserDAO.getUser("cleared"));
        Assertions.assertNull(databaseUserDAO.getUser("troy"));
        Assertions.assertNull(databaseUserDAO.getUser("MagnusCarlsen"));
        Assertions.assertNull(databaseUserDAO.getUser("ChessMaster"));
        Assertions.assertNull(databaseUserDAO.getUser("TestingCenter"));
    }


}
