package ru.nikkorejz.Engine.Statements;

import ru.nikkorejz.Engine.Statement;

public class SeparatorStatement implements Statement {

    public SeparatorStatement() {
    }

    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return ";";
    }
}
