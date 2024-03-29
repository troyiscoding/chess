package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private boolean hasMoved = false;
    private boolean hasMovedTwo = false;
    private boolean enPass = false;
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public boolean getHasMovedTwo() {
        return hasMovedTwo;
    }

    public void setHasMovedTwo(boolean hasMovedTwo) {
        this.hasMovedTwo = hasMovedTwo;
    }

    public boolean isEnPass() {
        return enPass;
    }

    public void setEnPass(boolean enPass) {
        this.enPass = enPass;
    }


    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        var piece = board.getPiece(myPosition);
        switch (piece.getPieceType()) {
            case KING:
                return new KingMoves().kingMoves(board, myPosition);
            case QUEEN:
                return new QueenMoves().queenMoves(board, myPosition);
            case BISHOP:
                return new BishopMoves().bishopMoves(board, myPosition);
            case KNIGHT:
                return new KnightMoves().knightMoves(board, myPosition);
            case ROOK:
                return new RookMoves().rookMoves(board, myPosition);
            case PAWN:
                return new PawnMoves().pawnMoves(board, myPosition);
            default:
                throw new RuntimeException("Error: Look at your pieceMoves");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessPiece that = (ChessPiece) o;

        if (pieceColor != that.pieceColor) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = pieceColor != null ? pieceColor.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}

