package webSocketMessages.userCommands;

public class Leave extends UserGameCommand {
    public final int gameID;

    public Leave(String authToken, int gameID) {
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

        Leave leave = (Leave) o;
        return gameID == leave.gameID;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + gameID;
        return result;
    }
}
