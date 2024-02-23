package passoffTests.serviceTests;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ChessService;
import service.ResponseException;

import static org.junit.jupiter.api.Assertions.*;

class ChessServiceTest {

    private ChessService chessService;

    @BeforeEach
    void setUp() {
        chessService = new ChessService();
    }

    @Test
    void registerSuccess() throws ResponseException, DataAccessException {
        UserData user = new UserData("testUser", "testPassword", "testEmail");
        AuthData authData = chessService.register(user);
        assertNotNull(authData);
        assertEquals("testUser", authData.username());
    }

    @Test
    void registerFailure() {
        // Negative test register with null username
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData(null, "testPassword", "testEmail");
            chessService.register(user);
        });

        // test register with null password
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", null, "testEmail");
            chessService.register(user);
        });

        // test register with null email
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", "testPassword", null);
            chessService.register(user);
        });

        // test register with existing username
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", "testPassword", "testEmail");
            chessService.register(user); // First time should be successful
            chessService.register(user); // Second time should throw exception
        });
    }


    @Test
    void loginSuccess() throws ResponseException, DataAccessException {
        UserData user = new UserData("testUser", "testPassword", "DOES NOT MATTER");
        AuthData authData = chessService.register(user);
        assertNotNull(authData); // Make sure that register worked correctly
        assertEquals("testUser", authData.username()); // Make sure that register worked correctly

        AuthData authData2 = chessService.login(user);
        assertNotNull(authData2);
        assertEquals("testUser", authData2.username()); // Make sure that login returned the correct username
        assertNotEquals(authData.authToken(), authData2.authToken()); // Check that the tokens are different

        //Lets try to login again with the same user and see if the token is different
        AuthData authData3 = chessService.login(user);
        assertNotNull(authData3);
        assertEquals("testUser", authData3.username()); // Make sure that login returned the correct username
        assertNotEquals(authData.authToken(), authData3.authToken()); // Check that the tokens are different
        assertNotEquals(authData2.authToken(), authData3.authToken()); // Check that the tokens are different
    }

    @Test
    void loginFailure() throws ResponseException, DataAccessException {
        // Negative test login with null username
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData(null, "testPassword", "DOES NOT MATTER");
            chessService.login(user);
        });

        // test login with null password
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", null, "DOES NOT MATTER");
            chessService.login(user);
        });

        // test login with incorrect username
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", "testPassword", "DOES NOT MATTER");
            chessService.login(user);
        });

        // test login with incorrect password
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", "testPassword", "DOES NOT MATTER");
            chessService.register(user); // First time should be successful
            user = new UserData("testUser", "WRONG PASSWORD", "DOES NOT MATTER");
            chessService.login(user); // Second time should throw exception
        });
    }


    @Test
    void logout() {
    }


    // test login

    // Negative options test login with null username,test login with null password
    // test login with incorrect username, test login with incorrect password, test login with data access error


    // test logout

    // Negative options test logout with null username
    // test logout with null password, test logout with incorrect username
    // test logout with incorrect password, test logout with data access error
}