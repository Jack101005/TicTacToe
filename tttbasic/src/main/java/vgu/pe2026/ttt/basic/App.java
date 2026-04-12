package vgu.pe2026.ttt.basic;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java App <1|2> (1 = human starts, 2 = computer starts)");
            return;
        }
        int starter = Integer.parseInt(args[0]);
        Game game = new Game(starter);
        game.start();
    }
}