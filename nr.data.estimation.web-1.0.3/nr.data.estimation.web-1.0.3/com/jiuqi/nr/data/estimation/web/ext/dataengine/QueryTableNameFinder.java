/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ITableNameFinder
 *  com.jiuqi.np.dataengine.queryparam.TableNameFindParam
 */
package com.jiuqi.nr.data.estimation.web.ext.dataengine;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ITableNameFinder;
import com.jiuqi.np.dataengine.queryparam.TableNameFindParam;
import java.util.Map;

public class QueryTableNameFinder
implements ITableNameFinder {
    protected final Map<String, String> primaryCondition;
    protected final Map<String, String> oriTable2CopiedTable;

    public QueryTableNameFinder(Map<String, String> primaryCondition, Map<String, String> oriTable2CopiedTable) {
        this.primaryCondition = primaryCondition;
        this.oriTable2CopiedTable = oriTable2CopiedTable;
    }

    public String findTableName(ExecutorContext context, TableNameFindParam param) {
        return this.oriTable2CopiedTable.get(param.getTableName());
    }

    public Map<String, String> getTableCondition(ExecutorContext context, String tableName) {
        if (this.oriTable2CopiedTable.containsValue(tableName)) {
            return this.primaryCondition;
        }
        return null;
    }
}

