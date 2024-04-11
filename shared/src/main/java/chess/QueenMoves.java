package chess;

import java.util.Collection;
import java.util.HashSet;

public class QueenMoves {
    private final HashSet<ChessMove> myMoves = new HashSet<>();

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        CheckCross.crossCheck(myPosition, board, myMoves);
        LineCheck.lineCheck(myPosition, board, myMoves);
        return myMoves;
    }

    private boolean validMove(int row, int col, ChessBoard board, ChessPosition myPosition) {
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
