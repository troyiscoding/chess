package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMoves {
    private HashSet<ChessMove> myMoves = new HashSet<ChessMove>();

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        validMove(myPosition.getRow() + 1, myPosition.getColumn(), board, myPosition);
        validMove(myPosition.getRow() + 1, myPosition.getColumn() + 1, board, myPosition);
        validMove(myPosition.getRow() + 1, myPosition.getColumn() - 1, board, myPosition);
        validMove(myPosition.getRow(), myPosition.getColumn() + 1, board, myPosition);
        validMove(myPosition.getRow(), myPosition.getColumn() - 1, board, myPosition);
        validMove(myPosition.getRow() - 1, myPosition.getColumn(), board, myPosition);
        validMove(myPosition.getRow() - 1, myPosition.getColumn() + 1, board, myPosition);
        validMove(myPosition.getRow() - 1, myPosition.getColumn() - 1, board, myPosition);
        return myMoves;
    }

    private void validMove(int row, int col, ChessBoard board, ChessPosition myPosition) {
        if (row < 9 && row > 0 && col < 9 && col > 0) {
            if (board.getPiece(new ChessPosition(row, col)) == null) {
                //add piece to board
                ChessPosition futurePosition = new ChessPosition(row, col);
                ChessMove addMove = new ChessMove(myPosition, futurePosition, null);
                myMoves.add(addMove);
            }
        }
    }
}
