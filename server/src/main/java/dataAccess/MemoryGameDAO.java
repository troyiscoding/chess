package dataAccess;

import model.GameData;

import java.util.*;

public class MemoryGameDAO implements GameDAO {
    final static private Map<Integer, GameData> games = new HashMap<>();


    public int insertGame(GameData game) {
        Random rand = new Random();
        int gameID = rand.nextInt(1000);
        game = new GameData(gameID, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(gameID, game);
        return gameID;
    }

    public GameData findGame(int gameID) {
        return games.get(gameID);
    }

    public void updateGame(GameData game) throws DataAccessException {
        if (game == null) {
            throw new DataAccessException("Game does not exist");
        }
        games.put(game.gameID(), game);
    }

    public void clear() {
        games.clear();
    }

    public HashSet<GameData> getGames() throws DataAccessException {
        return new HashSet<>(games.values());
    }
}
