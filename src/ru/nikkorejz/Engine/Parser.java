package ru.nikkorejz.Engine;

import ru.nikkorejz.Engine.Expressions.BinaryExpression;
import ru.nikkorejz.Engine.Expressions.NumberExpression;
import ru.nikkorejz.Engine.Expressions.UnaryExpression;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final static Token EOF = new Token(TokenType.EOF, "");

    private final List<Token> _Tokens;
    private final int _TokensSize;

    private Token _CurrentToken;
    private int _CurrentTokenPosition;

    public Parser(List<Token> tokens) {
        _Tokens = tokens;
        _TokensSize = _Tokens.size();
        _CurrentTokenPosition = 0;
        _CurrentToken = _Tokens.get(_CurrentTokenPosition);
    }

    public List<Expression> parse() {
        final List<Expression> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(getExpression());
        }
        return result;
    }

    private Expression getExpression() {
        Expression result = getTerm();
        while (true) {
            if (match(TokenType.PLUS)) {
                getNextToken();
                result = new BinaryExpression(result, TokenType.PLUS, getTerm());
            }

            if (match(TokenType.MINUS)) {
                getNextToken();
                result = new BinaryExpression(result, TokenType.MINUS, getTerm());
            }
            break;
        }
        return result;
    }

    private Expression getTerm() {
        Expression result = getFactor();
        while (true) {
            if (match(TokenType.MULTIPLY)) {
                getNextToken();
                result = new BinaryExpression(result, TokenType.MULTIPLY, getFactor());
            }

            if (match(TokenType.DIVIDE)) {
                getNextToken();
                result = new BinaryExpression(result, TokenType.DIVIDE, getFactor());
            }
            break;
        }
        return result;
    }

    private Expression getFactor() {
        final Token current = _CurrentToken;
        if (match(TokenType.NUMBER)) {
            getNextToken();
            return new NumberExpression(Double.parseDouble(current.getContent()));
        }

        if (match(TokenType.MINUS)) {
            getNextToken();
            return new UnaryExpression(TokenType.MINUS, getFactor());
        }

        if (match(TokenType.PLUS)) {
            getNextToken();
            return new UnaryExpression(TokenType.PLUS, getFactor());
        }
        throw new RuntimeException("Unknown expression (\"" + current.toString() + "\").");
    }

    private Token getNextToken() {
        _CurrentTokenPosition++;
        if (_CurrentTokenPosition >= _TokensSize) {
            _CurrentToken = EOF;
        } else {
            _CurrentToken = _Tokens.get(_CurrentTokenPosition);
        }
        return _CurrentToken;
    }

//    private Token getNextToken(int relativePosition) {
//        _CurrentTokenPosition += relativePosition;
//        if (_CurrentTokenPosition >= _TokensSize) {
//            return EOF;
//        }
//        return _Tokens.get(_CurrentTokenPosition);
//    }

    private boolean match(TokenType type) {
        final Token current = _CurrentToken;
        if (type != current.getType())
            return false;
//        _CurrentTokenPosition++;
        return true;
    }

}
