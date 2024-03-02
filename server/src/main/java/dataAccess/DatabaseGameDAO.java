package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import static dataAccess.DatabaseManager.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class DatabaseGameDAO implements GameDAO {
    private static final String[] CREATE_TABLE_STATEMENTS = {
            """
        CREATE TABLE IF NOT EXISTS `Game`(
            `gameID` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
            `whiteUsername` VARCHAR(255) DEFAULT NULL,
            `blackUsername` VARCHAR(255) DEFAULT NULL,
            `gameName` VARCHAR(255) NOT NULL,
            `gameJSON` BLOB DEFAULT NULL
        );"""
    };

    public int insertGame(GameData game) throws DataAccessException {
        configureDB(CREATE_TABLE_STATEMENTS);
        int generatedKey = 0;
        try (var conn = getConnection()) {
            String json = new Gson().toJson(game.game());
            try (var preparedStatement = conn.prepareStatement("INSERT INTO Game (whiteUsername, blackUsername, gameName, gameJSON) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, game.whiteUsername());
                preparedStatement.setString(2, game.blackUsername());
                preparedStatement.setString(3, game.gameName());
                preparedStatement.setString(4, json);
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            throw new DataAccessException("Error: failed to insert game");
        }
        return generatedKey;
    }

    public GameData findGame(int gameID) throws DataAccessException {
        configureDB(CREATE_TABLE_STATEMENTS);
        GameData returnGame = null;
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM `Game` WHERE gameID = ?")) {
                preparedStatement.setInt(1, gameID);
                try (var rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        String whiteUsername = rs.getString("whiteUsername");
                        String blackUsername = rs.getString("blackUsername");
                        String gameName = rs.getString("gameName");
                        String gameJSON = rs.getString("gameJSON");
                        ChessGame game = new Gson().fromJson(gameJSON, ChessGame.class);
                        returnGame = new GameData(gameID, whiteUsername, blackUsername, gameName, game);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: failed to find game");
        }
        return returnGame;
    }

    public void updateGame(GameData game) throws DataAccessException {
        configureDB(CREATE_TABLE_STATEMENTS);
        try (var conn = getConnection()) {
            String json = new Gson().toJson(game.game());
            try (var preparedStatement = conn.prepareStatement("UPDATE `Game` SET whiteUsername = ?, blackUsername = ?, gameName = ?, gameJSON = ? WHERE gameID = ?")) {
                preparedStatement.setString(1, game.whiteUsername());
                preparedStatement.setString(2, game.blackUsername());
                preparedStatement.setString(3, game.gameName());
                preparedStatement.setString(4, json);
                preparedStatement.setInt(5, game.gameID());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: failed to update game");
        }
    }

    public void clear() throws DataAccessException {
        configureDB(CREATE_TABLE_STATEMENTS);
        String sql = "DELETE FROM `Game`;";
        executeStatement(sql);

    }

    public HashSet<GameData> getGames() throws DataAccessException {
        configureDB(CREATE_TABLE_STATEMENTS);
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM `Game`")) {
                HashSet<GameData> games;
                try (var rs = preparedStatement.executeQuery()) {
                    games = new HashSet<>();
                    while (rs.next()) {
                        int gameID = rs.getInt("gameID");
                        String whiteUsername = rs.getString("whiteUsername");
                        String blackUsername = rs.getString("blackUsername");
                        String gameName = rs.getString("gameName");
                        String gameJSON = rs.getString("gameJSON");
                        ChessGame game = new Gson().fromJson(gameJSON, ChessGame.class);
                        games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
                    }
                }
                return games;
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: failed to get games");
        }
    }

}
