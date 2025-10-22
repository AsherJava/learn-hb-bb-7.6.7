/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.cache.graph.TableNode
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 */
package com.jiuqi.nr.bql.interpret.var;

import com.jiuqi.bi.adhoc.cache.graph.TableNode;
import com.jiuqi.bi.adhoc.model.TimeGranularity;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.bql.interpret.BiAdaptContext;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;

public abstract class BiAdaptVariable
extends Variable {
    private static final long serialVersionUID = 7935993784277231687L;

    public BiAdaptVariable(String varName, String varTitle, int dataType) {
        super(varName, varTitle, dataType);
    }

    public void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
    }

    protected String getPeriodTableName(IContext context) throws InterpretException {
        TableNode periodTable = this.getPeriodTableNode(context);
        if (periodTable != null) {
            return periodTable.getTableName();
        }
        throw new InterpretException("\u6ca1\u6709\u627e\u5230\u65f6\u671f\u7ef4\u5ea6\u8868");
    }

    protected String getPeriodTypeField(IContext context) throws InterpretException {
        TableNode periodTable = this.getPeriodTableNode(context);
        if (periodTable != null) {
            if (periodTable.getTimeGranularity() == TimeGranularity.YEAR) {
                return PeriodTableColumn.YEAR.getCode();
            }
            if (periodTable.getTimeGranularity() == TimeGranularity.QUARTER) {
                return PeriodTableColumn.QUARTER.getCode();
            }
            if (periodTable.getTimeGranularity() == TimeGranularity.MONTH) {
                return PeriodTableColumn.MONTH.getCode();
            }
            if (periodTable.getTimeGranularity() == TimeGranularity.DAY) {
                return PeriodTableColumn.DAY.getCode();
            }
        }
        throw new InterpretException("\u6ca1\u6709\u627e\u5230\u65f6\u671f\u7ef4\u5ea6\u5b57\u6bb5");
    }

    protected TableNode getUnitTableNode(IContext context) throws InterpretException {
        BiAdaptContext aContext = (BiAdaptContext)context;
        TableNode unitTableNode = aContext.getUnitTableNode();
        if (unitTableNode == null) {
            throw new InterpretException("\u6ca1\u6709\u627e\u5230\u4e3b\u7ef4\u5ea6\u8868");
        }
        return unitTableNode;
    }

    protected TableNode getPeriodTableNode(IContext context) {
        BiAdaptContext aContext = (BiAdaptContext)context;
        return aContext.getPeriodTableNode();
    }
}

