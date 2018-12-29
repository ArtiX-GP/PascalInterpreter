package ru.nikkorejz.Engine.Statements;

import ru.nikkorejz.Engine.Expression;
import ru.nikkorejz.Engine.Statement;
import ru.nikkorejz.Engine.Variables;

public class AssignmentStatement implements Statement {

    private final String _VariableName;
    private final Expression _Expression;

    public AssignmentStatement(String variableName, Expression expression) {
        _VariableName = variableName;
        _Expression = expression;
    }

    @Override
    public void execute() {
        final double result = _Expression.eval();
        Variables.add(_VariableName, result);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", _VariableName, _Expression);
    }
}
