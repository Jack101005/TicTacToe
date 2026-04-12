package vgu.pe2026.ttt.basic;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;

    public Game(int starter) {
        board = new Board();
        // Players are now identified by number only, not by name "Human"/"Computer"
        Player human = new Player("Player#1", 1, true);
        Player computer = new Player("Player#2", 2, false);
        player1 = (starter == 1) ? human : computer;
        player2 = (starter == 1) ? computer : human;
    }

    public void start() {
        // Welcoming message required by v0.3
        System.out.println("Hello!");

        // Show the initial board (all 0s) before any move
        board.display();

        Player[] players = {player1, player2};
        int turn = 0;

        while (true) {
            Player current = players[turn % 2];

            // Print whose turn it is before every move
            System.out.println(current.getName() + "'s turn");

            int move = current.getMove(board, current.getName());
            board.setCell(move, current.getNumber());
            board.display();

            if (board.checkWin(current.getNumber())) {
                System.out.println(current.getName() + " won!");
                break;
            }
            if (board.isFull()) {
                System.out.println("It is a draw!");
                break;
            }
            turn++;
        }
    }
}