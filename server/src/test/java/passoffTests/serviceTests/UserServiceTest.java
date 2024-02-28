package passoffTests.serviceTests;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;
import service.ResponseException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void registerSuccess() throws ResponseException, DataAccessException {
        UserData user = new UserData("testMyUser", "testPassword", "testEmail");
        AuthData authData = assertDoesNotThrow(() -> userService.register(user));
        assertNotNull(authData);
        assertEquals("testMyUser", authData.username());
    }

    @Test
    void registerFailure() {
        // Negative test register with null username
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData(null, "testPassword", "testEmail");
            userService.register(user);
        });

        // test register with null password
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", null, "testEmail");
            userService.register(user);
        });

        // test register with null email
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", "testPassword", null);
            userService.register(user);
        });

        // test register with existing username
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", "testPassword", "testEmail");
            userService.register(user); // First time should be successful
            userService.register(user); // Second time should throw exception
        });
    }


    @Test
    void loginSuccess() throws ResponseException, DataAccessException {
        UserData user = new UserData("testUserLogin", "testPassword", "DOES NOT MATTER");
        AuthData authData = userService.register(user);
        assertNotNull(authData); // Make sure that register worked correctly
        assertEquals("testUserLogin", authData.username()); // Make sure that register worked correctly

        AuthData authData2 = userService.login(user);
        assertNotNull(authData2);
        assertEquals("testUserLogin", authData2.username()); // Make sure that login returned the correct username
        assertNotEquals(authData.authToken(), authData2.authToken()); // Check that the tokens are different

        //Lets try to login again with the same user and see if the token is different
        AuthData authData3 = userService.login(user);
        assertNotNull(authData3);
        assertEquals("testUserLogin", authData3.username()); // Make sure that login returned the correct username
        assertNotEquals(authData.authToken(), authData3.authToken()); // Check that the tokens are different
        assertNotEquals(authData2.authToken(), authData3.authToken()); // Check that the tokens are different
    }

    @Test
    void loginFailure() throws ResponseException, DataAccessException {
        // Negative test login with null username
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData(null, "testPassword", "DOES NOT MATTER");
            userService.login(user);
        });

        // test login with null password
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", null, "DOES NOT MATTER");
            userService.login(user);
        });

        // test login with incorrect username
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("incorrectUsername", "testPassword", "DOES NOT MATTER");
            userService.login(user);
        });

        // test login with incorrect password
        assertThrows(ResponseException.class, () -> {
            UserData user = new UserData("testUser", "testPassword", "DOES NOT MATTER");
            userService.register(user); // First time should be successful
            user = new UserData("testUser", "WRONG PASSWORD", "DOES NOT MATTER");
            userService.login(user); // Second time should throw exception
        });
    }


    @Test
    void logoutSuccess() throws ResponseException, DataAccessException {
        UserData user = new UserData("testUserLogout", "testPassword", "troy@gmail.com");
        AuthData authData = userService.register(user);
        assertNotNull(authData); // Make sure that register worked correctly
        assertEquals("testUserLogout", authData.username()); // Make sure that register worked correctly
        //Test Logout
        assertTrue(userService.logout(authData.authToken()));

        AuthData authData2 = userService.login(user); // Login again to get a new token
        assertNotNull(authData2);// Make sure that login worked correctly
        assertEquals("testUserLogout", authData2.username()); // Make sure that login returned the correct username
        assertNotEquals(authData.authToken(), authData2.authToken()); // Check that the tokens are different
        //Test Logout
        assertTrue(userService.logout(authData2.authToken()));
    }

    @Test
    void logoutFailure() throws ResponseException, DataAccessException {
        // Negative test logout with null username
        assertThrows(ResponseException.class, () -> userService.logout(null));

        // test logout with incorrect username
        assertThrows(ResponseException.class, () -> userService.logout("incorrectToken"));
    }


    @Test
    void clearTestSuccess() throws DataAccessException, ResponseException {
        UserData user = new UserData("testUserClear", "testPassword", "testEmail");
        AuthData authData = userService.register(user);
        assertNotNull(authData); // Make sure that register worked correctly
        assertEquals("testUserClear", authData.username()); // Make sure that register worked correctly

        userService.clear(); // Clear the data

        // Try to login with the same user, it should throw an exception because the data was cleared
        assertThrows(ResponseException.class, () -> userService.login(user));
    }

    //EXTRA TESTS FOR HELPER FUNCTION
    @Test
    void validateAuthTokenBooleanSuccess() throws ResponseException, DataAccessException {
        UserData user = new UserData("testUserToken", "testPassword", "testEmail");
        AuthData authData = userService.register(user);
        boolean isValid = UserService.validateAuthTokenBoolean(authData.authToken());
        assertTrue(isValid);
    }

    @Test
    void validateAuthTokenBooleanFailure() throws DataAccessException {
        assertThrows(ResponseException.class, () -> UserService.validateAuthTokenBoolean(null));
        assertThrows(ResponseException.class, () -> UserService.validateAuthTokenBoolean("incorrectToken"));
    }

    @Test
    void validateAuthTokenStringSuccess() throws ResponseException, DataAccessException {
        UserData user = new UserData("testUserTokenString", "testPassword", "testEmail");
        AuthData authData = userService.register(user);
        String username = UserService.validateAuthTokenString(authData.authToken());
        assertEquals("testUserTokenString", username);
    }

    @Test
    void validateAuthTokenStringFailure() throws ResponseException, DataAccessException {
        assertThrows(ResponseException.class, () -> UserService.validateAuthTokenString(null));
        userService.register(new UserData("testUserTokenStringFailure", "testPassword", "testEmail"));
        assertThrows(ResponseException.class, () -> UserService.validateAuthTokenString("incorrectToken"));
    }


}