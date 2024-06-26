package chess;

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
    private TeamColor teamTurn = TeamColor.WHITE;
    public ChessBoard board = new ChessBoard();
    private boolean enPassant = false;
    private boolean castling = false;
    public boolean isOver = false;


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
            Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);
            Collection<ChessMove> validMoves = new HashSet<>();
            for (ChessMove move : moves) {
                ChessGame testGame = deepCopy();
                ChessPiece testPiece = testGame.board.getPiece(move.getStartPosition());
                testGame.board.addPiece(move.getStartPosition(), null);
                testGame.board.addPiece(move.getEndPosition(), testPiece);
                if (!testGame.isInCheck(testPiece.getTeamColor())) {
                    validMoves.add(move);
                }
            }
            //En Croissant
            addEnPassantMoves(piece, startPosition, validMoves, board);
            //Castling
            addCastlingMoves(piece, startPosition, validMoves, board);
            return validMoves;
        }
    }

    private void addEnPassantMoves(ChessPiece piece, ChessPosition startPosition, Collection<ChessMove> validMoves, ChessBoard board) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece currentPiece = board.getPiece(position);
                if (currentPiece != null) {
                    if ((currentPiece.getTeamColor() != piece.getTeamColor()) && (currentPiece.getPieceType() == ChessPiece.PieceType.PAWN) && (piece.getPieceType() == ChessPiece.PieceType.PAWN)) {
                        if (currentPiece.getHasMovedTwo() && enPassant) {
                            if (currentPiece.getTeamColor() == TeamColor.WHITE) {
                                if (startPosition.getRow() == 4 && (startPosition.getColumn() == j + 1 || startPosition.getColumn() == j - 1)) {
                                    validMoves.add(new ChessMove(startPosition, new ChessPosition(position.getRow() - 1, position.getColumn()), null));
                                    piece.setEnPass(true);
                                }
                            } else {
                                if (startPosition.getRow() == 5 && (startPosition.getColumn() == j + 1 || startPosition.getColumn() == j - 1)) {
                                    validMoves.add(new ChessMove(startPosition, new ChessPosition(position.getRow() + 1, position.getColumn()), null));
                                    piece.setEnPass(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addCastlingMoves(ChessPiece piece, ChessPosition startPosition, Collection<ChessMove> validMoves, ChessBoard board) {
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            if (piece.getTeamColor() == TeamColor.WHITE && board.getPiece(new ChessPosition(1, 5)) != null && board.getPiece(new ChessPosition(1, 5)).getPieceType() == ChessPiece.PieceType.KING) {
                if (!piece.getHasMoved() && !isInCheck(TeamColor.WHITE) && !board.getPiece(new ChessPosition(1, 1)).getHasMoved() && board.getPiece(new ChessPosition(1, 1)).getPieceType() == ChessPiece.PieceType.ROOK) {
                    if (board.getPiece(new ChessPosition(1, 1)) != null) {
                        if (board.getPiece(new ChessPosition(1, 1)).getPieceType() == ChessPiece.PieceType.ROOK) {
                            if (!board.getPiece(new ChessPosition(1, 1)).getHasMoved()) {
                                if (board.getPiece(new ChessPosition(1, 2)) == null && board.getPiece(new ChessPosition(1, 3)) == null && board.getPiece(new ChessPosition(1, 4)) == null) {
                                    if (!isInCheck(TeamColor.WHITE)) {
                                        ChessGame testGame = deepCopy();
                                        ChessPiece kingPiece = testGame.board.getPiece(new ChessPosition(1, 5));
                                        testGame.board.addPiece(new ChessPosition(1, 5), null);
                                        testGame.board.addPiece(new ChessPosition(1, 1), null);
                                        testGame.board.addPiece(new ChessPosition(1, 3), kingPiece);
                                        testGame.board.addPiece(new ChessPosition(1, 4), kingPiece);
                                        if (!testGame.isInCheck(kingPiece.getTeamColor())) {
                                            validMoves.add(new ChessMove(startPosition, new ChessPosition(1, 3), null));
                                            castling = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!piece.getHasMoved() && !isInCheck(TeamColor.WHITE) && !board.getPiece(new ChessPosition(1, 8)).getHasMoved() && board.getPiece(new ChessPosition(1, 8)).getPieceType() == ChessPiece.PieceType.ROOK) {
                    if (board.getPiece(new ChessPosition(1, 8)) != null) {
                        if (board.getPiece(new ChessPosition(1, 8)).getPieceType() == ChessPiece.PieceType.ROOK) {
                            if (!board.getPiece(new ChessPosition(1, 8)).getHasMoved()) {
                                if (board.getPiece(new ChessPosition(1, 7)) == null && board.getPiece(new ChessPosition(1, 6)) == null) {
                                    if (!isInCheck(TeamColor.WHITE)) {
                                        ChessGame testGame = deepCopy();
                                        ChessPiece kingPiece = testGame.board.getPiece(new ChessPosition(1, 5));
                                        testGame.board.addPiece(new ChessPosition(1, 5), null);
                                        testGame.board.addPiece(new ChessPosition(1, 8), null);
                                        testGame.board.addPiece(new ChessPosition(1, 7), kingPiece);
                                        testGame.board.addPiece(new ChessPosition(1, 6), kingPiece);
                                        if (!testGame.isInCheck(kingPiece.getTeamColor())) {
                                            validMoves.add(new ChessMove(startPosition, new ChessPosition(1, 7), null));
                                            castling = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (!piece.getHasMoved() && !isInCheck(TeamColor.BLACK) && board.getPiece(new ChessPosition(8, 5)) != null && board.getPiece(new ChessPosition(8, 5)).getPieceType() == ChessPiece.PieceType.KING) {
                    if (board.getPiece(new ChessPosition(8, 1)) != null) {
                        if (board.getPiece(new ChessPosition(8, 1)).getPieceType() == ChessPiece.PieceType.ROOK) {
                            if (!board.getPiece(new ChessPosition(8, 1)).getHasMoved()) {
                                if (board.getPiece(new ChessPosition(8, 2)) == null && board.getPiece(new ChessPosition(8, 3)) == null && board.getPiece(new ChessPosition(8, 4)) == null) {
                                    if (!isInCheck(TeamColor.BLACK)) {
                                        ChessGame testGame = deepCopy();
                                        ChessPiece kingPiece = testGame.board.getPiece(new ChessPosition(8, 5));
                                        testGame.board.addPiece(new ChessPosition(8, 5), null);
                                        testGame.board.addPiece(new ChessPosition(8, 1), null);
                                        testGame.board.addPiece(new ChessPosition(8, 3), kingPiece);
                                        testGame.board.addPiece(new ChessPosition(8, 4), kingPiece);
                                        if (!testGame.isInCheck(kingPiece.getTeamColor())) {
                                            validMoves.add(new ChessMove(startPosition, new ChessPosition(8, 3), null));
                                            castling = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!piece.getHasMoved() && !isInCheck(TeamColor.BLACK)) {
                    if (board.getPiece(new ChessPosition(8, 8)) != null) {
                        if (board.getPiece(new ChessPosition(8, 8)).getPieceType() == ChessPiece.PieceType.ROOK) {
                            if (!board.getPiece(new ChessPosition(8, 8)).getHasMoved()) {
                                if (board.getPiece(new ChessPosition(8, 7)) == null && board.getPiece(new ChessPosition(8, 6)) == null) {
                                    if (!isInCheck(TeamColor.BLACK)) {
                                        ChessGame testGame = deepCopy();
                                        ChessPiece kingPiece = testGame.board.getPiece(new ChessPosition(8, 5));
                                        testGame.board.addPiece(new ChessPosition(8, 5), null);
                                        testGame.board.addPiece(new ChessPosition(8, 8), null);
                                        testGame.board.addPiece(new ChessPosition(8, 7), kingPiece);
                                        testGame.board.addPiece(new ChessPosition(8, 6), kingPiece);
                                        if (!testGame.isInCheck(kingPiece.getTeamColor())) {
                                            validMoves.add(new ChessMove(startPosition, new ChessPosition(8, 7), null));
                                            castling = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
            ChessGame testGame = deepCopy();
            boolean check = false;
            for (ChessMove chessMove : chessMoves) {
                ChessPiece testPiece = testGame.board.getPiece(chessMove.getStartPosition());
                testGame.board.addPiece(chessMove.getStartPosition(), null);
                testGame.board.addPiece(chessMove.getEndPosition(), testPiece);
                if (testGame.isInCheck(testGame.teamTurn)) {
                    check = true;
                } else {
                    check = false;
                    break;
                }
                testGame.board.addPiece(chessMove.getEndPosition(), testPiece);
                testGame.board.addPiece(chessMove.getEndPosition(), null);
            }
            if (check) {
                throw new InvalidMoveException("Invalid move");
            }
            ChessPiece piece = board.getPiece(move.getStartPosition());

            if (piece.isEnPass()) {
                if (piece.getTeamColor() == TeamColor.WHITE) {
                    board.addPiece(new ChessPosition(move.getEndPosition().getRow() - 1, move.getEndPosition().getColumn()), null);
                } else {
                    board.addPiece(new ChessPosition(move.getEndPosition().getRow() + 1, move.getEndPosition().getColumn()), null);
                }
                board.addPiece(move.getStartPosition(), null);
                board.addPiece(move.getEndPosition(), piece);
                piece.setEnPass(false);
            }
            if (castling) {
                if (move.getEndPosition().getColumn() == 3) {
                    board.addPiece(new ChessPosition(move.getEndPosition().getRow(), 1), null);
                    board.addPiece(new ChessPosition(move.getEndPosition().getRow(), 4), new ChessPiece(piece.getTeamColor(), ChessPiece.PieceType.ROOK));
                } else if (move.getEndPosition().getColumn() == 7) {
                    board.addPiece(new ChessPosition(move.getEndPosition().getRow(), 8), null);
                    board.addPiece(new ChessPosition(move.getEndPosition().getRow(), 6), new ChessPiece(piece.getTeamColor(), ChessPiece.PieceType.ROOK));
                }
            }

            if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                if (move.getEndPosition().getRow() == 8 || move.getEndPosition().getRow() == 1) {
                    board.addPiece(move.getStartPosition(), null);
                    board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));

                } else {
                    board.addPiece(move.getStartPosition(), null);
                    board.addPiece(move.getEndPosition(), piece);
                    if (Math.abs(move.getStartPosition().getRow() - move.getEndPosition().getRow()) == 2) {
                        piece.setHasMovedTwo(true);
                        enPassant = true;
                    } else {
                        piece.setHasMovedTwo(false);
                        enPassant = false;
                    }
                }
            } else {
                board.addPiece(move.getStartPosition(), null);
                board.addPiece(move.getEndPosition(), piece);
                enPassant = false;
                piece.setHasMoved(true);
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
        if (isInCheck(teamColor)) {
            return false;
        } else {
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    ChessPosition position = new ChessPosition(i, j);
                    ChessPiece currentPiece = board.getPiece(position);
                    if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                        Collection<ChessMove> moves = validMoves(position);
                        for (ChessMove move : moves) {
                            ChessGame testGame = deepCopy();
                            ChessPiece testPiece = testGame.board.getPiece(move.getStartPosition());
                            testGame.board.addPiece(move.getStartPosition(), null);
                            testGame.board.addPiece(move.getEndPosition(), testPiece);
                            if (!testGame.isInCheck(teamColor)) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
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

    public ChessGame deepCopy() {
        ChessGame newGame = new ChessGame();
        newGame.setTeamTurn(this.teamTurn);
        newGame.setBoard(deepCopyBoard(this.board));
        return newGame;
    }

    private ChessBoard deepCopyBoard(ChessBoard originalBoard) {
        ChessBoard newBoard = new ChessBoard();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = originalBoard.getPiece(position);
                if (piece != null) {
                    newBoard.addPiece(position, new ChessPiece(piece.getTeamColor(), piece.getPieceType()));
                } else {
                    newBoard.addPiece(position, null);
                }
            }
        }
        return newBoard;
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
}