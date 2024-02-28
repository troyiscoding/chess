package dataAccess;

import model.GameData;

import java.util.HashSet;

public class DatabaseGameDAO implements GameDAO {
    public int insertGame(GameData game) {
        return 0;
    }

    public GameData findGame(int gameID) {
        return null;
    }

    public void updateGame(GameData game) throws DataAccessException {

    }

    public void clear() {

    }

    public HashSet<GameData> getGames() throws DataAccessException {
        return null;
    }
}
