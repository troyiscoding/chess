package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoves {

    private final HashSet<ChessMove> myMoves = new HashSet<>();

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        CheckCross.crossCheck(myPosition, board, myMoves);
        return myMoves;
    }
}
