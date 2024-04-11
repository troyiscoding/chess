package chess;

import java.util.Collection;

public class LineCheck {
    public static void lineCheck(ChessPosition myPosition, ChessBoard board, Collection<ChessMove> myMoves) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //Move up
        for (int i = currentRow + 1; i < 9; i++) {
            if (ValidMove.validMove(i, currentCol, board, myPosition, myMoves)) break;
        }
        //Move down
        for (int i = currentRow - 1; i > 0; i--) {
            if (ValidMove.validMove(i, currentCol, board, myPosition, myMoves)) break;
        }
        //Move left
        for (int i = currentCol - 1; i > 0; i--) {
            if (ValidMove.validMove(currentRow, i, board, myPosition, myMoves)) break;
        }
        //Move right
        for (int i = currentCol + 1; i < 9; i++) {
            if (ValidMove.validMove(currentRow, i, board, myPosition, myMoves)) break;
        }
    }
}
