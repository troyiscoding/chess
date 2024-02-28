package passoffTests.serviceTests;

import dataAccess.DataAccessException;
import handler.ListResponse;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.GameService;
import service.ResponseException;
import service.UserService;

import java.util.ArrayList;

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
        GameData game2 = new GameData(1, "player1", "player2", "gameStatus", null);
        GameData createdGame2 = gameService.createGame(game2, authData.authToken());
        assertNotNull(createdGame2);
        assertNotEquals(createdGame.gameID(), createdGame2.gameID());
    }

    @Test
    void createGameFailure() {
        GameData game = new GameData(1, "player1", "player2", "gameStatus", null);
        assertThrows(ResponseException.class, () -> gameService.createGame(game, null));
        assertThrows(ResponseException.class, () -> gameService.createGame(game, "incorrectToken"));
    }

    @Test
    void listGamesSuccess() throws ResponseException, DataAccessException {
        //Insert a few games
        GameData game = new GameData(1, "whiteUser1", "blackUser1", "testgame1", null);
        UserData user = new UserData("testUser", "testPassword", "testEmail");
        AuthData authData = userService.register(user);
        assertNotNull(authData);
        assertEquals("testUser", authData.username());

        //Create a game
        GameData createdGame = gameService.createGame(game, authData.authToken());
        assertNotNull(createdGame);
        GameData game2 = new GameData(2, "whiteUser2", "blackUser2", "testgame2", null);
        GameData createdGame2 = gameService.createGame(game2, authData.authToken());
        assertNotNull(createdGame2);
        assertNotEquals(createdGame.gameID(), createdGame2.gameID());

        // Call the listGames method with a valid auth token
        ArrayList<ListResponse> gamesList = gameService.listGames(authData.authToken());

        // Check that the gamesList is not null
        assertNotNull(gamesList);

        // Check that the gamesList is not empty
        assertFalse(gamesList.isEmpty());

        //Check that it returns the correct number of games
        assertEquals(2, gamesList.size());

        //Check that it returns the correct games and information
        assertTrue(gamesList.contains(new ListResponse(createdGame.gameID(), "whiteUser1", "blackUser1", "testgame1")));
        assertTrue(gamesList.contains(new ListResponse(createdGame2.gameID(), "whiteUser2", "blackUser2", "testgame2")));
    }

    @Test
    void listGamesFailure() throws ResponseException, DataAccessException {
        // Call the listGames method with an invalid auth token
        assertThrows(ResponseException.class, () -> gameService.listGames("invalidAuthToken"));

        // Call the listGames method with a null auth token
        assertThrows(ResponseException.class, () -> gameService.listGames(null));

        // Call the listGames method with an empty auth token
        assertThrows(ResponseException.class, () -> gameService.listGames(""));

        //Insert a few games
        UserData user = new UserData("testUser", "testPassword", "testEmail");
        AuthData authData = userService.register(user);
        assertNotNull(authData);
        assertEquals("testUser", authData.username());

        // Call the listGames method with a valid auth token
        ArrayList<ListResponse> gamesList = gameService.listGames(authData.authToken());

        // Check that the gamesList is not null
        assertNotNull(gamesList);

        // Check that the gamesList is empty
        assertTrue(gamesList.isEmpty());
    }


}
