/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.antlr.runtime.Token
 */
package com.jiuqi.bi.database.sql.parser.antlr;

import org.antlr.runtime.Token;

public final class TokenInfo {
    public final String msg;
    public final Token token;

    public TokenInfo(Token token, String msg) {
        this.msg = msg;
        this.token = token;
    }
}

