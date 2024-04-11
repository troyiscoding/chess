package ui;

import java.util.Scanner;

public class PreLoginRepl {
    //This class is responsible for the Read-Eval-Print-Loop (REPL) for the pre-login state.
    //READ - Read input from the user
    //EVAL - Evaluate the input and determine the appropriate action
    //PRINT - Print the result of the action
    //LOOP - Repeat the process until the user quits

    private final PreLogin client;

    public PreLoginRepl(String serverUrl) {
        client = new PreLogin(serverUrl);
    }

    public void run() {
        //System.out.print(client.helpLogin());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit") && client.state == LoginState.SIGNED_OUT) {
            System.out.print("[" + LoginState.SIGNED_OUT + "]" + " >>> ");
            result = scanner.nextLine(); //READ
            String print = client.eval(result); //EVAL
            System.out.print(print); //PRINT
            System.out.println();
        }
        scanner.close();
    }
}
