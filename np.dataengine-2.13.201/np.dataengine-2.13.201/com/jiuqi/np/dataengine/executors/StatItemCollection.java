/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatRow;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatItemCollection {
    private List<StatItem> statItems = new ArrayList<StatItem>();
    private Map<Long, StatItem> statItemMap = new HashMap<Long, StatItem>();
    private Map<DimensionValueSet, StatRow> statRows = new HashMap<DimensionValueSet, StatRow>();
    private StatRow currentRow;

    public StatItem getItem(int index) {
        return this.statItems.get(index);
    }

    public int getColumnCount() {
        return this.statItems.size();
    }

    public StatItem creatStatItem(QueryContext context, StatisticInfo statInfo) throws SyntaxException {
        int index = this.statItems.size();
        StatItem statItem = new StatItem(statInfo.statKind, statInfo.valueNode.getType((IContext)context), index);
        this.statItemMap.put(statInfo.getUniqueNum(), statItem);
        this.statItems.add(statItem);
        return statItem;
    }

    public StatRow getRow(DimensionValueSet rowKey) {
        StatRow row = this.statRows.get(rowKey);
        if (row == null) {
            row = new StatRow(new DimensionValueSet(rowKey), this.statItems.size());
            this.statRows.put(row.getRowKey(), row);
        }
        return row;
    }

    public AbstractData getStatResult(StatItem statItem) {
        if (this.currentRow == null) {
            return statItem.getResult();
        }
        return this.currentRow.getResult(statItem);
    }

    public StatItem getStatItem(Long number) {
        StatItem statItem = this.statItemMap.get(number);
        return statItem;
    }

    public void setCurrentRow(DimensionValueSet rowKey) {
        this.currentRow = this.getRow(rowKey);
    }

    public void reset() {
        if (this.statRows.size() > 0) {
            this.statRows.clear();
        }
        this.currentRow = null;
    }
}

