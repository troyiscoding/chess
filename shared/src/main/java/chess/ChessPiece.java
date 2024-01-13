package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
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
        Collection<ChessMove> moves = new ArrayList<>();
        if (this.type == PieceType.PAWN) {
            if (this.pieceColor == ChessGame.TeamColor.WHITE) {
                if (myPosition.getRow() == 2) {
                    ChessPosition position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
                    ChessPosition position2 = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn());
                    ChessPosition position3 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                    ChessPosition position4 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                    if (board.getPiece(position) == null) {
                        moves.add(new ChessMove(myPosition, position, null));
                    }
                    if (board.getPiece(position2) == null) {
                        moves.add(new ChessMove(myPosition, position2, null));
                    }
                    if (board.getPiece(position3) != null) {
                        if (board.getPiece(position3).getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, position3, null));
                        }
                    }
                    if (board.getPiece(position4) != null) {
                        if (board.getPiece(position4).getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, position4, null));
                        }
                    }
                } else {
                    ChessPosition position = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
                    ChessPosition position2 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                    ChessPosition position3 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                    if (board.getPiece(position) == null) {
                        moves.add(new ChessMove(myPosition, position, null));
                    }
                    if (board.getPiece(position2) != null) {
                        if (board.getPiece(position2).getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, position2, null));
                        }
                    }
                    if (board.getPiece(position3) != null) {
                        if (board.getPiece(position3).getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, position3, null));
                        }
                    }
                }
            }
        }
        return moves;
    }
}
