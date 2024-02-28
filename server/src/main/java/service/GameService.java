package service;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MemoryGameDAO;
import handler.JoinRequest;
import model.GameData;

import java.util.ArrayList;
import java.util.HashSet;

import handler.ListResponse;

public class GameService {
    private final static GameDAO gameDAO = new MemoryGameDAO();


    public GameData createGame(GameData game, String authToken) throws ResponseException, DataAccessException {
        if (game == null || authToken == null) {
            throw new ResponseException(400, "bad request");
        }
        if (UserService.validateAuthTokenBoolean(authToken)) {
            if (gameDAO.getGames().contains(game)) {
                throw new DataAccessException("Game already exists");
            }
            int gameID = gameDAO.insertGame(game);
            return new GameData(gameID, null, null, null, null);
        } else {
            throw new ResponseException(401, "unauthorized");
        }
    }

    public void joinGame(JoinRequest gameData, String authToken) throws ResponseException, DataAccessException {
        if (authToken == null) {
            throw new ResponseException(401, "unauthorized");
        }
        if (gameData == null) {
            throw new ResponseException(400, "Error: bad request");
        }
        if (gameDAO.findGame(gameData.gameID()) == null) {
            throw new ResponseException(400, "Error: bad request");
        }
        // if (gameDAO.getGames().contains(gameData)) {
        //   throw new ResponseException(403, "already taken");
        //}
        if (UserService.validateAuthTokenBoolean(authToken)) {
            GameData game = gameDAO.findGame(gameData.gameID());
            gameDAO.updateGame(game);
        } else {
            throw new ResponseException(401, "unauthorized");
        }
    }

    public ArrayList<ListResponse> listGames(String authToken) throws ResponseException, DataAccessException {
        if (authToken == null) {
            throw new ResponseException(401, "Error: unauthorized");
        }
        if (UserService.validateAuthTokenBoolean(authToken)) {
            ArrayList<ListResponse> returnGame = new ArrayList<>();
            HashSet<GameData> games = gameDAO.getGames();
            if (games != null) {
                for (GameData game : games) {
                    returnGame.add(new ListResponse(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName()));
                }
            }
            return returnGame;
        } else {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }

    public void clear() throws DataAccessException {
        gameDAO.clear();
    }

}
