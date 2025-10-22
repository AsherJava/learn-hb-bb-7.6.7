/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;

public interface INvwaRowCallBack {
    public void queryForObject(List<ColumnModelDefine> var1, MemoryDataSet<NvwaQueryColumn> var2);
}

