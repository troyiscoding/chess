package webSocketMessages.serverMessages;

import java.util.Objects;

public class ERROR extends ServerMessage {
    public final String message;

    public ERROR(String message) {
        super(ServerMessageType.ERROR);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ERROR error = (ERROR) o;
        return Objects.equals(message, error.message);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(message);
        return result;
    }
}
