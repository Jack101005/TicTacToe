package vgu.pe2026.ttt.basic;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Tic_Tac_Toe_BlackBoxTest {

    private void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
 
    // Helper: start capturing what the terminal prints
    private ByteArrayOutputStream captureOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        return out;
    }
 
    // Helper: restore terminal back to normal
    private void restore() {
        System.setOut(System.out);
        System.setIn(System.in);
    }
    
    @Test
    public void checkforInvalidInputLow(){
        setInput("0\n5\n");
        ByteArrayOutputStream out = captureOutput();
        Board board = new Board();
        Player human = new Player("Player#1", 1, true);
        human.getMove(board, "Player#1");
        restore();
        assertTrue(out.toString().contains("Please, input a valid number [1-9]"));
    }

    @Test
    public void checkforInvalidInputHigh(){
        setInput("10\n5\n");
        ByteArrayOutputStream out = captureOutput();
        Board board = new Board();
        Player human = new Player("Player#1", 1, true);
        human.getMove(board, "Player#1");
        restore();
        assertTrue(out.toString().contains("Please, input a valid number [1-9]"));
    }
}
