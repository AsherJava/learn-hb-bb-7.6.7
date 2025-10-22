/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public interface IDataMaskingProcesser {
    default public String getMaskingData(ExecutorContext context, ColumnModelDefine field, String value) {
        return null;
    }
}

