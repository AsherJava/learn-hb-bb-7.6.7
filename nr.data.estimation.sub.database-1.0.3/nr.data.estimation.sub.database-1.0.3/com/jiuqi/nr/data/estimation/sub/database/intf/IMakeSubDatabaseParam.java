/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.sub.database.intf;

import com.jiuqi.nr.data.estimation.sub.database.intf.ICopiedTableGenerator;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import java.util.List;

public interface IMakeSubDatabaseParam {
    public String getSubDatabaseCode();

    public String getSubDatabaseTitle(String var1);

    public ICopiedTableGenerator getCopiedTableGenerator(String var1);

    public List<DesignColumnModelDefine> getOtherPrimaryColumns(String var1);
}

