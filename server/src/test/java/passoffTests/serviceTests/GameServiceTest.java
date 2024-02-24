package passoffTests.serviceTests;

import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.GameService;
import service.ResponseException;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private UserService userService;
    private GameService gameService;


    @BeforeEach
    void setUp() {
        gameService = new GameService();
        userService = new UserService();
    }

    @Test
    void createGameSuccess() throws ResponseException, DataAccessException {
        GameData game = new GameData(1, "player1", "player2", "gameStatus", null);
        UserData user = new UserData("testUser", "testPassword", "testEmail");
        AuthData authData = userService.register(user);
        assertNotNull(authData);
        assertEquals("testUser", authData.username());

        //Create a game
        GameData createdGame = gameService.createGame(game, authData.authToken());
        assertNotNull(createdGame);
        assertEquals(1, createdGame.gameID());
    }

    @Test
    void createGameFailure() throws DataAccessException {
        GameData game = new GameData(1, "player1", "player2", "gameStatus", null);
        assertThrows(ResponseException.class, () -> gameService.createGame(game, null));

        assertThrows(ResponseException.class, () -> gameService.createGame(game, "incorrectToken"));
    }

}
