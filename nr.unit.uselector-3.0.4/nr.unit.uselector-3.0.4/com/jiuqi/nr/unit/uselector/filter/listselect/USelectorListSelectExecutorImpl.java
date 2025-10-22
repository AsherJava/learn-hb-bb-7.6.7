/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.uselector.dataio.IRowData;
import com.jiuqi.nr.unit.uselector.filter.listselect.FTableConditionRow;
import com.jiuqi.nr.unit.uselector.filter.listselect.FTableUnitRow;
import com.jiuqi.nr.unit.uselector.filter.listselect.FilterDataRule;
import com.jiuqi.nr.unit.uselector.filter.listselect.USelectorListSelectExecutor;
import java.util.ArrayList;
import java.util.List;

public class USelectorListSelectExecutorImpl
implements USelectorListSelectExecutor {
    private int unMatch;
    private int exactCount;
    private int fuzzyCount;
    private int maxShowSize;
    private FilterDataRule rule;
    private List<IEntityRow> allRows;
    private List<IRowData> conditions;

    public USelectorListSelectExecutorImpl(int maxShowSize, FilterDataRule rule, List<IEntityRow> allRows, List<IRowData> conditions) {
        this.rule = rule;
        this.allRows = allRows;
        this.conditions = conditions;
        this.maxShowSize = maxShowSize;
    }

    @Override
    public int getExactCount() {
        return this.exactCount;
    }

    @Override
    public int getFuzzyCount() {
        return this.fuzzyCount;
    }

    @Override
    public int getUnMatch() {
        return this.unMatch;
    }

    @Override
    public List<FTableConditionRow> executeListSelect() {
        ArrayList<FTableConditionRow> dataSet = new ArrayList<FTableConditionRow>();
        int order = 0;
        for (IRowData condition : this.conditions) {
            FTableConditionRow condRow = this.conditionRow(condition, ++order);
            this.doSelectData(condRow, condition);
            dataSet.add(condRow);
            if (condRow.getChildren().size() == 0) {
                condRow.setMatchType("N");
                ++this.unMatch;
                continue;
            }
            if ("T".equals(condRow.getMatchType())) {
                ++this.exactCount;
                continue;
            }
            if (!"F".equals(condRow.getMatchType())) continue;
            ++this.fuzzyCount;
        }
        return dataSet;
    }

    protected void doSelectData(FTableConditionRow condRow, IRowData condition) {
        int index = 0;
        ArrayList<FTableUnitRow> children = new ArrayList<FTableUnitRow>();
        int matchType = 0;
        for (IEntityRow allRow : this.allRows) {
            IEntityRow row = allRow;
            if (!this.rule.matchRowWithCondition(condition, row)) continue;
            if (this.rule.isCodeExactMatch()) {
                if (matchType != 2) {
                    children.clear();
                    index = 0;
                    matchType = 2;
                }
                if (index >= this.maxShowSize) continue;
                children.add(this.unitRow(row, false));
                if (index++ != this.maxShowSize - 1) continue;
                children.add(this.showMoreRow());
                continue;
            }
            if (this.rule.isTitleExactMatch() && matchType < 2) {
                if (matchType == 0) {
                    children.clear();
                    index = 0;
                    matchType = 1;
                }
                if (index >= this.maxShowSize) continue;
                children.add(this.unitRow(row, false));
                if (index++ != this.maxShowSize - 1) continue;
                children.add(this.showMoreRow());
                continue;
            }
            if (matchType >= 1 || index >= this.maxShowSize || "T".equals(condRow.getMatchType())) continue;
            children.add(this.unitRow(row, false));
            condRow.setMatchType("F");
            if (index++ != this.maxShowSize - 1) continue;
            children.add(this.showMoreRow());
        }
        if (matchType > 0 && !children.isEmpty()) {
            if (children.size() == 1) {
                ((FTableUnitRow)children.get(0)).setChecked(true);
                condRow.setMatchType("T");
            } else {
                condRow.setMatchType("F");
            }
        }
        condRow.setChildren(children);
    }

    protected FTableConditionRow conditionRow(IRowData filterData, int order) {
        FTableConditionRow rowData = new FTableConditionRow();
        rowData.setKey(OrderGenerator.newOrder());
        rowData.setCode(filterData.getCode());
        rowData.setTitle(filterData.getTitle());
        rowData.setType("condition");
        rowData.setOrder(order);
        return rowData;
    }

    protected FTableUnitRow unitRow(IEntityRow row, boolean exactMatch) {
        FTableUnitRow rowData = new FTableUnitRow();
        rowData.setKey(row.getEntityKeyData());
        rowData.setCode(row.getCode());
        rowData.setTitle(row.getTitle());
        rowData.setType("unit");
        rowData.setChecked(exactMatch);
        return rowData;
    }

    protected FTableUnitRow showMoreRow() {
        FTableUnitRow rowData = new FTableUnitRow();
        rowData.setKey(OrderGenerator.newOrder());
        rowData.setCode("");
        rowData.setTitle("\u5339\u914d\u7ed3\u679c\u592a\u591a\uff0c\u5bfc\u51fa\u67e5\u770b\u66f4\u591a\u5339\u914d\u7ed3\u679c\uff01");
        rowData.setType("more");
        return rowData;
    }
}

