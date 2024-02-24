package service;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MemoryGameDAO;
import model.GameData;

public class GameService {
    private final static GameDAO gameDAO = new MemoryGameDAO();


    public GameData createGame(GameData game, String authToken) throws ResponseException, DataAccessException {
        if (UserService.validateAuthTokenBoolean(authToken)) {
            if (gameDAO.getGames().containsKey(game)) {
                throw new DataAccessException("Game already exists");
            }
            int gameID = gameDAO.insertGame(game);
            return new GameData(gameID, null, null, null, null);
        } else {
            throw new ResponseException(401, "Invalid AuthToken");
        }
    }

    public void joinGame(int gameID, String authToken) throws ResponseException {
        return;

    }

}
