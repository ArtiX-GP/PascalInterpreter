package ru.nikkorejz.Engine;

public class Token {
    private TokenType _Type;
    private String _Content;

    public Token() {
    }

    Token(TokenType type, String content) {
        _Type = type;
        _Content = content;
    }

    public void setType(TokenType type) {
        _Type = type;
    }

    public void setContent(String content) {
        _Content = content;
    }

    TokenType getType() {
        return _Type;
    }

    String getContent() {
        return _Content;
    }

    @Override
    public String toString() {
        return getType() + " >> " + getContent();
    }
}
