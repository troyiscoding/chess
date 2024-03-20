package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class DrawChessBoard {
    private static final int BOARD_SIZE_IN_SQUARES = 8;

    public static void main(String[] args) {
        drawChessBoard();
    }

    public static void drawChessBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        drawHeaders(out);
        drawBlackTop(out);
        drawBox(out);
        drawWhiteBottom(out);
        drawHeaders(out);

        out.println();
        drawHeaders(out);
        drawWhiteTop(out);
        drawBox(out);
        drawBlackBottom(out);
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

    private static void drawBlackTop(PrintStream out) {
        out.print(SET_TEXT_COLOR_WHITE + (1) + " ");
        out.print(SET_TEXT_COLOR_RED);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_ROOK);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_KNIGHT);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_BISHOP);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_QUEEN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_KING);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_BISHOP);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_KNIGHT);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_ROOK);
        out.print(INTILIJ_HACK);
        out.print(SET_TEXT_COLOR_WHITE + " " + (1) + " ");
        out.println();
        out.print(SET_TEXT_COLOR_WHITE + (2) + " ");
        out.print(SET_TEXT_COLOR_RED);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_PAWN);
        out.print(INTILIJ_HACK);
        out.print(SET_TEXT_COLOR_WHITE + " " + (2) + " ");
        out.println();
    }

    private static void drawWhiteBottom(PrintStream out) {
        out.print(SET_TEXT_COLOR_WHITE + (7) + " ");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_PAWN);
        out.print(INTILIJ_HACK);
        out.print(SET_TEXT_COLOR_WHITE + " " + (7) + " ");
        out.println();
        out.print(SET_TEXT_COLOR_WHITE + (8) + " ");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_ROOK);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_KNIGHT);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_BISHOP);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_QUEEN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_KING);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_BISHOP);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(WHITE_KNIGHT);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(WHITE_ROOK);
        out.print(INTILIJ_HACK);
        out.print(SET_TEXT_COLOR_WHITE + " " + (8) + " ");
        out.println();
    }

    private static void drawWhiteTop(PrintStream out) {
        out.print(RESET_TEXT_BOLD_FAINT);
        out.print(SET_TEXT_COLOR_WHITE + (1) + " ");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_ROOK);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_KNIGHT);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_BISHOP);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_QUEEN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_KING);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_BISHOP);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_KNIGHT);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_ROOK);
        out.print(INTILIJ_HACK);
        out.print(SET_TEXT_COLOR_WHITE + " " + (1) + " ");
        out.println();
        out.print(SET_TEXT_COLOR_WHITE + (2) + " ");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_PAWN);
        out.print(INTILIJ_HACK);
        out.print(SET_TEXT_COLOR_WHITE + " " + (1) + " ");
        out.println();
    }

    private static void drawBlackBottom(PrintStream out) {
        out.print(SET_TEXT_COLOR_WHITE + (7) + " ");
        out.print(SET_TEXT_COLOR_RED);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_PAWN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_PAWN);
        out.print(INTILIJ_HACK);
        out.print(SET_TEXT_COLOR_WHITE + " " + (7) + " ");
        out.println();
        out.print(SET_TEXT_COLOR_WHITE + (8) + " ");
        out.print(SET_TEXT_COLOR_RED);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_ROOK);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_KNIGHT);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_BISHOP);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_QUEEN);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_KING);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_BISHOP);
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(BLACK_KNIGHT);
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(BLACK_ROOK);
        out.print(INTILIJ_HACK);
        out.print(SET_TEXT_COLOR_WHITE + " " + (8) + " ");
        out.println();
    }

    private static void drawBox(PrintStream out) {
        for (int i = 2; i < BOARD_SIZE_IN_SQUARES - 2; i++) {
            out.print(SET_TEXT_COLOR_WHITE + (i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE_IN_SQUARES; j++) {
                if ((i + j) % 2 == 0) {
                    out.print(SET_BG_COLOR_LIGHT_GREY);
                    out.print("\u2001");
                } else {
                    out.print(SET_BG_COLOR_DARK_GREY);
                    out.print("\u2001");
                }
                out.print("  ");
            }
            out.print(INTILIJ_HACK);
            out.print(SET_TEXT_COLOR_WHITE + " " + (i) + " ");
            out.println();
        }
    }
}
