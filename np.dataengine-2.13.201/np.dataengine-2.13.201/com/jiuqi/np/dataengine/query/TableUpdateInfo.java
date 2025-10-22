/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;

public class TableUpdateInfo {
    public TableRunInfo tableRunInfo;
    public QueryTable queryTable;
    public QueryFields queryFields;
    public int beginDateColIndex;
    public int endDateColIndex;
    public int[] dimFieldsColIndexes;

    public TableUpdateInfo(TableRunInfo tableRunInfo, QueryTable queryTable, QueryFields queryFields) {
        this.tableRunInfo = tableRunInfo;
        this.queryTable = queryTable;
        this.queryFields = queryFields;
        this.beginDateColIndex = -1;
        this.endDateColIndex = -1;
        this.dimFieldsColIndexes = new int[tableRunInfo.getDimFields().size()];
    }
}

