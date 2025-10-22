/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 */
package com.jiuqi.nr.data.estimation.sub.database.intf;

import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;

public interface IDataSchemeSubDatabaseHelper {
    public StringLogger createSubDatabase(String var1, String var2);

    public StringLogger updateSubDatabase(String var1, String var2);

    public StringLogger deleteSubDatabase(String var1, String var2, boolean var3);

    public IDataSchemeSubDatabase querySubDatabaseDefine(String var1, String var2);

    public boolean existSubDatabase(String var1, String var2);
}

