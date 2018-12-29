package ru.nikkorejz.Engine.Expressions;

import ru.nikkorejz.Engine.Expression;

public class NumberExpression implements Expression {

    private double _Number;

    public NumberExpression(double number) {
        _Number = number;
    }

    @Override
    public double eval() {
        return _Number;
    }

    @Override
    public String toString() {
        return String.valueOf(_Number);
    }
}
