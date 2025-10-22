/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DimensionNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.RestrictInfo
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DimensionNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.interpret.BiAdaptTable;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import java.util.List;

public class BiAdaptNode
extends ASTNode {
    private static final long serialVersionUID = 8428675663561568518L;
    private BiAdaptTable table;
    private DataField dataField;
    private PeriodModifier periodModifier;
    private DimensionValueSet dimRestriction;
    private IASTNode condition;
    private int statKind = -1;

    public BiAdaptNode(BiAdaptTable table, DataField dataField) {
        super(null);
        this.dataField = dataField;
        this.table = table;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return null;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        int dataType = this.dataField.getDataFieldType().getValue();
        if (this.statKind == 2) {
            dataType = 3;
        } else if (dataType == DataFieldType.DATE.getValue() || dataType == DataFieldType.DATE_TIME.getValue()) {
            dataType = 2;
        } else if (dataType == DataFieldType.INTEGER.getValue()) {
            dataType = 3;
        } else if (dataType == DataFieldType.BIGDECIMAL.getValue()) {
            dataType = 10;
        } else if (dataType == 34) {
            dataType = 6;
        }
        return dataType;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        String tableCode = this.table.getCode();
        if (this.isSimple()) {
            buffer.append(tableCode).append(".").append(this.dataField.getCode());
        } else {
            buffer.append("[").append(tableCode).append(".").append(this.dataField.getCode());
            String periodTableName = this.table.getPeriodTableName();
            if (this.periodModifier != null) {
                if (this.periodModifier.getYearModifier() != 0) {
                    this.appendYearOffSet(buffer, periodTableName);
                }
                if (this.periodModifier.getPeriodModifier() != 0 && this.periodModifier.getPeriodType() != PeriodType.YEAR.type()) {
                    this.appendPeriodOffSet(buffer, periodTableName);
                }
            }
            if (this.dimRestriction != null) {
                this.appendDimRestriction(buffer, tableCode, periodTableName);
            }
            if (this.condition != null) {
                buffer.append(",");
                this.condition.interpret(context, buffer, Language.FORMULA, info);
            }
            buffer.append("]");
            if (this.statKind >= 0) {
                this.surroundWithStatFun(buffer);
            }
        }
    }

    protected void appendDimRestriction(StringBuilder buffer, String tableCode, String periodTableName) {
        for (int i = 0; i < this.dimRestriction.size(); ++i) {
            buffer.append(",");
            String dimName = this.dimRestriction.getName(i);
            Object value = this.dimRestriction.getValue(i);
            if (dimName.equals("DATATIME")) {
                buffer.append(periodTableName).append(".").append(PeriodTableColumn.TIMEKEY.getCode());
                buffer.append("=\"").append(TimeDimUtils.periodToTimeKey((String)value.toString())).append("\"");
                continue;
            }
            DataField dimField = this.table.getDimField(dimName);
            buffer.append(tableCode).append(".").append(dimField.getCode()).append("=");
            if (dimField.getDataFieldType() == DataFieldType.STRING) {
                buffer.append("\"").append(value).append("\"");
                continue;
            }
            buffer.append(value);
        }
    }

    protected void appendYearOffSet(StringBuilder buffer, String periodTableName) {
        buffer.append(",");
        int yearOffSet = this.periodModifier.getYearModifier();
        if (yearOffSet < 0) {
            buffer.append("lag(");
            buffer.append(periodTableName).append(".").append(PeriodTableColumn.YEAR.getCode());
            buffer.append(",").append(0 - yearOffSet);
            buffer.append(")");
        } else if (this.periodModifier.getYearFlag() == 1) {
            buffer.append(periodTableName).append(".").append(PeriodTableColumn.YEAR.getCode());
            buffer.append("=").append(yearOffSet);
        } else {
            buffer.append("lead(");
            buffer.append(periodTableName).append(".").append(PeriodTableColumn.YEAR.getCode());
            buffer.append(",").append(yearOffSet);
            buffer.append(")");
        }
    }

    protected void appendPeriodOffSet(StringBuilder buffer, String periodTableName) throws InterpretException {
        buffer.append(",");
        int periodOffSet = this.periodModifier.getPeriodModifier();
        String periodColumn = this.getPeriodColumn(this.periodModifier.getPeriodType());
        if (periodColumn == null) {
            throw new InterpretException("\u6682\u4e0d\u652f\u6301\u65f6\u671f\u7c7b\u578b\u4e3a" + PeriodType.fromType((int)this.periodModifier.getPeriodType()).title() + "\u7684\u65f6\u671f\u504f\u79fb");
        }
        if (periodOffSet < 0) {
            buffer.append("lag(");
            buffer.append(periodTableName).append(".").append(periodColumn);
            buffer.append(",").append(0 - periodOffSet);
            buffer.append(")");
        } else if (this.periodModifier.getPeriodFlag() == 1) {
            buffer.append(periodTableName).append(".").append(periodColumn);
            buffer.append("=").append(periodOffSet);
        } else {
            buffer.append("lead(");
            buffer.append(periodTableName).append(".").append(periodColumn);
            buffer.append(",").append(periodOffSet);
            buffer.append(")");
        }
    }

    public void bindRestriction(RestrictInfo info) throws DynamicNodeException {
        this.dimRestriction = this.getDimRestriction(info);
        if (info.conditionNode != null) {
            this.condition = info.conditionNode;
        }
        if (info.statKind >= 0) {
            this.statKind = info.statKind;
        }
        this.periodModifier = info.periodModifier;
    }

    private DimensionValueSet getDimRestriction(RestrictInfo info) throws DynamicNodeException {
        List dimesionNodes = info.getDimesionNodes();
        if (dimesionNodes == null || dimesionNodes.isEmpty()) {
            return null;
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (DimensionNode dimesionNode : dimesionNodes) {
            String dimensionName = dimesionNode.getDimesionName().toUpperCase();
            DataField dimField = this.table.getDimField(dimensionName);
            if (dimField == null) {
                throw new DynamicNodeException("\u7ef4\u5ea6\u6807\u8bc6[" + dimesionNode.getDimesionName() + "]\u5728[" + this.table.getCode() + "]\u8868\u4e2d\u6ca1\u6709\u627e\u5230\u5b57\u6bb5\u5b57\u4e49");
            }
            dimensionValueSet.setValue(dimensionName, dimesionNode.getDimesionValue());
        }
        return dimensionValueSet;
    }

    private String getPeriodColumn(int periodType) {
        if (periodType == PeriodType.SEASON.type()) {
            return PeriodTableColumn.QUARTER.getCode();
        }
        if (periodType == PeriodType.MONTH.type()) {
            return PeriodTableColumn.MONTH.getCode();
        }
        if (periodType == PeriodType.DAY.type()) {
            return PeriodTableColumn.DAY.getCode();
        }
        return null;
    }

    private void surroundWithStatFun(StringBuilder buffer) {
        switch (this.statKind) {
            case 1: {
                buffer.insert(0, "sum(");
                break;
            }
            case 2: {
                buffer.insert(0, "count(");
                break;
            }
            case 3: {
                buffer.insert(0, "avg(");
                break;
            }
            case 6: {
                buffer.insert(0, "first(");
                break;
            }
            case 7: {
                buffer.insert(0, "last(");
                break;
            }
            case 4: {
                buffer.insert(0, "max(");
                break;
            }
            case 5: {
                buffer.insert(0, "min(");
            }
        }
        buffer.append(")");
    }

    private boolean isSimple() {
        return this.periodModifier == null && this.condition == null && this.dimRestriction == null && this.statKind < 0;
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
        try {
            this.toFormula(null, buffer, null);
        }
        catch (InterpretException e) {
            e.printStackTrace();
        }
    }

    public IASTNode getCondition() {
        return this.condition;
    }

    public void setCondition(IASTNode condition) {
        this.condition = condition;
    }

    public int getStatKind() {
        return this.statKind;
    }

    public void setStatKind(int statKind) {
        this.statKind = statKind;
    }

    public DataField getDataField() {
        return this.dataField;
    }

    public void setDataField(DataField dataField) {
        this.dataField = dataField;
    }

    public PeriodModifier getPeriodModifier() {
        return this.periodModifier;
    }

    public void setPeriodModifier(PeriodModifier periodModifier) {
        this.periodModifier = periodModifier;
    }

    public DimensionValueSet getDimRestriction() {
        return this.dimRestriction;
    }

    public void setDimRestriction(DimensionValueSet dimRestriction) {
        this.dimRestriction = dimRestriction;
    }

    public BiAdaptTable getTable() {
        return this.table;
    }

    public boolean isStat() {
        return this.statKind >= 0;
    }
}

