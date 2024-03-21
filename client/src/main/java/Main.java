import ui.PreLoginRepl;

public class Main {
    public static void main(String[] args) {
        System.out.println("♕ Welcome to 240 chess. Type Help to get started. ♕");
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new PreLoginRepl(serverUrl).run();
    }
}