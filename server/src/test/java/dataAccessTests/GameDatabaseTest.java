package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.DatabaseGameDAO;
import model.GameData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameDatabaseTest {
    private DatabaseGameDAO game;

    @BeforeAll
    static void startServer() {
        //start my server
    }

    @BeforeEach
    void setUp() throws DataAccessException {
        game = new DatabaseGameDAO();
        game.clear();
    }

    @AfterEach
    void cleanup() throws DataAccessException {
        game.clear();
    }

    @Test
    void insertGameSuccess() {
        GameData testGame = new GameData(1, "whiteUsername", "blackUsername", "gameName", new ChessGame());
        int returnID = assertDoesNotThrow(() -> game.insertGame(testGame));
        GameData fetchGame = assertDoesNotThrow(() -> game.findGame(returnID));
        Assertions.assertEquals(returnID, fetchGame.gameID());
        Assertions.assertEquals("whiteUsername", fetchGame.whiteUsername());
        Assertions.assertEquals("blackUsername", fetchGame.blackUsername());
        Assertions.assertEquals("gameName", fetchGame.gameName());
    }

    @Test
    void insertGameFailure() {
        Assertions.assertThrows(DataAccessException.class, () -> game.insertGame(null));
        GameData testGame = new GameData(1, "whiteUsername", "blackUsername", null, new ChessGame());
        Assertions.assertThrows(DataAccessException.class, () -> game.insertGame(testGame));
    }

    @Test
    void findGameSuccess() {
        GameData testGame = new GameData(1, "whiteUsername", "blackUsername", "gameName", new ChessGame());
        int returnID = assertDoesNotThrow(() -> game.insertGame(testGame));
        GameData fetchGame = assertDoesNotThrow(() -> game.findGame(returnID));
        Assertions.assertEquals(returnID, fetchGame.gameID());
        Assertions.assertEquals("whiteUsername", fetchGame.whiteUsername());
        Assertions.assertEquals("blackUsername", fetchGame.blackUsername());
        Assertions.assertEquals("gameName", fetchGame.gameName());

        GameData testGame2 = new GameData(2, "whiteUsername2", "blackUsername2", "gameName2", new ChessGame());
        int returnID2 = assertDoesNotThrow(() -> game.insertGame(testGame2));
        GameData fetchGame2 = assertDoesNotThrow(() -> game.findGame(returnID2));
        Assertions.assertEquals(returnID2, fetchGame2.gameID());
        Assertions.assertEquals("whiteUsername2", fetchGame2.whiteUsername());
        Assertions.assertEquals("blackUsername2", fetchGame2.blackUsername());
        Assertions.assertEquals("gameName2", fetchGame2.gameName());
    }

    @Test
    void findGameFailure() {
        //Find a game that does not exist
        Assertions.assertNull(assertDoesNotThrow(() -> game.findGame(64915894)));
        Assertions.assertNull(assertDoesNotThrow(() -> game.findGame(0)));
    }

    @Test
    void updateGameSuccess() {
        GameData testGame = new GameData(1, null, null, "the game", new ChessGame());
        int returnID = assertDoesNotThrow(() -> game.insertGame(testGame));
        GameData fetchGame = assertDoesNotThrow(() -> game.findGame(returnID));
        Assertions.assertEquals(returnID, fetchGame.gameID());
        Assertions.assertNull(fetchGame.whiteUsername());
        Assertions.assertNull(fetchGame.blackUsername());
        Assertions.assertEquals("the game", fetchGame.gameName());
        //Update the game
        GameData updatedGame = new GameData(returnID, "whiteUsername", null, "gameName", new ChessGame());
        assertDoesNotThrow(() -> game.updateGame(updatedGame));
        GameData fetchGame2 = assertDoesNotThrow(() -> game.findGame(returnID));
        Assertions.assertEquals(returnID, fetchGame2.gameID());
        Assertions.assertEquals("whiteUsername", fetchGame2.whiteUsername());
        Assertions.assertNull(fetchGame2.blackUsername());
        Assertions.assertEquals("gameName", fetchGame2.gameName());

        //Update the game again
        GameData updatedGame2 = new GameData(returnID, "whiteUsername", "blackUsername", "gameName", new ChessGame());
        assertDoesNotThrow(() -> game.updateGame(updatedGame2));
        GameData fetchGame3 = assertDoesNotThrow(() -> game.findGame(returnID));
        Assertions.assertEquals(returnID, fetchGame3.gameID());
        Assertions.assertEquals("whiteUsername", fetchGame3.whiteUsername());
        Assertions.assertEquals("blackUsername", fetchGame3.blackUsername());
        Assertions.assertEquals("gameName", fetchGame3.gameName());
    }

    @Test
    void updateGameFailure() {
        //Update a game that is null
        Assertions.assertThrows(DataAccessException.class, () -> game.updateGame(null));
        //Update a game with a invalid gameName
        GameData testGame = new GameData(1, "whiteUsername", "blackUsername", null, new ChessGame());
        Assertions.assertThrows(DataAccessException.class, () -> game.insertGame(testGame));
    }

    @Test
    void clear() {
        //Add a game
        GameData testGame = new GameData(1, "whiteUsername", "blackUsername", "gameName", new ChessGame());
        int returnID = assertDoesNotThrow(() -> game.insertGame(testGame));
        GameData fetchGame = assertDoesNotThrow(() -> game.findGame(returnID));
        Assertions.assertEquals(returnID, fetchGame.gameID());
        Assertions.assertEquals("whiteUsername", fetchGame.whiteUsername());
        Assertions.assertEquals("blackUsername", fetchGame.blackUsername());
        Assertions.assertEquals("gameName", fetchGame.gameName());
        //Clear the database
        assertDoesNotThrow(() -> game.clear());
        Assertions.assertNull(assertDoesNotThrow(() -> game.findGame(returnID)));
    }

    @Test
    void getGamesSuccess() {
        //Add a game
        GameData testGame = new GameData(1, "whiteUsername", "blackUsername", "gameName", new ChessGame());
        int returnID = assertDoesNotThrow(() -> game.insertGame(testGame));
        GameData fetchGame = assertDoesNotThrow(() -> game.findGame(returnID));
        Assertions.assertEquals(returnID, fetchGame.gameID());
        Assertions.assertEquals("whiteUsername", fetchGame.whiteUsername());
        Assertions.assertEquals("blackUsername", fetchGame.blackUsername());
        Assertions.assertEquals("gameName", fetchGame.gameName());
        //Get all games
        Assertions.assertEquals(1, assertDoesNotThrow(() -> game.getGames().size()));

        //Add another game
        GameData testGame2 = new GameData(2, "whiteUsername2", "blackUsername2", "gameName2", new ChessGame());
        int returnID2 = assertDoesNotThrow(() -> game.insertGame(testGame2));
        GameData fetchGame2 = assertDoesNotThrow(() -> game.findGame(returnID2));
        Assertions.assertEquals(returnID2, fetchGame2.gameID());
        Assertions.assertEquals("whiteUsername2", fetchGame2.whiteUsername());
        Assertions.assertEquals("blackUsername2", fetchGame2.blackUsername());
        Assertions.assertEquals("gameName2", fetchGame2.gameName());
        //Get all games
        Assertions.assertEquals(2, assertDoesNotThrow(() -> game.getGames().size()));
    }

    @Test
    void getGamesFailure() {
        //Get all games when there are none
        Assertions.assertEquals(0, assertDoesNotThrow(() -> game.getGames().size()));
        Assertions.assertDoesNotThrow(() -> game.clear());

    }
}
