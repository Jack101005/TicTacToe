package vgu.pe2026.ttt.basic;

import java.util.Scanner;

public class Player {
    private String name;
    private int number;
    private boolean isHuman;
    private Scanner scanner;

    public Player(String name, int number, boolean isHuman) {
        this.name = name;
        this.number = number;
        this.isHuman = isHuman;
        if (isHuman) this.scanner = new Scanner(System.in);
    }

    public int getMove(Board board) {
        if (isHuman) {
            while (true) {
                System.out.print(name + ", enter your move (1-9): ");
                int move = scanner.nextInt() - 1;
                if (move >= 0 && move <= 8 && !board.isOccupied(move)) return move;
                System.out.println("Invalid move! Try again.");
            }
        } else {
            return board.getFirstEmpty();
        }
    }

    public String getName() { return name; }
    public int getNumber() { return number; }
    public boolean isHuman() { return isHuman; }
}
