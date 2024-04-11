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
}
