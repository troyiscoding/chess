package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import chess.*;

import static ui.EscapeSequences.*;

public class DrawBoardNew {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static boolean printWhiteBackground;

    public static void drawBoardNew(ChessBoard board, ChessGame.TeamColor teamColor) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        drawHeaders(out);
        if (teamColor == ChessGame.TeamColor.WHITE) {
            drawBoardWhite(out, board);
        } else {
            drawBoardBlack(out, board);
        }
        drawHeaders(out);
    }

    private static void drawHeaders(PrintStream out) {
        out.print(SET_TEXT_BOLD);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("  ");
        for (int i = 0; i < BOARD_SIZE_IN_SQUARES; i++) {
            out.print(" " + (char) ('A' + i) + "\u2001");
        }
        out.println();
    }

    private static void drawBoardWhite(PrintStream out, ChessBoard board) {
        for (int i = BOARD_SIZE_IN_SQUARES - 1; i >= 0; i--) {
            out.print(SET_TEXT_COLOR_WHITE + (i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE_IN_SQUARES; j++) {
                if ((i + j) % 2 == 0) {
                    out.print(SET_BG_COLOR_DARK_GREY);
                } else {
                    out.print(SET_BG_COLOR_LIGHT_GREY);
                }
                printHelper(i, j, out, board);
            }
            out.print(INTILIJ_HACK);
            out.print(SET_TEXT_COLOR_WHITE + " " + (i + 1) + " ");
            out.println();
        }
    }

    private static void drawBoardBlack(PrintStream out, ChessBoard board) {
        //out.print(RESET_TEXT_BOLD_FAINT);
        printWhiteBackground = false;
        for (int i = 0; i < BOARD_SIZE_IN_SQUARES; i++) {
            out.print(SET_TEXT_COLOR_WHITE + (i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE_IN_SQUARES; j++) {
                if ((i + j) % 2 == 0) {
                    out.print(SET_BG_COLOR_LIGHT_GREY);
                } else {
                    out.print(SET_BG_COLOR_DARK_GREY);
                }
                printHelper(i, j, out, board);
            }
            out.print(INTILIJ_HACK);
            out.print(SET_TEXT_COLOR_WHITE + " " + (i + 1) + " ");
            out.println();
        }
    }

    private static void printHelper(int i, int j, PrintStream out, ChessBoard board) {
        printWhiteBackground = !printWhiteBackground;
        ChessPiece piece = board.squares[i][j];
        if (piece != null) {
            if (piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_BLUE);
                out.print(WHITE_PAWN);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                out.print(SET_TEXT_COLOR_RED);
                out.print(BLACK_PAWN);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.ROOK && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_BLUE);
                out.print(WHITE_ROOK);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.ROOK && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                out.print(SET_TEXT_COLOR_RED);
                out.print(BLACK_ROOK);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_BLUE);
                out.print(WHITE_KNIGHT);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                out.print(SET_TEXT_COLOR_RED);
                out.print(BLACK_KNIGHT);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.BISHOP && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_BLUE);
                out.print(WHITE_BISHOP);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.BISHOP && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                out.print(SET_TEXT_COLOR_RED);
                out.print(BLACK_BISHOP);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.QUEEN && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_BLUE);
                out.print(WHITE_QUEEN);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.QUEEN && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                out.print(SET_TEXT_COLOR_RED);
                out.print(BLACK_QUEEN);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_BLUE);
                out.print(WHITE_KING);
            }
            if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                out.print(SET_TEXT_COLOR_RED);
                out.print(BLACK_KING);
            }
        } else {
            out.print("\u2001"); // print empty square
            out.print("  ");
        }
    }
}
