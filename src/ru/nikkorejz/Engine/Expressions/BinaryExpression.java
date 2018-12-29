package ru.nikkorejz.Engine.Expressions;

import ru.nikkorejz.Engine.Expression;
import ru.nikkorejz.Engine.TokenType;

public class BinaryExpression implements Expression {

    private final Expression _ExprL, _ExprR;
    private final TokenType _Operation;

    public BinaryExpression(Expression exprL, TokenType operation, Expression exprR) {
        _ExprL = exprL;
        _ExprR = exprR;
        _Operation = operation;
    }

    @Override
    public double eval() {
        switch (_Operation) {
            case PLUS:
                return _ExprL.eval() + _ExprR.eval();
            case MINUS:
                return _ExprL.eval() - _ExprR.eval();
            case MULTIPLY:
                return _ExprL.eval() * _ExprR.eval();
            case DIVIDE:
                return _ExprL.eval() / _ExprR.eval();
        }
        throw new RuntimeException("Unknown binary operation. \"" + _Operation.toString() + "\" operation can't be executed.");
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", _ExprL, _Operation, _ExprR);
    }
}
