package webSocketMessages.userCommands;

public class JOIN_OBSERVER extends UserGameCommand {
    public final int gameID;

    public JOIN_OBSERVER(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_OBSERVER;
    }

    public int getGameID() {
        return gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JOIN_OBSERVER that = (JOIN_OBSERVER) o;
        return gameID == that.gameID;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + gameID;
        return result;
    }
}
