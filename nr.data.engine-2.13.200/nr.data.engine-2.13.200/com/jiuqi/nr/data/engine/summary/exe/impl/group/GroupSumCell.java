/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.nr.data.engine.summary.exe.impl.group;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.common.SumEngineConsts;
import com.jiuqi.nr.data.engine.summary.exe.SumCell;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupSumCell
extends SumCell
implements Comparable<GroupSumCell> {
    private SumCell cell;
    private StatUnit statUnit;
    private int dataType;
    private int resultSetIndex;

    public GroupSumCell(SumCell cell, int resultSetIndex) {
        this.cell = cell;
        this.resultSetIndex = resultSetIndex;
        this.dataType = DataTypesConvert.fieldTypeToDataType((FieldType)cell.getSrcField().getType());
        this.statUnit = StatItem.createStatUnit((int)this.transSumType(cell.getSumType()), (int)this.dataType);
    }

    private int transSumType(int sumType) {
        if (sumType == SumEngineConsts.SumType.SUM.getValue() || sumType == SumEngineConsts.SumType.COUNT.getValue()) {
            return 1;
        }
        if (sumType == SumEngineConsts.SumType.MAX.getValue()) {
            return 4;
        }
        if (sumType == SumEngineConsts.SumType.MIN.getValue()) {
            return 5;
        }
        return sumType;
    }

    public void runStatistic(ResultSet rs) {
        try {
            Object value = rs.getObject(this.resultSetIndex);
            this.statUnit.statistic(AbstractData.valueOf((Object)value, (int)this.dataType));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setResultToUpdator(IDataRow row) throws IncorrectQueryException, SQLException {
        Object value = this.getSumValue();
        if (this.cell.getDivider() != 0.0) {
            double doubleValue = Convert.toDouble((Object)value);
            value = doubleValue / this.cell.getDivider();
        }
        row.setValue(this.cell.getDestField(), value);
    }

    public Object getSumValue() {
        return this.statUnit.getResult().getAsObject();
    }

    public SumCell getCell() {
        return this.cell;
    }

    public int getResultSetIndex() {
        return this.resultSetIndex;
    }

    public void setResultSetIndex(int resultSetIndex) {
        this.resultSetIndex = resultSetIndex;
    }

    @Override
    public int getRow() {
        return this.cell.getRow();
    }

    @Override
    public int getCol() {
        return this.cell.getCol();
    }

    @Override
    public String getRowCondition() {
        return this.cell.getRowCondition();
    }

    @Override
    public String getColCondition() {
        return this.cell.getColCondition();
    }

    @Override
    public String getSumExp() {
        return this.cell.getSumExp();
    }

    @Override
    public int getSumType() {
        return this.cell.getSumType();
    }

    @Override
    public String getCustomizeFormula() {
        return this.cell.getCustomizeFormula();
    }

    @Override
    public void setRowCondition(String rowCondition) {
        this.cell.setRowCondition(rowCondition);
    }

    @Override
    public void setColCondition(String colCondition) {
        this.cell.setColCondition(colCondition);
    }

    @Override
    public String getCondition() {
        return this.cell.getCondition();
    }

    @Override
    public IASTNode getParsedExp() {
        return this.cell.getParsedExp();
    }

    @Override
    public void setParsedExp(IASTNode parsedExp) {
        this.cell.setParsedExp(parsedExp);
    }

    @Override
    public FieldDefine getSrcField() {
        return this.cell.getSrcField();
    }

    @Override
    public FieldDefine getDestField() {
        return this.cell.getDestField();
    }

    @Override
    public Object getValue() {
        return this.cell.getValue();
    }

    @Override
    public String getAlias() {
        return this.cell.getAlias();
    }

    @Override
    public String getPeriodOffSet() {
        return this.cell.getPeriodOffSet();
    }

    @Override
    public void setPeriodOffSet(String periodOffSet) {
        this.cell.setPeriodOffSet(periodOffSet);
    }

    @Override
    public void setValue(String value) {
        this.cell.setValue(value);
    }

    @Override
    public double getDivider() {
        return this.cell.getDivider();
    }

    @Override
    public void setDivider(double divider) {
        this.cell.setDivider(divider);
    }

    @Override
    public int compareTo(GroupSumCell o) {
        int result = this.getRow() - o.getRow();
        if (result == 0) {
            result = this.getCol() - o.getCol();
        }
        return result;
    }
}

