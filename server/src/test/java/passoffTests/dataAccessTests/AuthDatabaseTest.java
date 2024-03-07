package passoffTests.dataAccessTests;


import dataAccess.DataAccessException;
import dataAccess.DatabaseAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AuthDatabaseTest {
    private DatabaseAuthDAO auth;

    @BeforeAll
    static void startServer() {
        //start my server
    }

    @BeforeEach
    void setUp() throws DataAccessException {
        auth = new DatabaseAuthDAO();
        auth.clear();
    }

    @AfterEach
    void cleanup() throws DataAccessException {
        auth.clear();
    }

    @Test
    void createAuthSuccess() {
        assertDoesNotThrow(() -> auth.createAuth(new AuthData("token", "troy")));
        AuthData fetchAuth = assertDoesNotThrow(() -> auth.getAuth("token"));
        Assertions.assertEquals("troy", fetchAuth.username());
        Assertions.assertEquals("token", fetchAuth.authToken());
    }

    @Test
    void createAuthFailure() {
        assertDoesNotThrow(() -> auth.createAuth(new AuthData("token", "troy")));
        Assertions.assertThrows(Exception.class, () -> auth.createAuth(new AuthData("token", "troy")));
    }

    @Test
    void getAuthSuccess() {
        assertDoesNotThrow(() -> auth.createAuth(new AuthData("123456789", "Formula1")));
        AuthData fetchAuth = assertDoesNotThrow(() -> auth.getAuth("123456789"));
        Assertions.assertEquals("Formula1", fetchAuth.username());
        Assertions.assertEquals("123456789", fetchAuth.authToken());
    }

    @Test
    void getAuthFailure() {
        AuthData fetchAuth = assertDoesNotThrow(() -> auth.getAuth("555"));
        Assertions.assertNull(fetchAuth);
    }

    @Test
    void deleteAuthSuccess() {
        assertDoesNotThrow(() -> auth.createAuth(new AuthData("token", "troy")));
        assertDoesNotThrow(() -> auth.deleteAuth("token"));
        AuthData fetchAuth = assertDoesNotThrow(() -> auth.getAuth("token"));
        Assertions.assertNull(fetchAuth);
    }

    @Test
    void deleteAuthFailure() {
        assertDoesNotThrow(() -> auth.createAuth(new AuthData("token", "troy")));
        Assertions.assertDoesNotThrow(() -> auth.deleteAuth("token"));
        Assertions.assertDoesNotThrow(() -> auth.deleteAuth("invalidToken"));
    }

    @Test
    void clear() {
        assertDoesNotThrow(() -> auth.createAuth(new AuthData("testmyclear", "caffeine")));
        assertDoesNotThrow(() -> auth.clear());
        AuthData fetchAuth = assertDoesNotThrow(() -> auth.getAuth("testmyclear"));
        Assertions.assertNull(fetchAuth);
    }
}