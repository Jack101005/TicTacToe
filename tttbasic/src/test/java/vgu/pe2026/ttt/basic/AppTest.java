package vgu.pe2026.ttt.basic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test // I was intenting to test the win scenario of the game 
    public void shouldAnswerWithTrue(){
        Board board = new Board();
        board.setCell(0,1);
        board.setCell(1,1);
        board.setCell(2,1);
        assertTrue(board.checkWin(1));
    }

    @Test // I am testing the scenario when the game comes to the "full board" situation because it is also count as a draw.
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
}

