package vgu.pe2026.ttt.basic;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;

    public Game(int starter) {
        board = new Board();
        Player human = new Player("Human", 1, true);
        Player computer = new Player("Computer", 2, false);
        player1 = (starter == 1) ? human : computer;
        player2 = (starter == 1) ? computer : human;
    }

    public void start() {
        System.out.println("Tic-Tac-Toe! Human = 1, Computer = 2");
        board.display();

        Player[] players = {player1, player2};
        int turn = 0;

        while (true) {
            Player current = players[turn % 2];
            if (!current.isHuman()) System.out.println("Computer's turn:");

            int move = current.getMove(board);
            board.setCell(move, current.getNumber());
            board.display();

            if (board.checkWin(current.getNumber())) {
                System.out.println(current.getName() + " wins!");
                break;
            }
            if (board.isFull()) {
                System.out.println("It's a draw!");
                break;
            }
            turn++;
        }
    }
}
