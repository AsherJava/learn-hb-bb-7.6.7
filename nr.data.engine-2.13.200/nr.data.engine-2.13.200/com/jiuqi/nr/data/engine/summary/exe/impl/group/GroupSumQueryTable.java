/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataRow
 */
package com.jiuqi.nr.data.engine.summary.exe.impl.group;

import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumQueryTable;
import com.jiuqi.nr.data.engine.summary.exe.impl.group.GroupSumCell;
import com.jiuqi.nr.data.engine.summary.exe.impl.group.SumGroup;
import com.jiuqi.nr.data.engine.summary.parse.SumContext;
import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupSumQueryTable
extends SumQueryTable {
    private Map<String, SumGroup> sumGroups = new HashMap<String, SumGroup>();
    private SumNode groupField;

    public GroupSumQueryTable(String tableName, String periodOffSet, SumNode groupField) {
        super(tableName, periodOffSet);
        this.groupField = groupField;
    }

    public void prepare() {
        for (SumGroup group : this.sumGroups.values()) {
            List<GroupSumCell> groupSumCells = group.getGroupSumCells();
            Collections.sort(groupSumCells);
            for (int i = 0; i < groupSumCells.size(); ++i) {
                GroupSumCell cell = groupSumCells.get(i);
                cell.setResultSetIndex(i + 1);
            }
        }
    }

    public void runGroupSum(SumContext context, Connection conn, ResultSet rs, IDataRow row) throws Exception {
        List<SumGroup> groups = this.getSumGroupList();
        int conditionIndex = groups.get(0).getGroupSumCells().size() + 1;
        while (rs.next()) {
            Object conditionFieldValue = rs.getObject(conditionIndex);
            context.setConditionFieldValue(conditionFieldValue);
            for (SumGroup group : groups) {
                group.runStatistic(context, rs);
            }
        }
        for (SumGroup group : groups) {
            group.doSave(row);
        }
    }

    public Map<String, SumGroup> getSumGroups() {
        return this.sumGroups;
    }

    public List<SumGroup> getSumGroupList() {
        return new ArrayList<SumGroup>(this.sumGroups.values());
    }

    public SumNode getGroupField() {
        return this.groupField;
    }

    public void setGroupField(SumNode groupField) {
        this.groupField = groupField;
    }

    @Override
    public String toString() {
        return "GroupSumQueryTable [tableName=" + this.getTableName() + ", periodOffSet=" + this.getPeriodOffSet() + "]";
    }
}

