package ru.nikkorejz.Engine;

import ru.nikkorejz.Engine.Expressions.BinaryExpression;
import ru.nikkorejz.Engine.Expressions.NumberExpression;
import ru.nikkorejz.Engine.Expressions.UnaryExpression;
import ru.nikkorejz.Engine.Expressions.VariableExpression;
import ru.nikkorejz.Engine.Statements.AssignmentStatement;
import ru.nikkorejz.Engine.Statements.ComplexStatement;
import ru.nikkorejz.Engine.Statements.SeparatorStatement;

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

    public Statement parse() {
        final ComplexStatement result = new ComplexStatement();
        while (!match(TokenType.EOF)) {
            result.add(getStatement());
        }
        return result;
    }

    private Statement getStatement() {
        final Token current = _CurrentToken;
        if (current.getType().equals(TokenType.WORD) && getNextToken(1).getType().equals(TokenType.EQUALS)) {
            return getAssignmentStatement(current.getContent());
        }

        if (current.getType().equals(TokenType.BEGIN)) {
            return getComplexStatement();
        }

        if (current.getType().equals(TokenType.EOL)) {
            getNextToken();
            return new SeparatorStatement();
        }

        if (current.getType().equals(TokenType.EOF)) {
            return null;
        }

        throw new RuntimeException("Unknown statement \"" + current.getType() + "\"");
    }

    private Statement getComplexStatement() {
        final ComplexStatement complexStatement = new ComplexStatement();
        check(TokenType.BEGIN);
        getNextToken();
        while (!match(TokenType.END)) {
            if (match(TokenType.EOF)) {
                throw new RuntimeException("Caught \"EOF\" statement instead \"END\".");
            }
            complexStatement.add(getStatement());
        }
        getNextToken();
        return complexStatement;
    }

    private Statement getAssignmentStatement(String varName) {
        check(TokenType.EQUALS);
        getNextToken();
        return new AssignmentStatement(varName, getExpression());
    }

    private Expression getExpression() {
        Expression result = getTerminal();
        while (true) {
            if (match(TokenType.PLUS)) {
                getNextToken();
                result = new BinaryExpression(result, TokenType.PLUS, getTerminal());
                continue;
            }

            if (match(TokenType.MINUS)) {
                getNextToken();
                result = new BinaryExpression(result, TokenType.MINUS, getTerminal());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression getTerminal() {
        Expression result = getFactor();
        while (true) {
            if (match(TokenType.MULTIPLY)) {
                getNextToken();
                result = new BinaryExpression(result, TokenType.MULTIPLY, getFactor());
                continue;
            }

            if (match(TokenType.DIVIDE)) {
                getNextToken();
                result = new BinaryExpression(result, TokenType.DIVIDE, getFactor());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression getFactor() {
        final Token current = _CurrentToken;

        if (match(TokenType.MINUS)) {
            getNextToken();
            return new UnaryExpression(TokenType.MINUS, getFactor());
        }

        if (match(TokenType.PLUS)) {
            getNextToken();
            return new UnaryExpression(TokenType.PLUS, getFactor());
        }

        if (match(TokenType.NUMBER)) {
            getNextToken();
            return new NumberExpression(Double.parseDouble(current.getContent()));
        }

        if (match(TokenType.LPAREN)) {
            getNextToken();
            Expression result = getExpression();
            if (match(TokenType.RPAREN)) {
                getNextToken();
                return result;
            }
            throw new RuntimeException("\")\" expected.");
        }

        if (match(TokenType.WORD)) {
            getNextToken();
            return new VariableExpression(current.getContent());
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

    private Token getNextToken(int relativePosition) {
        _CurrentTokenPosition += relativePosition;
        if (_CurrentTokenPosition >= _TokensSize) {
            _CurrentToken = EOF;
        } else {
            _CurrentToken = _Tokens.get(_CurrentTokenPosition);
        }
        return _CurrentToken;
    }

    private boolean match(TokenType type) {
        return type.equals(_CurrentToken.getType());
    }

    private void check(TokenType type) {
        if (type != _CurrentToken.getType()) {
            throw new RuntimeException(_CurrentToken + " doesn't equals of " + type);
        }
    }

}
