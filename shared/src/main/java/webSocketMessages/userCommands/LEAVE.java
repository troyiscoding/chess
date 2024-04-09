package webSocketMessages.userCommands;

public class LEAVE extends UserGameCommand {
    public final int gameID;

    public LEAVE(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.LEAVE;
    }

    public int getGameID() {
        return gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LEAVE leave = (LEAVE) o;
        return gameID == leave.gameID;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + gameID;
        return result;
    }
}
