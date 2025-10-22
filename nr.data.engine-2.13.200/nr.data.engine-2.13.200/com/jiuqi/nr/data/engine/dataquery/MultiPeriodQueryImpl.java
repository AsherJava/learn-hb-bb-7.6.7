/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.GroupingQueryImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.np.dataengine.query.old.GroupingTableImpl
 *  com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl
 */
package com.jiuqi.nr.data.engine.dataquery;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.GroupingQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.query.old.GroupingTableImpl;
import com.jiuqi.nr.data.engine.dataquery.IMultiPeriodQuery;
import java.util.List;

public class MultiPeriodQueryImpl
implements IMultiPeriodQuery {
    @Override
    public IGroupingTable getMultiPeriodTable(ExecutorContext context, List<String> periodList, IGroupingQuery groupingQuery) throws Exception {
        DimensionValueSet masterKeys = groupingQuery.getMasterKeys();
        DimensionValueSet copyKeys = new DimensionValueSet();
        copyKeys.assign(masterKeys);
        IGroupingTable dataTable = null;
        ((GroupingQueryImpl)groupingQuery).setExcuteCustomGather(false);
        for (String periodCode : periodList) {
            copyKeys.setValue("DATATIME", (Object)periodCode);
            groupingQuery.setMasterKeys(copyKeys);
            if (dataTable == null) {
                dataTable = groupingQuery.executeReader(context);
                continue;
            }
            IGroupingTable readonlyTable = groupingQuery.executeReader(context);
            ((GroupingTableImpl)dataTable).addDataRows(((GroupingTableImpl)readonlyTable).getAllDataRows());
        }
        if (dataTable != null) {
            GroupingTableImpl groupingTableImpl = (GroupingTableImpl)dataTable;
            ((GroupingQueryImpl)groupingQuery).executeAdvancedGather_old(context, (com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl)groupingTableImpl, true);
            groupingTableImpl.setTotalCount(groupingTableImpl.getCount());
        }
        return dataTable;
    }

    @Override
    public IReadonlyTable getMultiPeriodTable(ExecutorContext context, List<String> periodList, IDataQuery dataQuery) throws Exception {
        DimensionValueSet masterKeys = dataQuery.getMasterKeys();
        DimensionValueSet copyKeys = new DimensionValueSet();
        copyKeys.assign(masterKeys);
        IReadonlyTable dataTable = null;
        for (String periodCode : periodList) {
            copyKeys.setValue("DATATIME", (Object)periodCode);
            dataQuery.setMasterKeys(copyKeys);
            if (dataTable == null) {
                dataTable = dataQuery.executeReader(context);
                continue;
            }
            IReadonlyTable readonlyTable = dataQuery.executeReader(context);
            ((ReadonlyTableImpl)dataTable).addDataRows(((ReadonlyTableImpl)readonlyTable).getAllDataRows());
        }
        if (dataTable != null) {
            ((ReadonlyTableImpl)dataTable).setTotalCount(dataTable.getCount());
        }
        return dataTable;
    }
}

