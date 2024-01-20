package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        if (ChessPiece.PieceType.PAWN == promotionPiece) {
            return promotionPiece;
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessMove chessMove = (ChessMove) o;

        if (!Objects.equals(startPosition, chessMove.startPosition))
            return false;
        if (!Objects.equals(endPosition, chessMove.endPosition))
            return false;
        return promotionPiece == chessMove.promotionPiece;
    }

    @Override
    public int hashCode() {
        int result = startPosition != null ? startPosition.hashCode() : 0;
        result = 31 * result + (endPosition != null ? endPosition.hashCode() : 0);
        result = 31 * result + (promotionPiece != null ? promotionPiece.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChessMove{" +
                "startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", promotionPiece=" + promotionPiece +
                '}';
    }
}

