package chess;

import java.util.Collection;

public class CheckCross {
    public static void crossCheck(ChessPosition myPosition, ChessBoard board, Collection<ChessMove> myMoves) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //Move down-left
        for (int i = currentRow - 1, j = currentCol - 1; i > 0 && j > 0; i--, j--) {
            if (ValidMove.validMove(i, j, board, myPosition, myMoves)) break;
        }
        //Move down-right
        for (int i = currentRow - 1, j = currentCol + 1; i > 0 && j < 9; i--, j++) {
            if (ValidMove.validMove(i, j, board, myPosition, myMoves)) break;
        }
        //Move up-left
        for (int i = currentRow + 1, j = currentCol - 1; i < 9 && j > 0; i++, j--) {
            if (ValidMove.validMove(i, j, board, myPosition, myMoves)) break;
        }
        //Move up-right
        for (int i = currentRow + 1, j = currentCol + 1; i < 9 && j < 9; i++, j++) {
            if (ValidMove.validMove(i, j, board, myPosition, myMoves)) break;
        }
    }
}
