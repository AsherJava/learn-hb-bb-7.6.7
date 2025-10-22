/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.queryparam.TableNameFindParam
 */
package com.jiuqi.nr.data.estimation.web.ext.dataengine;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.queryparam.TableNameFindParam;
import com.jiuqi.nr.data.estimation.web.ext.dataengine.QueryTableNameFinder;
import java.util.List;
import java.util.Map;

public class CalculateTableNameFinder
extends QueryTableNameFinder {
    private final List<String> dataRegionKeys;

    public CalculateTableNameFinder(Map<String, String> primaryCondition, Map<String, String> oriTable2CopiedTable, List<String> dataRegionKeys) {
        super(primaryCondition, oriTable2CopiedTable);
        this.dataRegionKeys = dataRegionKeys;
    }

    @Override
    public String findTableName(ExecutorContext context, TableNameFindParam param) {
        if (this.dataRegionKeys.contains(param.getRegionKey())) {
            return (String)this.oriTable2CopiedTable.get(param.getTableName());
        }
        return param.getTableName();
    }
}

