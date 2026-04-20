package vgu.pe2026.ttt.basic;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest_BlackBox_4 {

    private final PrintStream originalOut = System.out;
    private PipedOutputStream outputStream;
    private BufferedReader scanner;

    @Before
    public void setUp() throws IOException {
        outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        scanner = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(System.in);
    }

    // -------------------------
    // ALL V0.3 TESTS (unchanged)
    // -------------------------

    // No argument passed -> error message
    @Test
    public void emptyOptionTest() throws Exception {
        App.main(new String[]{});
        outputStream.close();
        assertEquals("Please, input a valid option [1-2]", scanner.readLine());
    }

    // Argument is a letter "a" -> error message
    @Test
    public void InvalidValue_a() throws Exception {
        App.main(new String[]{"a"});
        outputStream.close();
        assertEquals("Please, input a valid option [1-2]", scanner.readLine());
    }

    // Argument is out of range "3" -> error message
    @Test
    public void InvalidValue_3() throws Exception {
        App.main(new String[]{"3"});
        outputStream.close();
        assertEquals("Please, input a valid option [1-2]", scanner.readLine());
    }

    // Argument is quoted "'1'" -> error message (not exactly "1")
    @Test
    public void InvalidOptionTest() throws Exception {
        App.main(new String[]{"'1'"});
        outputStream.close();
        assertEquals("Please, input a valid option [1-2]", scanner.readLine());
    }

    // Valid argument "1" -> first line is "Hello!"
    @Test
    public void Player1StartsWithHello() throws Exception {
        App.main(new String[]{"1"});
        outputStream.close();
        assertEquals("Hello!", scanner.readLine());
    }

    // Valid argument "2" -> first line is "Hello!"
    @Test
    public void Player2StartsWithHello() throws Exception {
        App.main(new String[]{"2"});
        outputStream.close();
        assertEquals("Hello!", scanner.readLine());
    }

    // Valid argument "1" -> Hello! then empty board then Player#1's turn
    @Test
    public void Player1OptionTest() throws Exception {
        App.main(new String[]{"1"});
        outputStream.close();
        assertEquals("Hello!", scanner.readLine());
        scanner.readLine(); // blank line before board
        assertEquals("| 0 | 0 | 0 |", scanner.readLine());
        assertEquals("| 0 | 0 | 0 |", scanner.readLine());
        assertEquals("| 0 | 0 | 0 |", scanner.readLine());
        scanner.readLine(); // blank line after board
        assertEquals("Player#1's turn", scanner.readLine());
    }

    // Valid argument "2" -> Hello! then empty board then Player#2's turn
    @Test
    public void Player2OptionTest() throws Exception {
        App.main(new String[]{"2"});
        outputStream.close();
        assertEquals("Hello!", scanner.readLine());
        scanner.readLine(); // blank line before board
        assertEquals("| 0 | 0 | 0 |", scanner.readLine());
        assertEquals("| 0 | 0 | 0 |", scanner.readLine());
        assertEquals("| 0 | 0 | 0 |", scanner.readLine());
        scanner.readLine(); // blank line after board
        assertEquals("Player#2's turn", scanner.readLine());
    }

    // -------------------------
    // NEW V0.4 TESTS
    // -------------------------

    // Typing exactly "q" during human turn -> "End of the game"
    @Test
    public void quitWithQ() throws Exception {
        System.setIn(new ByteArrayInputStream("q\n".getBytes()));
        App.main(new String[]{"1"});
        outputStream.close();
        // Read past Hello!, board, and turn message to get to the quit message
        String line;
        String lastLine = null;
        while ((line = scanner.readLine()) != null) {
            lastLine = line;
        }
        assertEquals("End of the game", lastLine);
    }

    // Typing "Q" (uppercase) -> not "q", so shows error message instead of quitting
    @Test
    public void uppercaseQIsInvalid() throws Exception {
        System.setIn(new ByteArrayInputStream("Q\n".getBytes()));
        App.main(new String[]{"1"});
        outputStream.close();
        String output = "";
        String line;
        while ((line = scanner.readLine()) != null) {
            output += line + "\n";
        }
        // "Q" should trigger the invalid number message, NOT "End of the game"
        assertEquals(true, output.contains("Please, input a valid number [1-9]"));
        assertEquals(false, output.contains("End of the game"));
    }

    // Typing " q" (q with leading space) -> after trim() becomes "q" -> quits
    @Test
    public void qWithSpaceQuitsAfterTrim() throws Exception {
        System.setIn(new ByteArrayInputStream(" q\n".getBytes()));
        App.main(new String[]{"1"});
        outputStream.close();
        String output = "";
        String line;
        while ((line = scanner.readLine()) != null) {
            output += line + "\n";
        }
        assertEquals(true, output.contains("End of the game"));
    }

    // Typing "quit" -> not exactly "q", shows error message
    @Test
    public void quitWordIsInvalid() throws Exception {
        System.setIn(new ByteArrayInputStream("quit\n".getBytes()));
        App.main(new String[]{"1"});
        outputStream.close();
        String output = "";
        String line;
        while ((line = scanner.readLine()) != null) {
            output += line + "\n";
        }
        assertEquals(true, output.contains("Please, input a valid number [1-9]"));
        assertEquals(false, output.contains("End of the game"));
    }
}

