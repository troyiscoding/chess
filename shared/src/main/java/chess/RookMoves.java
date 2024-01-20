package chess;

import java.util.Collection;
import java.util.HashSet;

public class RookMoves {
    private final HashSet<ChessMove> myMoves = new HashSet<>();

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //Move up
        for (int i = currentRow + 1; i < 9; i++) {
            if (validMove(i, currentCol, board, myPosition)) break;
        }
        //Move down
        for (int i = currentRow - 1; i > 0; i--) {
            if (validMove(i, currentCol, board, myPosition)) break;
        }
        //Move left
        for (int i = currentCol - 1; i > 0; i--) {
            if (validMove(currentRow, i, board, myPosition)) break;
        }
        //Move right
        for (int i = currentCol + 1; i < 9; i++) {
            if (validMove(currentRow, i, board, myPosition)) {
                break;
            }
        }
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
