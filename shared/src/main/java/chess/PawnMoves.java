package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class PawnMoves {
    private LinkedHashSet<ChessMove> myMoves = new LinkedHashSet<ChessMove>();

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor) {
        //return a collection of chessMoves
        if (firstMove(board, myPosition, teamColor)) {
            return myMoves;
        }

        return myMoves;
    }

    private boolean firstMove(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            if (myPosition.getRow() == 2) {
                validMove(myPosition.getRow() + 1, myPosition.getColumn(), board, teamColor);
                validMove(myPosition.getRow() + 2, myPosition.getColumn(), board, teamColor);
                return true;
            }
        } else if (teamColor == ChessGame.TeamColor.BLACK) {
            if (myPosition.getRow() == 7) {
                return true;
            }
        }
        return false;
    }

    private void validMove(int row, int col, ChessBoard board, ChessGame.TeamColor teamColor) {
        if (board.getPiece(new ChessPosition(row, col)) == null && row < 9 && row > 0 && col < 9 && col > 0) {
            //add piece to board
            //myMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(row, col),null);
        }
    }
}
