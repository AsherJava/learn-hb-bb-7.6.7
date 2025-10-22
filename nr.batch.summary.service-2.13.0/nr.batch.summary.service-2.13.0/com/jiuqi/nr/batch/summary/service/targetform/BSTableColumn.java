/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.nr.batch.summary.service.enumeration.SummaryFunction;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public interface BSTableColumn {
    public String getColumnName();

    public SummaryFunction getSQLGroupFunc();

    public ColumnModelDefine getColumnModel();
}

