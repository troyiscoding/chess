package webSocketMessages.serverMessages;

import java.util.Objects;

public class Error extends ServerMessage {
    public String errorMessage;

    public Error(String message) {
        super(ServerMessageType.ERROR);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Error error = (Error) o;
        return Objects.equals(errorMessage, error.errorMessage);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(errorMessage);
        return result;
    }
}
