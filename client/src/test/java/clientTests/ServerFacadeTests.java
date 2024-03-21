package clientTests;

import model.AuthData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void clear() {
        facade.clearData();
    }

    @Test
    public void registerTestSuccess() {
        AuthData result = assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        Assertions.assertEquals("troy", result.username());
    }

    @Test
    public void registerTestFailure() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        Assertions.assertThrows(Exception.class, () -> facade.register("troy", "password", "email"));
    }

    @Test
    public void loginTestSuccess() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        AuthData result = assertDoesNotThrow(() -> facade.login("troy", "password"));
        Assertions.assertEquals("troy", result.username());
    }

    @Test
    public void loginTestFailure() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        Assertions.assertThrows(Exception.class, () -> facade.login("troy", "wrongpassword"));
    }

    @Test
    public void logoutTestSuccess() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        AuthData result = assertDoesNotThrow(() -> facade.login("troy", "password"));
        assertDoesNotThrow(() -> facade.logout(result.authToken()));
    }

    @Test
    public void logoutTestFailure() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        AuthData result = assertDoesNotThrow(() -> facade.login("troy", "password"));
        assertDoesNotThrow(() -> facade.logout(result.authToken()));
        Assertions.assertThrows(Exception.class, () -> facade.logout(result.authToken()));
    }


}
