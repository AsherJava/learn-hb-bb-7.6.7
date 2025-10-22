/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.sub.database.entity;

import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabaseDefine;
import java.util.List;

public interface IDataSchemeSubDatabase
extends IDataSchemeSubDatabaseDefine {
    public String getSubTableCode(String var1);

    public String getSubColumnCode(String var1, String var2);

    public List<String> getOtherPrimaryColumnCodes(String var1);
}

