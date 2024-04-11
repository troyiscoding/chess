package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {
    public final int gameID;

    public Resign(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
    }

    public int getGameID() {
        return gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Resign resign = (Resign) o;
        return gameID == resign.gameID;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + gameID;
        return result;
    }
}
