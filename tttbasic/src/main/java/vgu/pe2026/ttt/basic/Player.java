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

    public int getMove(Board board, String playerName) {
        if (isHuman) {
            while (true) {
                String input = scanner.nextLine().trim();

                int move;
                try {
                    move = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    // Non-integer input: show error then re-print whose turn it is
                    System.out.println("Please, input a valid number [1-9]");
                    System.out.println(playerName + "'s turn");
                    continue;
                }

                // Number is outside [1-9] range
                if (move < 1 || move > 9) {
                    System.out.println("Please, input a valid number [1-9]");
                    System.out.println(playerName + "'s turn");
                    continue;
                }

                int index = move - 1;

                // Cell is already taken
                if (board.isOccupied(index)) {
                    System.out.println("The cell is occupied!");
                    System.out.println(playerName + "'s turn");
                    continue;
                }

                return index;
            }
        } else {
            // Computer always picks the first empty cell
            return board.getFirstEmpty();
        }
    }

    public String getName() { return name; }
    public int getNumber() { return number; }
    public boolean isHuman() { return isHuman; }
}