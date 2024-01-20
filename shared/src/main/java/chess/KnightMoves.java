package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightMoves {
    private final HashSet<ChessMove> myMoves = new HashSet<>();

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //left 2 up 1
        validMove(currentRow + 1, currentCol - 2, board, myPosition);
        //left 2 down 1
        validMove(currentRow - 1, currentCol - 2, board, myPosition);
        //left 1 up 2
        validMove(currentRow + 2, currentCol - 1, board, myPosition);
        //left 1 down 2
        validMove(currentRow - 2, currentCol - 1, board, myPosition);
        //right 2 up 1
        validMove(currentRow + 1, currentCol + 2, board, myPosition);
        //right 2 down 1
        validMove(currentRow - 1, currentCol + 2, board, myPosition);
        //right 1 up 2
        validMove(currentRow + 2, currentCol + 1, board, myPosition);
        //right 1 down 2
        validMove(currentRow - 2, currentCol + 1, board, myPosition);
        return myMoves;
    }

    private void validMove(int row, int col, ChessBoard board, ChessPosition myPosition) {
        if (row < 9 && row > 0 && col < 9 && col > 0) {
            ChessPiece destinationPiece = board.getPiece(new ChessPosition(row, col));
            if (destinationPiece == null || destinationPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                //Add piece to board if it is empty or an enemy piece
                ChessPosition futurePosition = new ChessPosition(row, col);
                ChessMove addMove = new ChessMove(myPosition, futurePosition, null);
                myMoves.add(addMove);
            }
        }
    }
}
