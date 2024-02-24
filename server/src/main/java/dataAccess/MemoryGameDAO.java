package dataAccess;

import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    final static private Map<Integer, GameData> games = new HashMap<>();
    static private int gameID = 0;

    public int insertGame(GameData game) {
        gameID++;
        games.put(gameID, game);
        return gameID;
    }

    public GameData findGame(int gameID) {
        return games.get(gameID);
    }

    public void updateGame(GameData game) {
        games.put(game.gameID(), game);
    }

    public void clear() {
        games.clear();
    }

    public Map<Integer, GameData> getGames() throws DataAccessException {
        return games;
    }
}
