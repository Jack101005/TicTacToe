package vgu.pe2026.ttt.basic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest_2
{
    // v0.2 changed empty cells from showing "0" to showing their position number (1-9)
    // This test confirms the new display format is correct
    @Test
    public void testBoardDisplayV2() {
        Board board = new Board();
        board.setCell(0, 1);
        board.setCell(4, 2);
        board.setCell(8, 1);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        board.display();

        System.setOut(System.out);

        // Empty cells show position number, occupied cells show player number
        String expected = "\n| 1 | 2 | 3 |\n| 4 | 2 | 6 |\n| 7 | 8 | 1 |\n\n";
        assertEquals(expected, out.toString());
    }

    // Test that an empty board shows all position numbers 1-9
    @Test
    public void testEmptyBoardDisplay() {
        Board board = new Board();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        board.display();

        System.setOut(System.out);

        String expected = "\n| 1 | 2 | 3 |\n| 4 | 5 | 6 |\n| 7 | 8 | 9 |\n\n";
        assertEquals(expected, out.toString());
    }

    // Test that typing a non-number (e.g. "abc") triggers the correct error message
    @Test
    public void testInvalidStringInput() {
        // Simulate user typing "abc" then "5" (so the game doesn't hang)
        String simulatedInput = "abc\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Board board = new Board();
        Player human = new Player("Human", 1, true);
        human.getMove(board);

        System.setOut(System.out);
        System.setIn(System.in);

        assertTrue(out.toString().contains("Please, input a valid number [1-9]"));
    }

    // Test that typing a number outside 1-9 (e.g. "0" or "10") triggers the correct error message
    @Test
    public void testOutOfRangeInput() {
        // Simulate user typing "0" (invalid) then "3" (valid)
        String simulatedInput = "0\n3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Board board = new Board();
        Player human = new Player("Human", 1, true);
        human.getMove(board);

        System.setOut(System.out);
        System.setIn(System.in);

        assertTrue(out.toString().contains("Please, input a valid number [1-9]"));
    }

    // Test that choosing an already occupied cell triggers the correct error message
    @Test
    public void testOccupiedCellInput() {
        // Cell 1 (index 0) is already taken, user types "1" then "3"
        String simulatedInput = "1\n3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Board board = new Board();
        board.setCell(0, 2); // pre-occupy cell 1
        Player human = new Player("Human", 1, true);
        human.getMove(board);

        System.setOut(System.out);
        System.setIn(System.in);

        assertTrue(out.toString().contains("The cell is occupied!"));
    }

    // Test that a valid input returns the correct 0-based index
    // User types "5" which should map to index 4
    @Test
    public void testValidInputReturnsCorrectIndex() {
        String simulatedInput = "5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Board board = new Board();
        Player human = new Player("Human", 1, true);
        int move = human.getMove(board);

        System.setIn(System.in);

        assertEquals(4, move);
    }

    // Test the win message format — v0.2 requires "Player Human won!" not "Human wins!"
    @Test
    public void testWinMessage() {
        // Human wins immediately: types 1, 2, 3 to fill top row
        // Computer takes 4, 5 in between (getFirstEmpty)
        // Sequence: Human=1, Computer=4, Human=2, Computer=5, Human=3
        String simulatedInput = "1\n2\n3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Game game = new Game(1); // human starts
        game.start();

        System.setOut(System.out);
        System.setIn(System.in);

        assertTrue(out.toString().contains("Player Human won!"));
    }

    // Test the draw message format — v0.2 requires "It is a draw!" not "It's a draw!"
    @Test
    public void testDrawMessage() {
        // Fill the board in a way that produces no winner
        // Board result: 1 2 1 / 1 2 1 / 2 1 2  -> draw
        // Human moves: 1,3,4,6,8  Computer moves: 2,5,7,9
        String simulatedInput = "1\n3\n4\n6\n8\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Game game = new Game(1);
        game.start();

        System.setOut(System.out);
        System.setIn(System.in);

        assertTrue(out.toString().contains("It is a draw!"));
    }
}