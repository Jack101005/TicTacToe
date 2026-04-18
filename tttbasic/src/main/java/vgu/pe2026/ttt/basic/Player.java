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

                // NEW in v0.4: if the user types exactly "q", end the game immediately
                if (input.equals("q")) {
                    System.out.println("End of the game");
                    System.exit(0);
                }

                // Try to parse the input as an integer
                int move;
                try {
                    move = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    // Anything that is not an integer and not "q" -> error
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