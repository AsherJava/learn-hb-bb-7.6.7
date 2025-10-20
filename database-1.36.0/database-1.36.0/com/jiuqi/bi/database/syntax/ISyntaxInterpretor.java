/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.syntax;

import com.jiuqi.bi.database.DBException;

public interface ISyntaxInterpretor {
    public boolean toSQL(Object var1, Object var2, Object var3, StringBuilder var4) throws DBException;
}

