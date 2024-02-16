package model;

public record AuthData(String authToken, String username) {
    public AuthData {
        if (authToken == null || authToken.isBlank()) {
            throw new IllegalArgumentException("Auth Token cannot be null or empty");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }
}
