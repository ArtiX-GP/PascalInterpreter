package ru.nikkorejz.Engine.Statements;

import ru.nikkorejz.Engine.Statement;

import java.util.ArrayList;
import java.util.List;

public class ComplexStatement implements Statement {

    private List<Statement> _Statements;

    public ComplexStatement() {
        _Statements = new ArrayList<>();
    }

    public void add(Statement statement) {
        _Statements.add(statement);
    }

    @Override
    public void execute() {
        for (Statement statement : _Statements) {
            statement.execute();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
//        result.append(".. ");
        for (Statement statement : _Statements) {
            result.append(statement);
        }
        return result.toString();
    }
}
