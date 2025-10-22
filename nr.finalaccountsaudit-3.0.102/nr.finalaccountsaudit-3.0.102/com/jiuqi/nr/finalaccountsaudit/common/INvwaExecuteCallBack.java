/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;

public interface INvwaExecuteCallBack {
    public void execute(INvwaUpdatableDataSet var1, INvwaDataUpdator var2, List<ColumnModelDefine> var3) throws Exception;
}

