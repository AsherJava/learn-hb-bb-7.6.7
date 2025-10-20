/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.sql.model.ISQLPrintable;

public interface ISQLObject
extends ISQLPrintable {
    public String name();

    public String alias();

    public void setAlias(String var1);
}

