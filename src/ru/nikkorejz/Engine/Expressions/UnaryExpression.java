package ru.nikkorejz.Engine.Expressions;

import ru.nikkorejz.Engine.Expression;
import ru.nikkorejz.Engine.TokenType;

public class UnaryExpression implements Expression {

    private final Expression _Expr;
    private final TokenType _Operation;

    public UnaryExpression(TokenType operation, Expression expr) {
        _Expr = expr;
        _Operation = operation;
    }

    @Override
    public double eval() {
        switch (_Operation) {
            case MINUS:
                return -_Expr.eval();
            case PLUS:
                return _Expr.eval();
        }
        throw new RuntimeException("Unknown unary operation. \"" + _Operation.toString() + "\" operation can't be executed.");
    }

    @Override
    public String toString() {
        return String.format("%s %s", _Operation, _Expr);
    }
}
