package ru.nikkorejz;

import ru.nikkorejz.Engine.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        final String line = "2 + 2 * -2 + 3745";
        Lexer lexer = new Lexer(line);
        List<Token> tokens = lexer.tokenize();
        for (Token token : tokens) {
            System.out.println(token);
        }

        final List<Expression> expressions = new Parser(tokens).parse();
        for (Expression expression : expressions) {
            System.out.println("(" + expression + ") = " + expression.eval());
        }
    }
}
