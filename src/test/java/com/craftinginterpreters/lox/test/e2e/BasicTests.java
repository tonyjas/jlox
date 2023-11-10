package com.craftinginterpreters.lox.test.e2e;


import com.craftinginterpreters.lox.Lox;
import org.junit.jupiter.api.Test;

public class BasicTests {


    @Test
    public void testAdd() {

        Lox.run("1 + 2");
    }
}
