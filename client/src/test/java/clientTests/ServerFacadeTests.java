package clientTests;

import handler.JoinRequest;
import model.AuthData;
import model.GameData;
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

    @Test
    public void createGameTestSuccess() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        AuthData result = assertDoesNotThrow(() -> facade.login("troy", "password"));
        GameData game = assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game"));
        Assertions.assertNotEquals(0, game.gameID());
    }

    @Test
    public void createGameTestFailure() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        Assertions.assertThrows(Exception.class, () -> facade.createGame(null, "Troy's Game"));
        Assertions.assertThrows(Exception.class, () -> facade.createGame("badToken", "Troy's Game"));
    }

    @Test
    public void listGamesTestSuccess() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        AuthData result = assertDoesNotThrow(() -> facade.login("troy", "password"));
        assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game"));
        assertDoesNotThrow(() -> facade.listGames(result.authToken()));
        assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game 2"));
        assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game 3"));
        assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game 4"));
        assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game 5"));
        assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game 6"));
        assertDoesNotThrow(() -> facade.listGames(result.authToken()));
    }

    @Test
    public void listGamesTestFailure() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        Assertions.assertThrows(Exception.class, () -> facade.listGames(null));
        Assertions.assertThrows(Exception.class, () -> facade.listGames("badToken"));
    }

    @Test
    public void joinGameTestSuccess() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        AuthData result = assertDoesNotThrow(() -> facade.login("troy", "password"));
        GameData game = assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game"));
        assertDoesNotThrow(() -> facade.joinGame(result.authToken(), new JoinRequest("white", game.gameID())));
    }

    @Test
    public void joinGameTestFailure() {
        assertDoesNotThrow(() -> facade.register("troy", "password", "email"));
        AuthData result = assertDoesNotThrow(() -> facade.login("troy", "password"));
        GameData game = assertDoesNotThrow(() -> facade.createGame(result.authToken(), "Troy's Game"));
        Assertions.assertThrows(Exception.class, () -> facade.joinGame(null, new JoinRequest("white", game.gameID())));
        Assertions.assertThrows(Exception.class, () -> facade.joinGame("badToken", new JoinRequest("white", game.gameID())));
        assertDoesNotThrow(() -> facade.joinGame(result.authToken(), new JoinRequest("white", game.gameID())));
        Assertions.assertThrows(Exception.class, () -> facade.joinGame(result.authToken(), new JoinRequest("white", game.gameID())));
    }

}
