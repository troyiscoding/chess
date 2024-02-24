package dataAccess;

import model.GameData;

import java.util.Map;

public interface GameDAO {
    void insertGame(int gameID, GameData game);

    GameData findGame(int gameID);

    void updateGame(GameData game);

    public void clear();

    public Map<Integer, GameData> getGames() throws DataAccessException;


}
