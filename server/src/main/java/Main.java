import chess.*;

public class Main {
    public static void main(String[] args) {
        server.Server server = new server.Server();
        server.run(8080);
        
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
    }
}
