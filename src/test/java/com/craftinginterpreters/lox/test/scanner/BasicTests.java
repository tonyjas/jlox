package com.craftinginterpreters.lox.test.scanner;

import com.craftinginterpreters.lox.Scanner;
import com.craftinginterpreters.lox.Token;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTests {


    @Test
    void testScannerOnePlusOne() {

        String source = "1 + 1";

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals(tokens.size(), 4);
        assertEquals(tokens.get(0).toString(), "NUMBER 1 1.0");
        assertEquals(tokens.get(1).toString(), "PLUS + null");
        assertEquals(tokens.get(2).toString(), "NUMBER 1 1.0");
        assertEquals(tokens.get(3).toString(), "EOF  null");
    }

    @Test
    void testScannerComplexArithmetic() {

        String source = "2 + 3 * 3 - -4 * (4+3) / 2";

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals(tokens.size(), 17);
        assertEquals(tokens.get(0).toString(), "NUMBER 2 2.0");
        assertEquals(tokens.get(1).toString(), "PLUS + null");
        assertEquals(tokens.get(2).toString(), "NUMBER 3 3.0");
        assertEquals(tokens.get(3).toString(), "STAR * null");
        assertEquals(tokens.get(4).toString(), "NUMBER 3 3.0");
        assertEquals(tokens.get(5).toString(), "MINUS - null");
        assertEquals(tokens.get(6).toString(), "MINUS - null");
        assertEquals(tokens.get(7).toString(), "NUMBER 4 4.0");
        assertEquals(tokens.get(8).toString(), "STAR * null");
        assertEquals(tokens.get(9).toString(), "LEFT_PAREN ( null");
        assertEquals(tokens.get(10).toString(), "NUMBER 4 4.0");
        assertEquals(tokens.get(11).toString(), "PLUS + null");
        assertEquals(tokens.get(12).toString(), "NUMBER 3 3.0");
        assertEquals(tokens.get(13).toString(), "RIGHT_PAREN ) null");
        assertEquals(tokens.get(14).toString(), "SLASH / null");
        assertEquals(tokens.get(15).toString(), "NUMBER 2 2.0");
        assertEquals(tokens.get(16).toString(), "EOF  null");

// [NUMBER 2 2.0, PLUS + null, NUMBER 3 3.0, STAR * null, NUMBER 3 3.0, MINUS - null, MINUS - null, NUMBER 4 4.0, STAR * null, LEFT_PAREN ( null, NUMBER 4 4.0, PLUS + null,
// NUMBER 3 3.0, RIGHT_PAREN ) null, SLASH / null, NUMBER 2 2.0, EOF  null]
    }

    @Test
    void testLexicalError() {

        // capture System.err
        final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStreamCaptor));

        String source = "1 + &";

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals("[line 1] Error: Unexpected character.", errStreamCaptor.toString().trim());
        assertEquals(tokens.size(), 3);
        assertEquals(tokens.get(0).toString(), "NUMBER 1 1.0");
        assertEquals(tokens.get(1).toString(), "PLUS + null");
        assertEquals(tokens.get(2).toString(), "EOF  null");

    }

    @Test
    void testVariables() {

        String source = """
                var var1 = 2
                var var2 = 3
                var var3 = var1 + var2
                """;

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals(tokens.size(), 15);
        assertEquals(tokens.get(0).toString(), "VAR var null");
        assertEquals(tokens.get(1).toString(), "IDENTIFIER var1 null");
        assertEquals(tokens.get(2).toString(), "EQUAL = null");
        assertEquals(tokens.get(3).toString(), "NUMBER 2 2.0");
        assertEquals(tokens.get(4).toString(), "VAR var null");
        assertEquals(tokens.get(5).toString(), "IDENTIFIER var2 null");
        assertEquals(tokens.get(6).toString(), "EQUAL = null");
        assertEquals(tokens.get(7).toString(), "NUMBER 3 3.0");
        assertEquals(tokens.get(8).toString(), "VAR var null");
        assertEquals(tokens.get(9).toString(), "IDENTIFIER var3 null");
        assertEquals(tokens.get(10).toString(), "EQUAL = null");
        assertEquals(tokens.get(11).toString(), "IDENTIFIER var1 null");
        assertEquals(tokens.get(12).toString(), "PLUS + null");
        assertEquals(tokens.get(13).toString(), "IDENTIFIER var2 null");
        assertEquals(tokens.get(14).toString(), "EOF  null");

        // [VAR var null, IDENTIFIER var1 null, EQUAL = null, NUMBER 2 2.0, VAR var null, IDENTIFIER var2 null,
        // EQUAL = null, NUMBER 3 3.0, VAR var null, IDENTIFIER var3 null, EQUAL = null, IDENTIFIER var1 null,
        // PLUS + null, IDENTIFIER var2 null, EOF  null]
    }


    @Test
    void testFunction() {
        String source = """
                fun myFunction(x, y) {
                    return x + y;
                }
                """;

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals(tokens.size(), 15);
        assertEquals(tokens.get(0).toString(), "FUN fun null");
        assertEquals(tokens.get(1).toString(), "IDENTIFIER myFunction null");
        assertEquals(tokens.get(2).toString(), "LEFT_PAREN ( null");
        assertEquals(tokens.get(3).toString(), "IDENTIFIER x null");
        assertEquals(tokens.get(4).toString(), "COMMA , null");
        assertEquals(tokens.get(5).toString(), "IDENTIFIER y null");
        assertEquals(tokens.get(6).toString(), "RIGHT_PAREN ) null");
        assertEquals(tokens.get(7).toString(), "LEFT_BRACE { null");
        assertEquals(tokens.get(8).toString(), "RETURN return null");
        assertEquals(tokens.get(9).toString(), "IDENTIFIER x null");
        assertEquals(tokens.get(10).toString(), "PLUS + null");
        assertEquals(tokens.get(11).toString(), "IDENTIFIER y null");
        assertEquals(tokens.get(12).toString(), "SEMICOLON ; null");
        assertEquals(tokens.get(13).toString(), "RIGHT_BRACE } null");
        assertEquals(tokens.get(14).toString(), "EOF  null");


        // [FUN fun null, IDENTIFIER myFunction null, LEFT_PAREN ( null, IDENTIFIER x null, COMMA , null,
        // IDENTIFIER y null, RIGHT_PAREN ) null, LEFT_BRACE { null, RETURN return null, IDENTIFIER x null,
        // PLUS + null, IDENTIFIER y null, SEMICOLON ; null, RIGHT_BRACE } null, EOF  null]

    }
}
