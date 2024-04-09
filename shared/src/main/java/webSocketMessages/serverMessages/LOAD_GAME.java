package webSocketMessages.serverMessages;

import model.GameData;

import java.util.Objects;

public class LOAD_GAME extends ServerMessage {
    public final GameData gameData;

    public LOAD_GAME(GameData gameData) {
        super(ServerMessageType.LOAD_GAME);
        this.gameData = gameData;
    }

    public GameData getGameData() {
        return gameData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LOAD_GAME loadGame = (LOAD_GAME) o;
        return Objects.equals(gameData, loadGame.gameData);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(gameData);
        return result;
    }
}
