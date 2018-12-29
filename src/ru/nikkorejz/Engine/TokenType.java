package ru.nikkorejz.Engine;

public enum TokenType {
    BEGIN,

    NUMBER,

    MULTIPLY,
    DIVIDE,
    MINUS,
    PLUS,

    WORD,
    EQUALS,

    LPAREN, // (
    RPAREN, // )

    END,

    EOL,
    EOF
}
