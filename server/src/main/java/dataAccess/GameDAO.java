package dataAccess;

import model.GameData;

public interface GameDAO {
    void insertGame(GameData game);

    GameData findGame(int gameID);

    void updateGame(GameData game);

    void deleteGame(int gameID);
}
