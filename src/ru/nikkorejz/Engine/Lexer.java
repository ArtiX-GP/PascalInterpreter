package ru.nikkorejz.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private static final Map<String, TokenType> OPERATORS = new HashMap<>();

    static {
        OPERATORS.put("*", TokenType.MULTIPLY);
        OPERATORS.put("/", TokenType.DIVIDE);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("+", TokenType.PLUS);

        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);
    }

    private final String _Line;
    private List<Token> _Tokens;

    private char _CurrentCharacter;

    private int _Position;
    private int _Length;

    public Lexer(String line) {
        _Line = line;
        _Length = line.length();

        _Position = 0;
        _Tokens = new ArrayList<>();
        _CurrentCharacter = _Line.charAt(_Position);
    }

    public List<Token> tokenize() {
        while (_Position < _Length) {
            _CurrentCharacter = getNextCharacter(0);
            if (Character.isDigit(_CurrentCharacter)) {
                tokenizeNumber();
            } else if (OPERATORS.keySet().contains(String.valueOf(_CurrentCharacter))) {
                tokenizeOperator();
            } else if (Character.isSpaceChar(_CurrentCharacter)) {
                getNextCharacter();
            } else {
                throw new RuntimeException("Unknown symbol. \"" + _CurrentCharacter + "\" is not a operator.");
            }
        }
        return _Tokens;
    }

    private void tokenizeNumber() {
        final StringBuilder buffer = new StringBuilder();
        while (Character.isDigit(_CurrentCharacter)) {
            buffer.append(_CurrentCharacter);
            _CurrentCharacter = getNextCharacter();
        }

        addToken(TokenType.NUMBER, buffer.toString());
    }

    private void tokenizeOperator() {
        addToken(OPERATORS.get(String.valueOf(_CurrentCharacter)));
        _CurrentCharacter = getNextCharacter();
    }

    private char getNextCharacter(int relativePosition) {
        _Position += relativePosition;
        if (_Position >= _Length) {
            return '\0';
        }
        return _Line.charAt(_Position);
    }

    private char getNextCharacter() {
        _Position++;
        if (_Position >= _Length) {
            return '\0';
        }
        return _Line.charAt(_Position);
    }

    private void addToken(TokenType type) {
        addToken(type, "");
    }

    private void addToken(TokenType type, String content) {
        _Tokens.add(new Token(type, content));
    }
}
