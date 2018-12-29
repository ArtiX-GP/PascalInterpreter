package ru.nikkorejz.Engine.Expressions;

import ru.nikkorejz.Engine.Expression;
import ru.nikkorejz.Engine.Variables;

public class VariableExpression implements Expression {

    private final String _VariableName;

    public VariableExpression(String variableName) {
        _VariableName = variableName;
    }

    @Override
    public double eval() {
        return Variables.get(_VariableName);
    }

    @Override
    public String toString() {
        return String.format("[Value of variable: %s]", _VariableName);
    }
}
