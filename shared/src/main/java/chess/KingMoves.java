package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMoves {
    private final HashSet<ChessMove> myMoves = new HashSet<>();

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        validMove(myPosition.getRow() + 1, myPosition.getColumn() - 1, board, myPosition); // Move 1: down-left
        validMove(myPosition.getRow() + 1, myPosition.getColumn(), board, myPosition);     // Move 2: down
        validMove(myPosition.getRow() - 1, myPosition.getColumn() - 1, board, myPosition); // Move 3: up-left
        validMove(myPosition.getRow(), myPosition.getColumn() - 1, board, myPosition);     // Move 4: left
        validMove(myPosition.getRow(), myPosition.getColumn() + 1, board, myPosition);     // Move 5: right
        validMove(myPosition.getRow() - 1, myPosition.getColumn(), board, myPosition);     // Move 6: up
        validMove(myPosition.getRow() + 1, myPosition.getColumn() + 1, board, myPosition); // Move 7: down-right
        validMove(myPosition.getRow() - 1, myPosition.getColumn() + 1, board, myPosition); // Move 8: up-right
        return myMoves;
    }

    private void validMove(int row, int col, ChessBoard board, ChessPosition myPosition) {
        if (row < 9 && row > 0 && col < 9 && col > 0) {
            if (board.getPiece(new ChessPosition(row, col)) == null || board.getPiece(new ChessPosition(row, col)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                //add piece to board
                ChessPosition futurePosition = new ChessPosition(row, col);
                ChessMove addMove = new ChessMove(myPosition, futurePosition, null);
                myMoves.add(addMove);
            }
        }
    }
}
