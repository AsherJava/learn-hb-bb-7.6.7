/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.org.formula;

import com.jiuqi.nr.entity.adapter.impl.org.formula.Token;
import com.jiuqi.nr.entity.adapter.impl.org.formula.TokenType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String input;
    private int position = 0;
    private final Pattern TOKEN_PATTERN = Pattern.compile("(?i)(IS\\s+NOT\\s+NULL|IS\\s+NULL|NOT\\s+IN|NOT\\s+LIKE|LIKE|IN|AND|OR|!=|==|<=|>=|<|>|<>|\\(|\\)|,)|([\\w.]+)|'([^']*)'", 2);

    public Lexer(String input) {
        this.input = input.trim();
    }

    Token nextToken() {
        if (this.position >= this.input.length()) {
            return new Token(TokenType.EOF, "");
        }
        Matcher matcher = this.TOKEN_PATTERN.matcher(this.input);
        if (matcher.find(this.position) && matcher.start() == this.position) {
            String match;
            this.position = matcher.end();
            while (this.position < this.input.length() && Character.isWhitespace(this.input.charAt(this.position))) {
                ++this.position;
            }
            String string = matcher.group(1) != null ? matcher.group(1) : (match = matcher.group(2) != null ? matcher.group(2) : matcher.group(3));
            if (match == null) {
                throw new RuntimeException("\u5339\u914d\u5931\u8d25");
            }
            if (match.equalsIgnoreCase("AND") || match.equalsIgnoreCase("OR")) {
                return new Token(TokenType.LOGICAL, match.toUpperCase());
            }
            if (match.equalsIgnoreCase("NOT")) {
                return new Token(TokenType.LOGICAL, "NOT");
            }
            if (match.matches("(?i)IS\\s+NOT\\s+NULL|IS\\s+NULL|NOT\\s+IN|NOT\\s+LIKE|LIKE|IN|!=|==|<=|>=|<|>|<>")) {
                return new Token(TokenType.OPERATOR, match.toUpperCase());
            }
            if (match.matches("\\(")) {
                return new Token(TokenType.PARENTHESIS, "(");
            }
            if (match.matches("\\)")) {
                return new Token(TokenType.PARENTHESIS, ")");
            }
            if (match.matches(",")) {
                return new Token(TokenType.OPERATOR, ",");
            }
            if (matcher.group(3) != null) {
                return new Token(TokenType.STRING, matcher.group(3));
            }
            if (match.matches("\\d+")) {
                return new Token(TokenType.NUMBER, match);
            }
            return new Token(TokenType.IDENTIFIER, match);
        }
        throw new RuntimeException("\u5728 " + this.position + "\u5904\u51fa\u73b0\u610f\u5916\u7684\u5b57\u7b26");
    }
}

