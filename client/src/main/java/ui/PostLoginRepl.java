package ui;

import java.util.Scanner;

public class PostLoginRepl {
    //This class is responsible for the Read-Eval-Print-Loop (REPL) for the pre-login state.
    //READ - Read input from the user
    //EVAL - Evaluate the input and determine the appropriate action
    //PRINT - Print the result of the action
    //LOOP - Repeat the process until the user quits

    private final PostLogin client;

    public PostLoginRepl(String serverUrl, LoginState state, String authToken) {
        client = new PostLogin(serverUrl, state, authToken);
        client.states = state;
    }

    public void run() {

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit") && client.states == LoginState.SIGNED_IN) {
            System.out.print("[" + client.states + "]" + " >>> "); //Print out Current State and Make Text Entry
            result = scanner.nextLine(); //READ
            String Print = client.eval(result); //EVAL
            System.out.print(Print); //PRINT
            System.out.println();
        }
        scanner.close();
    }
}
