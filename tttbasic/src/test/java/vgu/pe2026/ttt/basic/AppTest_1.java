package vgu.pe2026.ttt.basic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest_1
{
    @Test
    public void shouldAnswerWithTrue(){
        Board board = new Board();
        board.setCell(0,1);
        board.setCell(1,1);
        board.setCell(2,1);
        assertTrue(board.checkWin(1));
    }

    @Test
    public void shouldAnswerWithDraw(){
        Board board = new Board();
        board.setCell(0,1);
        board.setCell(1,1);
        board.setCell(2,2);
        board.setCell(3,2);
        board.setCell(4,2);
        board.setCell(5,1);
        board.setCell(6,1);
        board.setCell(7,1);
        board.setCell(8,2);
        assertTrue(board.isFull());
    }

    @Test
    public void testBoardDisplay() {
        Board board = new Board();
        board.setCell(0, 1);
        board.setCell(4, 2);
        board.setCell(8, 1);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        board.display();

        System.setOut(originalOut);

        String expected = "\n| 1 | 0 | 0 |\n| 0 | 2 | 0 |\n| 0 | 0 | 1 |\n\n";
        String actual = outputStream.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testColumnWin() {
        Board board = new Board();
        board.setCell(0, 2);
        board.setCell(3, 2);
        board.setCell(6, 2);
        assertTrue(board.checkWin(2));
    }

    @Test
    public void testDiagonalWin() {
        Board board = new Board();
        board.setCell(2, 1);
        board.setCell(4, 1);
        board.setCell(6, 1);
        assertTrue(board.checkWin(1));
    }

    @Test
    public void testNoWin() {
        Board board = new Board();
        board.setCell(0, 1);
        board.setCell(1, 2);
        board.setCell(2, 1);
        assertFalse(board.checkWin(1));
        assertFalse(board.checkWin(2));
    }

    @Test
    public void testIsOccupied() {
        Board board = new Board();
        board.setCell(3, 1);
        assertTrue(board.isOccupied(3));
        assertFalse(board.isOccupied(5));
    }

    @Test
    public void testBoardNotFull() {
        Board board = new Board();
        board.setCell(0, 1);
        board.setCell(1, 2);
        assertFalse(board.isFull());
    }

    @Test
    public void testGetFirstEmpty() {
        Board board = new Board();
        board.setCell(0, 1);
        board.setCell(1, 2);
        assertEquals(2, board.getFirstEmpty());
    }

    @Test
    public void testGetFirstEmptyWhenBoardEmpty() {
        Board board = new Board();
        assertEquals(0, board.getFirstEmpty());
    }
}