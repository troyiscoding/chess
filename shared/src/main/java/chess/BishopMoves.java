package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoves {

    private HashSet<ChessMove> myMoves = new HashSet<ChessMove>();

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //Move down-left
        for (int i = currentRow - 1, j = currentCol - 1; i > 0 && j > 0; i--, j--) {
            if (!validMove(i, j, board, myPosition)) break;
        }
        //Move down-right
        for (int i = currentRow - 1, j = currentCol + 1; i > 0 && j < 9; i--, j++) {
            if (!validMove(i, j, board, myPosition)) break;
        }
        //Move up-left
        for (int i = currentRow + 1, j = currentCol - 1; i < 9 && j > 0; i++, j--) {
            if (!validMove(i, j, board, myPosition)) break;
        }
        //Move up-right
        for (int i = currentRow + 1, j = currentCol + 1; i < 9 && j < 9; i++, j++) {
            if (!validMove(i, j, board, myPosition)) break;
        }
        //
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
                return destinationPiece == null;
            } else {
                return false;
            }
        }
        return false;
    }
}
