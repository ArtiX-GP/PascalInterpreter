package ru.nikkorejz;

import ru.nikkorejz.Engine.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Source.jz"));
        StringBuilder program = new StringBuilder();
        while (scanner.hasNextLine()) {
            program.append(scanner.nextLine()).append(" ");
        }

        Lexer lexer = new Lexer(program.toString());
        List<Token> tokens = lexer.tokenize();

//        Проверка токенов.
//        for (Token token : tokens) {
//            System.out.println(token);
//        }

        Statement statements = new Parser(tokens).parse();
        statements.execute();

//        Проверка выражения.
//        System.out.println(statements);


        System.out.println(String.format("%s = %s", "x", Variables.get("x")));
        System.out.println(String.format("%s = %s", "y", Variables.get("y")));
        System.out.println(String.format("%s = %s", "a", Variables.get("a")));
        System.out.println(String.format("%s = %s", "b", Variables.get("b")));
        System.out.println(String.format("%s = %s", "c", Variables.get("c")));
    }
}
