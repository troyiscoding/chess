package model;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, chess.ChessGame game) {
    public GameData {
        if (whiteUsername == null || whiteUsername.isBlank()) {
            throw new IllegalArgumentException("White Username cannot be null or empty");
        }
        if (blackUsername == null || blackUsername.isBlank()) {
            throw new IllegalArgumentException("Black Username cannot be null or empty");
        }
        if (gameName == null || gameName.isBlank()) {
            throw new IllegalArgumentException("Game Name cannot be null or empty");
        }
    }
}
