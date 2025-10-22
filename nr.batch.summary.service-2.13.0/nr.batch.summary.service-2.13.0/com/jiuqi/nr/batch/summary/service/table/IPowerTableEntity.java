/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table;

import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntityData;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumnMap;
import java.util.Map;
import java.util.Set;

public interface IPowerTableEntity
extends ITableEntityData {
    public IPowerTableEntity andConditionRows(Map<String, Object> var1);

    public IPowerTableEntity andConditionRows(Map<String, Object> var1, String var2);

    public IPowerTableEntity andJoinRows(IPowerTableEntity var1, Set<IPowerTableColumnMap> var2);

    public IPowerTableEntity andJoinRows(IPowerTableEntity var1, Set<IPowerTableColumnMap> var2, boolean var3);

    public IPowerTableEntity filterTagRows(String var1, boolean var2);

    public IPowerTableEntity groupByRows(Set<String> var1, Set<IPowerTableColumn> var2);

    public void clearRowTags();
}

