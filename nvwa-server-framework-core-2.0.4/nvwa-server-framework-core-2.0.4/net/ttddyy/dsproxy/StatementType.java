/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;

public enum StatementType {
    STATEMENT,
    PREPARED,
    CALLABLE;


    public static <T extends Statement> StatementType valueOf(T statement) {
        if (statement instanceof CallableStatement) {
            return CALLABLE;
        }
        if (statement instanceof PreparedStatement) {
            return PREPARED;
        }
        return STATEMENT;
    }
}

