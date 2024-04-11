package webSocketMessages.userCommands;

import chess.ChessGame;

import java.util.Objects;

public class JoinPlayer extends UserGameCommand {
    public int gameID;
    public ChessGame.TeamColor playerColor;

    public JoinPlayer(String authToken, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.commandType = CommandType.JOIN_PLAYER;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JoinPlayer that = (JoinPlayer) o;
        return gameID == that.gameID && playerColor == that.playerColor;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + gameID;
        result = 31 * result + Objects.hashCode(playerColor);
        return result;
    }
}
