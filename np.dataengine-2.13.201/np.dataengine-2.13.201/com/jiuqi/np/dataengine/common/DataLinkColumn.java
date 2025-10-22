/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataColumn;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import java.io.Serializable;
import java.util.List;

@Deprecated
public class DataLinkColumn
extends DataColumn
implements Comparable<DataLinkColumn>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 2727520880229875720L;
    private ReportInfo reportInfo;
    private String dataLinkCode;
    private Position gridPosition;
    private Position dataPosition;
    private NodeShowInfo showInfo;
    private String region;
    private List<String> expandDims;

    public DataLinkColumn(FieldDefine field) {
        super(field);
    }

    public DataLinkColumn(FieldDefine field, PeriodModifier periodModifier, DimensionValueSet dimensionRestriction) {
        super(field, periodModifier, dimensionRestriction);
    }

    public DataLinkColumn(DataModelLinkColumn column) {
        super(column.getExpression());
        this.reportInfo = column.getReportInfo();
        this.dataLinkCode = column.getDataLinkCode();
        this.gridPosition = column.getGridPosition();
        this.dataPosition = column.getDataPosition();
        this.showInfo = column.getShowInfo();
        this.region = column.getRegion();
        this.expandDims = column.getExpandDims();
        this.type = column.getType();
        this.dimensionRestriction = column.getDimensionRestriction();
        this.periodModifier = column.getPeriodModifier();
        this.entityViewDefine = column.getEntityViewDefine();
        try {
            IColumnModelFinder finder = (IColumnModelFinder)SpringBeanProvider.getBean(IColumnModelFinder.class);
            this.field = finder.findFieldDefine(column.getColumModel());
            if (column.getValueField() != null) {
                this.valueField = finder.findFieldDefine(column.getValueField());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public DataLinkColumn(String expression) {
        super(expression);
    }

    public String getDataLinkCode() {
        return this.dataLinkCode;
    }

    public void setDataLinkCode(String dataLinkCode) {
        this.dataLinkCode = dataLinkCode;
    }

    public Position getGridPosition() {
        return this.gridPosition;
    }

    public Position getDataPosition() {
        return this.dataPosition;
    }

    public void setGridPosition(Position gridPosition) {
        this.gridPosition = gridPosition;
    }

    public void setDataPosition(Position dataPosition) {
        this.dataPosition = dataPosition;
    }

    public Position getPosition(ExecutorContext executorContext) {
        return executorContext.isJQReportModel() ? this.dataPosition : this.gridPosition;
    }

    public ReportInfo getReportInfo() {
        return this.reportInfo;
    }

    public void setReportInfo(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public NodeShowInfo getShowInfo() {
        if (this.showInfo == null) {
            this.showInfo = new NodeShowInfo();
        }
        return this.showInfo;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setShowInfo(NodeShowInfo showInfo) {
        this.showInfo = showInfo;
    }

    public List<String> getExpandDims() {
        return this.expandDims;
    }

    public void setExpandDims(List<String> expandDims) {
        this.expandDims = expandDims;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + (this.dataLinkCode == null ? 0 : this.dataLinkCode.hashCode());
        result = 31 * result + (this.dataPosition == null ? 0 : this.dataPosition.hashCode());
        result = 31 * result + (this.gridPosition == null ? 0 : this.gridPosition.hashCode());
        result = 31 * result + (this.reportInfo == null ? 0 : this.reportInfo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DataLinkColumn other = (DataLinkColumn)obj;
        if (this.dataLinkCode == null ? other.dataLinkCode != null : !this.dataLinkCode.equals(other.dataLinkCode)) {
            return false;
        }
        if (this.dataPosition == null ? other.dataPosition != null : !this.dataPosition.equals((Object)other.dataPosition)) {
            return false;
        }
        if (this.gridPosition == null ? other.gridPosition != null : !this.gridPosition.equals((Object)other.gridPosition)) {
            return false;
        }
        return !(this.reportInfo == null ? other.reportInfo != null : !this.reportInfo.equals(other.reportInfo));
    }

    @Override
    public int compareTo(DataLinkColumn o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public String toString() {
        if (this.reportInfo != null) {
            return this.reportInfo.getReportName() + "[" + (this.dataPosition == null ? this.expression : this.dataPosition.row() + "," + this.dataPosition.col()) + "]" + (this.showInfo != null && !StringUtils.isEmpty((String)this.showInfo.getEndAppend()) ? this.showInfo.getEndAppend() : "");
        }
        return super.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        DataLinkColumn cloneColumn = (DataLinkColumn)super.clone();
        cloneColumn.setShowInfo(new NodeShowInfo());
        return cloneColumn;
    }
}

