package ui;

import java.util.Scanner;

public class GamePlayRepl {
    //This class is responsible for the Read-Eval-Print-Loop (REPL) for the gameplay state.
    //READ - Read input from the user
    //EVAL - Evaluate the input and determine the appropriate action
    //PRINT - Print the result of the action
    //LOOP - Repeat the process until the user quits

    private final GamePlay game;

    public GamePlayRepl(String serverURL, String token, int gameID, boolean observer) {
        game = new GamePlay(serverURL, token, gameID, observer);
    }

    public String run() {
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("leave")) {
            System.out.print("[" + "IN_GAME" + "]" + " >>> ");
            result = scanner.nextLine(); //READ
            String print = game.eval(result); //EVAL
            System.out.print(print); //PRINT
            System.out.println();
        }
        scanner.close();
        return "";
    }
}
