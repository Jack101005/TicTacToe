package vgu.pe2026.ttt.basic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest_3
{
    // Helper to capture stdout
    private ByteArrayOutputStream captureOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        return out;
    }

    private void restoreOutput() {
        System.setOut(System.out);
    }

    private void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    private void restoreInput() {
        System.setIn(System.in);
    }

    // No argument passed -> should print the validation message
    @Test
    public void testNoArgument() {
        ByteArrayOutputStream out = captureOutput();
        App.main(new String[]{});
        restoreOutput();
        assertTrue(out.toString().contains("Please, input a valid option [1-2]"));
    }

    // Argument is not "1" or "2" -> should print the validation message
    @Test
    public void testInvalidArgument() {
        ByteArrayOutputStream out = captureOutput();
        App.main(new String[]{"5"});
        restoreOutput();
        assertTrue(out.toString().contains("Please, input a valid option [1-2]"));
    }

    // Argument is a non-number string -> should print the validation message
    @Test
    public void testNonNumberArgument() {
        ByteArrayOutputStream out = captureOutput();
        App.main(new String[]{"abc"});
        restoreOutput();
        assertTrue(out.toString().contains("Please, input a valid option [1-2]"));
    }

    // Game must print "Hello!" as the first line
    @Test
    public void testWelcomeMessage() {
        setInput("1\n2\n3\n");
        ByteArrayOutputStream out = captureOutput();
        Game game = new Game(1);
        game.start();
        restoreOutput();
        restoreInput();
        assertTrue(out.toString().startsWith("Hello!"));
    }

    // Initial board must show all 0s
    @Test
    public void testInitialBoardShowsZeros() {
        Board board = new Board();
        ByteArrayOutputStream out = captureOutput();
        board.display();
        restoreOutput();
        String expected = "\n| 0 | 0 | 0 |\n| 0 | 0 | 0 |\n| 0 | 0 | 0 |\n\n";
        assertEquals(expected, out.toString());
    }

    // "Player#1's turn" must appear before the human's first move
    @Test
    public void testTurnMessageAppearsBeforeMove() {
        setInput("5\n");
        ByteArrayOutputStream out = captureOutput();
        Game game = new Game(1);
        game.start();
        restoreOutput();
        restoreInput();
        assertTrue(out.toString().contains("Player#1's turn"));
    }

    // "Player#2's turn" must appear when computer is about to move
    @Test
    public void testComputerTurnMessageAppears() {
        // Human picks 5, computer picks 1, human picks 2,3 won't happen
        // Just need enough moves to reach computer's turn
        setInput("5\n2\n3\n");
        ByteArrayOutputStream out = captureOutput();
        Game game = new Game(1);
        game.start();
        restoreOutput();
        restoreInput();
        assertTrue(out.toString().contains("Player#2's turn"));
    }

    // Typing a non-integer -> correct error + re-print turn message
    @Test
    public void testInvalidStringInputMessage() {
        setInput("abc\n5\n");
        ByteArrayOutputStream out = captureOutput();
        Board board = new Board();
        Player human = new Player("Player#1", 1, true);
        human.getMove(board, "Player#1");
        restoreOutput();
        restoreInput();
        String output = out.toString();
        assertTrue(output.contains("Please, input a valid number [1-9]"));
        assertTrue(output.contains("Player#1's turn"));
    }

    // Typing a number out of range -> correct error + re-print turn message
    @Test
    public void testOutOfRangeInputMessage() {
        setInput("0\n5\n");
        ByteArrayOutputStream out = captureOutput();
        Board board = new Board();
        Player human = new Player("Player#1", 1, true);
        human.getMove(board, "Player#1");
        restoreOutput();
        restoreInput();
        String output = out.toString();
        assertTrue(output.contains("Please, input a valid number [1-9]"));
        assertTrue(output.contains("Player#1's turn"));
    }

    // Typing an occupied cell -> correct error + re-print turn message
    @Test
    public void testOccupiedCellMessage() {
        setInput("1\n5\n");
        ByteArrayOutputStream out = captureOutput();
        Board board = new Board();
        board.setCell(0, 2);
        Player human = new Player("Player#1", 1, true);
        human.getMove(board, "Player#1");
        restoreOutput();
        restoreInput();
        String output = out.toString();
        assertTrue(output.contains("The cell is occupied!"));
        assertTrue(output.contains("Player#1's turn"));
    }

    // Win message must be "Player#1 won!" not "Human wins!" or "Player Human won!"
    @Test
    public void testWinMessage() {
        // Human wins top row: picks 1, 2, 3
        // Computer picks 4, 5 in between
        setInput("1\n2\n3\n");
        ByteArrayOutputStream out = captureOutput();
        Game game = new Game(1);
        game.start();
        restoreOutput();
        restoreInput();
        assertTrue(out.toString().contains("Player#1 won!"));
    }

    // Win message for computer must be "Player#2 won!"
    @Test
    public void testComputerWinMessage() {
        // Computer starts and wins left column: cells 1,4,7 (indices 0,3,6)
        // Computer picks 1,4,7 — human picks 2,5 in between
        setInput("2\n5\n");
        ByteArrayOutputStream out = captureOutput();
        Game game = new Game(2);
        game.start();
        restoreOutput();
        restoreInput();
        assertTrue(out.toString().contains("Player#2 won!"));
    }

    // Draw message must be "It is a draw!"
    @Test
    public void testDrawMessage() {
        // Human moves: 1,3,4,6,8 -> Computer moves: 2,5,7,9 -> draw
        setInput("1\n3\n4\n6\n8\n");
        ByteArrayOutputStream out = captureOutput();
        Game game = new Game(1);
        game.start();
        restoreOutput();
        restoreInput();
        assertTrue(out.toString().contains("It is a draw!"));
    }
}