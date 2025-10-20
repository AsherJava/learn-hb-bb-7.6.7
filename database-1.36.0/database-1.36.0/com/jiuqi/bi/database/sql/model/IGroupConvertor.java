/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.GroupMode;
import com.jiuqi.bi.database.sql.model.SQLModelException;

public interface IGroupConvertor {
    public void toSQL(StringBuilder var1, GroupMode var2, IDatabase var3, String var4) throws SQLModelException;
}

