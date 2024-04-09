package webSocketMessages.userCommands;

import chess.ChessMove;

import java.util.Objects;

public class MAKE_MOVE extends UserGameCommand {
    public final int gameID;
    public final ChessMove move;

    public MAKE_MOVE(String authToken, int gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.commandType = CommandType.MAKE_MOVE;
    }

    public ChessMove getMove() {
        return move;
    }

    public int getGameID() {
        return gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MAKE_MOVE makeMove = (MAKE_MOVE) o;
        return gameID == makeMove.gameID && Objects.equals(move, makeMove.move);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + gameID;
        result = 31 * result + Objects.hashCode(move);
        return result;
    }
}
