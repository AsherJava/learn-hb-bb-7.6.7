/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.org.formula;

import com.jiuqi.nr.entity.adapter.impl.org.formula.TokenType;

public class Token {
    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return String.format("Token(%s, %s)", new Object[]{this.type, this.value});
    }
}

