package ru.nikkorejz.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private static final Map<String, TokenType> OPERATORS = new HashMap<>();

    static {
        OPERATORS.put("BEGIN", TokenType.BEGIN);

        OPERATORS.put("*", TokenType.MULTIPLY);
        OPERATORS.put("/", TokenType.DIVIDE);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("+", TokenType.PLUS);

        OPERATORS.put(":=", TokenType.EQUALS);

        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);

        OPERATORS.put(";", TokenType.EOL);

        OPERATORS.put("END", TokenType.END);
        OPERATORS.put(".", TokenType.EOF);
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
            } else if (OPERATORS.keySet().contains(String.valueOf(_CurrentCharacter)) || _CurrentCharacter == ':') {
                tokenizeOperator();
            } else if (Character.isLetter(_CurrentCharacter)) {
                tokenizeWord();
            } else {
                _CurrentCharacter = getNextCharacter();
            }
        }
//        throw new RuntimeException("Unknown symbol. \"" + _CurrentCharacter + "\" is not a operator.");
        return _Tokens;
    }

    private void tokenizeWord() {
        final StringBuilder buffer = new StringBuilder();
        while (true) {
            if (Character.isLetterOrDigit(_CurrentCharacter) || _CurrentCharacter == '_') {
                buffer.append(_CurrentCharacter);
                _CurrentCharacter = getNextCharacter();
                continue;
            }
            break;
        }

        if (OPERATORS.containsKey(buffer.toString().toUpperCase())) {
            addToken(OPERATORS.get(buffer.toString().toUpperCase()));
        } else {
            addToken(TokenType.WORD, buffer.toString());
        }
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
        if (_CurrentCharacter != ':') {
            addToken(OPERATORS.get(String.valueOf(_CurrentCharacter)));
        } else {
            do {
                _CurrentCharacter = getNextCharacter();
            } while (Character.isSpaceChar(_CurrentCharacter));
            if (_CurrentCharacter == '=') {
                addToken(TokenType.EQUALS);
            } else {
                throw new RuntimeException("Unknown statement \":" + _CurrentCharacter + "\".");
            }
        }
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
