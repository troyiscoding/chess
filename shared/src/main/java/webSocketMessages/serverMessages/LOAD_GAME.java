package webSocketMessages.serverMessages;

import model.GameData;

import java.util.Objects;

public class LOAD_GAME extends ServerMessage {
    public final chess.ChessGame game;

    public LOAD_GAME(chess.ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }

    public chess.ChessGame getGameData() {
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LOAD_GAME loadGame = (LOAD_GAME) o;
        return Objects.equals(game, loadGame.game);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(game);
        return result;
    }
}
