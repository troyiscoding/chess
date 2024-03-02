package dataAccess;

import model.GameData;

import java.util.HashSet;

public interface GameDAO {
    int insertGame(GameData game) throws DataAccessException;

    GameData findGame(int gameID) throws DataAccessException;

    void updateGame(GameData game) throws DataAccessException;

    void clear() throws DataAccessException;

    HashSet<GameData> getGames() throws DataAccessException;
}
