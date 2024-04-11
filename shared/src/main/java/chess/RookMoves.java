package chess;

import java.util.Collection;
import java.util.HashSet;

public class RookMoves {
    private final HashSet<ChessMove> myMoves = new HashSet<>();

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        LineCheck.lineCheck(myPosition, board, myMoves);
        return myMoves;
    }


}
