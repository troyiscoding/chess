package dataAccess;

import model.GameData;

import java.util.HashSet;

public interface GameDAO {
    int insertGame(GameData game);

    GameData findGame(int gameID);

    void updateGame(GameData game) throws DataAccessException;

    void clear();

    HashSet<GameData> getGames() throws DataAccessException;


}
