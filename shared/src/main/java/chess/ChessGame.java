package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    public ChessBoard board = new ChessBoard();

    public ChessGame() {
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        } else {
            return piece.pieceMoves(board, startPosition);
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        if (move.getStartPosition().getRow() < 1 || move.getStartPosition().getRow() > 8 || move.getStartPosition().getColumn() < 1 || move.getStartPosition().getColumn() > 8) {
            throw new InvalidMoveException("Out of board");
        } else if (move.getEndPosition().getRow() < 1 || move.getEndPosition().getRow() > 8 || move.getEndPosition().getColumn() < 1 || move.getEndPosition().getColumn() > 8) {
            throw new InvalidMoveException("Out of board");
        } else if (board.getPiece(move.getStartPosition()) == null) {
            throw new InvalidMoveException("No piece");
        } else if (board.getPiece(move.getStartPosition()).getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Wrong team");
        } else {
            Collection<ChessMove> chessMoves = validMoves(move.getStartPosition());
            if (chessMoves == null || !chessMoves.contains(move)) {
                throw new InvalidMoveException("Invalid move");
            }
            for (ChessMove chessMove : chessMoves) {
                ChessGame testGame = new ChessGame();
                testGame.setBoard(board);
                testGame.setTeamTurn(teamTurn);
                testGame.makeMove(chessMove);
                if (testGame.isInCheck(teamTurn)) {
                    throw new InvalidMoveException("Check");
                }
            }
            ChessPiece piece = board.getPiece(move.getStartPosition());
            if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                if (move.getEndPosition().getRow() == 8 || move.getEndPosition().getRow() == 1) {
                    board.addPiece(move.getStartPosition(), null);
                    board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));
                } else {
                    board.addPiece(move.getStartPosition(), null);
                    board.addPiece(move.getEndPosition(), piece);
                }
            } else {
                board.addPiece(move.getStartPosition(), null);
                board.addPiece(move.getEndPosition(), piece);
            }
            if (teamTurn == TeamColor.WHITE) {
                teamTurn = TeamColor.BLACK;
            } else {
                teamTurn = TeamColor.WHITE;
            }
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean b = false;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(position);
                if (currentPiece != null) {
                    if (currentPiece.getTeamColor() != teamColor) {
                        Collection<ChessMove> moves = currentPiece.pieceMoves(this.board, position);
                        for (ChessMove move : moves) {
                            if (board.getPiece(move.getEndPosition()) != null) {
                                if (board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING) {
                                    b = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return b;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean c = false;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition findKing = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(findKing);
                if (currentPiece != null) {
                    if (currentPiece.getPieceType() == ChessPiece.PieceType.KING && currentPiece.getTeamColor() == teamColor) {
                        Collection<ChessMove> moves = currentPiece.pieceMoves(this.board, findKing);
                        for (ChessMove move : moves) {
                            if (board.getPiece(move.getEndPosition()) == null) {
                                if (isInCheck(teamColor)) {
                                    c = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return c;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean d = false;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(position);
                if (currentPiece != null) {
                    if (currentPiece.getTeamColor() == teamColor) {
                        Collection<ChessMove> moves = currentPiece.pieceMoves(this.board, position);
                        if (!moves.isEmpty()) {
                            d = true;
                        }
                    }
                }
            }
        }
        return d;

    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessGame chessGame = (ChessGame) o;

        if (teamTurn != chessGame.teamTurn) return false;
        return Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        int result = teamTurn != null ? teamTurn.hashCode() : 0;
        result = 31 * result + (board != null ? board.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamTurn=" + teamTurn +
                ", board=" + board +
                '}';
    }
}