package vgu.pe2026.ttt.basic;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BlackBoxTest {

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
    }

    @Test
    public void emptyOptionTest() throws Exception {
        App.main(new String[]{});
        outputStream.close();
        assertEquals("Please, input a valid option [1-2]", scanner.readLine());
    }

    @Test
    public void InvalidValue_a() throws Exception {
        App.main(new String[]{"a"});
        outputStream.close();
        assertEquals("Please, input a valid option [1-2]", scanner.readLine());
    }

     @Test
    public void InvalidValue_ignore() throws Exception {
        App.main(new String[]{"1", "extra"});
        outputStream.close();
        assertEquals("Please, input a valid option [1-2]", scanner.readLine());
    }

     @Test
    public void InvalidOptionTest() throws Exception {
        App.main(new String[]{"'1'"});
        outputStream.close();
        assertEquals("Please, input a valid option [1-2]", scanner.readLine());
    }

    @Test
    public void InvalidValue_hello() throws Exception {
        App.main(new String[]{"1", "extra"});
        outputStream.close();
        assertEquals("Hello", scanner.readLine());
    }

    @Test
    public void Player1OptionTest() throws Exception, IOException {
        App.main(new String[]{"1"});
        outputStream.close();
        assertEquals("Hello", scanner.readLine());
        assertEquals("|0|0|0|", scanner.readLine());
        assertEquals("|0|0|0|", scanner.readLine());
        assertEquals("|0|0|0|", scanner.readLine());
    }

    @Test
    public void Player1OptionTestExtra() throws Exception, IOException {
        App.main(new String[]{"1", "extra"});
        outputStream.close();
        assertEquals("Hello", scanner.readLine());
        assertEquals("|0|0|0|", scanner.readLine());
        assertEquals("|0|0|0|", scanner.readLine());
        assertEquals("|0|0|0|", scanner.readLine());
    }
}