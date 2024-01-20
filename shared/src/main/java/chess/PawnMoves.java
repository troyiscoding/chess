package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMoves {
    private final HashSet<ChessMove> myMoves = new HashSet<>();

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        boolean isWhite = board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE;
        if (isWhite) {
            boolean isBlocked = validMoveForward(currentRow + 1, currentCol, board, myPosition);
            //Check if pawn is in starting position
            if (currentRow == 2 && !isBlocked) {
                validMoveForward(currentRow + 2, currentCol, board, myPosition);
            }
            ChessPiece takeRight = board.getPiece(new ChessPosition(currentRow + 1, currentCol + 1));
            if (takeRight != null && takeRight.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMove(currentRow + 1, currentCol + 1, board, myPosition);
            }
            ChessPiece takeLeft = board.getPiece(new ChessPosition(currentRow + 1, currentCol - 1));
            if (takeLeft != null && takeLeft.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMove(currentRow + 1, currentCol - 1, board, myPosition);
            }

        } else {
            boolean isBlocked = validMoveForward(currentRow - 1, currentCol, board, myPosition);
            //Check if pawn is in starting position

            if (currentRow == 7 && !isBlocked) {
                validMoveForward(currentRow - 2, currentCol, board, myPosition);
            }
            ChessPiece takeRight = board.getPiece(new ChessPosition(currentRow - 1, currentCol + 1));
            if (takeRight != null && takeRight.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMove(currentRow - 1, currentCol + 1, board, myPosition);
            }
            ChessPiece takeLeft = board.getPiece(new ChessPosition(currentRow - 1, currentCol - 1));
            if (takeLeft != null && takeLeft.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMove(currentRow - 1, currentCol - 1, board, myPosition);
            }
        }
        return myMoves;
    }

    private void validMove(int row, int col, ChessBoard board, ChessPosition myPosition) {
        if (row < 9 && row > 0 && col < 9 && col > 0) {
            ChessPiece destinationPiece = board.getPiece(new ChessPosition(row, col));
            if (destinationPiece == null || destinationPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                //Add piece to board if it is empty or an enemy piece
                ChessPosition futurePosition = new ChessPosition(row, col);
                if (row == 8 || row == 1) {
                    promotion(myPosition, futurePosition);
                } else {
                    ChessMove addMove = new ChessMove(myPosition, futurePosition, null);
                    myMoves.add(addMove);
                }
            }
        }
    }

    private boolean validMoveForward(int row, int col, ChessBoard board, ChessPosition myPosition) {
        if (row < 9 && row > 0 && col < 9 && col > 0) {
            ChessPiece destinationPiece = board.getPiece(new ChessPosition(row, col));
            if (destinationPiece == null) {
                //Add piece to board if it is empty or an enemy piece
                ChessPosition futurePosition = new ChessPosition(row, col);
                if (row == 8 || row == 1) {
                    promotion(myPosition, futurePosition);
                    return false;
                } else {
                    ChessMove addMove = new ChessMove(myPosition, futurePosition, null);
                    myMoves.add(addMove);
                    return false;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    private void promotion(ChessPosition myPosition, ChessPosition endPosition) {
        ChessPiece.PieceType[] promotionPiece = new ChessPiece.PieceType[]{ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.ROOK, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT};
        for (ChessPiece.PieceType piece : promotionPiece) {
            ChessMove addMove = new ChessMove(myPosition, endPosition, piece);
            myMoves.add(addMove);
        }
    }
}
