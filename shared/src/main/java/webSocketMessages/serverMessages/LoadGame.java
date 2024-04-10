package webSocketMessages.serverMessages;

import java.util.Objects;

public class LoadGame extends ServerMessage {
    public chess.ChessGame game;

    public LoadGame(chess.ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        //if (game == null) {
        //  this.game = new chess.ChessGame();
        //} else {
        this.game = game;
        //}
    }

    public chess.ChessGame getGameData() {
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LoadGame loadGame = (LoadGame) o;
        return Objects.equals(game, loadGame.game);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(game);
        return result;
    }
}
