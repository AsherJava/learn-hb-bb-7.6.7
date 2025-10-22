/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.ITempTable
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.ITempTable;
import java.util.List;

public interface ITableEntity {
    public String getTableName();

    public ITempTable getTempTableDefine();

    public LogicField findColumn(String var1);

    public boolean hasColumn(String var1);

    public List<LogicField> getPrimaryColumns();

    public List<LogicField> getLogicColumns();

    public List<LogicField> getAllColumns();
}

