package chess;

import java.util.Collection;

public class ValidMove {
    static boolean validMove(int row, int col, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> myMoves) {
        if (row < 9 && row > 0 && col < 9 && col > 0) {
            ChessPiece destinationPiece = board.getPiece(new ChessPosition(row, col));
            if (destinationPiece == null || destinationPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                //Add piece to board if it is empty or an enemy piece
                ChessPosition futurePosition = new ChessPosition(row, col);
                ChessMove addMove = new ChessMove(myPosition, futurePosition, null);
                myMoves.add(addMove);
                return destinationPiece != null;
            } else {
                return true;
            }
        }
        return true;
    }
}
