package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import chess.*;

import static ui.EscapeSequences.*;

public class DrawBoardNew {

    private static final int BOARD_SIZE_IN_SQUARES = 8;

    public static void drawBoardNew() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);


    }
}
