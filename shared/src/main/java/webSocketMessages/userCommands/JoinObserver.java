package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand {
    public int gameID;

    public JoinObserver(String authToken, int gameID) {
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

        JoinObserver that = (JoinObserver) o;
        return gameID == that.gameID;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + gameID;
        return result;
    }
}
