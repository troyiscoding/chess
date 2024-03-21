import ui.PreLoginRepl;
//import server.Server;

public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        //new Server().run(8080);

        System.out.println("♕ Welcome to 240 chess. Type Help to get started. ♕");
        new PreLoginRepl(serverUrl).run();
    }
}