package dataAccess;

import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    final static private Map<Integer, GameData> games = new HashMap<>();

    public void insertGame(int gameID, GameData game) {
        games.put(gameID, game);
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
