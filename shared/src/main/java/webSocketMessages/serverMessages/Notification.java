package webSocketMessages.serverMessages;

import java.util.Objects;

public class Notification extends ServerMessage {
    public String message;

    public Notification(String message) {
        super(ServerMessageType.NOTIFICATION);
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

        Notification that = (Notification) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(message);
        return result;
    }
}
