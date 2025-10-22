/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.org.formula;

import com.jiuqi.nr.entity.adapter.impl.org.formula.Lexer;
import com.jiuqi.nr.entity.adapter.impl.org.formula.Token;
import com.jiuqi.nr.entity.adapter.impl.org.formula.TokenType;

public class ExpressionParser {
    private final Lexer lexer;
    private Token currentToken;

    public ExpressionParser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
    }

    void consume(TokenType type) {
        if (this.currentToken.type != type) {
            throw new RuntimeException("\u65e0\u6cd5\u8bc6\u522b\u7684token: " + this.currentToken);
        }
        this.currentToken = this.lexer.nextToken();
    }

    public boolean parseExpression() {
        return this.parseOrExpression();
    }

    private boolean parseOrExpression() {
        boolean valid = this.parseAndExpression();
        while (this.currentToken.type == TokenType.LOGICAL && this.currentToken.value.equals("OR")) {
            this.consume(TokenType.LOGICAL);
            valid &= this.parseAndExpression();
        }
        return valid;
    }

    private boolean parseAndExpression() {
        boolean valid = this.parsePrimaryExpression();
        while (this.currentToken.type == TokenType.LOGICAL && this.currentToken.value.equals("AND")) {
            this.consume(TokenType.LOGICAL);
            valid &= this.parsePrimaryExpression();
        }
        return valid;
    }

    private boolean parsePrimaryExpression() {
        if (this.currentToken.type == TokenType.PARENTHESIS && this.currentToken.value.equals("(")) {
            this.consume(TokenType.PARENTHESIS);
            boolean valid = this.parseExpression();
            if (this.currentToken.type == TokenType.PARENTHESIS && this.currentToken.value.equals(")")) {
                this.consume(TokenType.PARENTHESIS);
                return valid;
            }
            throw new RuntimeException("\u7f3a\u5c11\u53f3\u62ec\u53f7");
        }
        return this.parseComparison();
    }

    private boolean parseComparison() {
        if (this.currentToken.type == TokenType.IDENTIFIER) {
            this.consume(TokenType.IDENTIFIER);
            if (this.currentToken.type == TokenType.OPERATOR) {
                String operator = this.currentToken.value;
                this.consume(TokenType.OPERATOR);
                if (operator.equalsIgnoreCase("IS NULL") || operator.equalsIgnoreCase("IS NOT NULL")) {
                    return true;
                }
                if (operator.equalsIgnoreCase("IN") || operator.equalsIgnoreCase("NOT IN")) {
                    if (this.currentToken.type == TokenType.PARENTHESIS && this.currentToken.value.equals("(")) {
                        this.consume(TokenType.PARENTHESIS);
                        if (this.currentToken.type == TokenType.NUMBER || this.currentToken.type == TokenType.STRING) {
                            this.consume(this.currentToken.type);
                            while (this.currentToken.type == TokenType.OPERATOR && this.currentToken.value.equals(",")) {
                                this.consume(TokenType.OPERATOR);
                                if (this.currentToken.type == TokenType.NUMBER || this.currentToken.type == TokenType.STRING) {
                                    this.consume(this.currentToken.type);
                                    continue;
                                }
                                throw new RuntimeException("IN \u5b50\u53e5\u7684\u9017\u53f7\u540e\u9762\u9700\u8981\u6709\u503c");
                            }
                        } else {
                            throw new RuntimeException("IN \u5b50\u53e5\u4e2d\u9700\u8981\u6709\u503c");
                        }
                        if (this.currentToken.type == TokenType.PARENTHESIS && this.currentToken.value.equals(")")) {
                            this.consume(TokenType.PARENTHESIS);
                            return true;
                        }
                        throw new RuntimeException("IN \u5b50\u53e5\u4e2d\u7f3a\u5c11\u53f3\u62ec\u53f7");
                    }
                    throw new RuntimeException("\u5728 IN \u6216 NOT IN \u540e\u9700\u8981\u51fa\u73b0 '('");
                }
                if (this.currentToken.type == TokenType.NUMBER || this.currentToken.type == TokenType.STRING) {
                    this.consume(this.currentToken.type);
                    return true;
                }
                if (this.currentToken.type == TokenType.IDENTIFIER) {
                    this.consume(TokenType.IDENTIFIER);
                    return true;
                }
                return false;
            }
        }
        throw new RuntimeException("\u65e0\u6548\u7684\u6bd4\u8f83\u8868\u8fbe\u5f0f");
    }
}

