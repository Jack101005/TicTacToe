package vgu.pe2026.ttt.basic;
 
public class App {
    public static void main(String[] args) {
        // If no argument is passed, or the argument is not "1" or "2", show error and stop
        if (args.length == 0 || (!args[0].equals("1") && !args[0].equals("2"))) {
            System.out.println("Please, input a valid option [1-2]");
            return;
        }
        int starter = Integer.parseInt(args[0]);
        Game game = new Game(starter);
        game.start();
    }
}
 