/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

public enum AlterType {
    ADD,
    DROP,
    MODIFY,
    RENAME;


    public static AlterType typeOf(String name) {
        if (name.equalsIgnoreCase("add")) {
            return ADD;
        }
        if (name.equalsIgnoreCase("drop")) {
            return DROP;
        }
        if (name.equalsIgnoreCase("modify")) {
            return MODIFY;
        }
        if (name.equalsIgnoreCase("rename")) {
            return RENAME;
        }
        return null;
    }
}

