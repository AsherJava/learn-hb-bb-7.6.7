/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.summary.executor.sum.engine.model;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.summary.executor.SummaryExecuteException;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryCondition;
import com.jiuqi.nr.summary.model.cell.DataCell;
import java.util.ArrayList;
import java.util.List;

public class RuntimeSummaryCell
implements Cloneable {
    private DataCell cellDefine;
    private IExpression evalExpression;
    private FieldDefine targetColumn;
    private List<SummaryCondition> conditions = new ArrayList<SummaryCondition>();
    private int targetFieldIndex = -1;
    private int resultDataType;
    private int statKind = 1;
    private static final int[] STAT_KINDS = new int[]{0, 1, 2, 3, 5, 4, 1, 9};

    public void doInit(SumContext sumContext) throws Exception {
        this.evalExpression = sumContext.getFormulaParser().parseEval(this.cellDefine.getExp(), (IContext)sumContext);
        this.resultDataType = this.evalExpression.getType((IContext)sumContext);
        this.targetColumn = sumContext.getBeanSet().dataDefinitionController.queryFieldDefine(this.cellDefine.getSummaryZb().getFieldKey());
        if (this.targetColumn == null) {
            throw new SummaryExecuteException("\u6839\u636ekey\u3010" + this.cellDefine.getSummaryZb().getFieldKey() + " \u3011\u6ca1\u6709\u627e\u5230\u6c47\u603b\u76ee\u6807\u5b57\u6bb5");
        }
        if (this.cellDefine.getSummaryMode() != null) {
            this.statKind = STAT_KINDS[this.cellDefine.getSummaryMode().getValue()];
        }
    }

    public RuntimeSummaryCell(DataCell cellDefine) {
        this.cellDefine = cellDefine;
    }

    public DataCell getCellDefine() {
        return this.cellDefine;
    }

    public void addCondition(SummaryCondition condition) {
        if (condition != null) {
            this.conditions.add(condition);
        }
    }

    public List<SummaryCondition> getConditions() {
        return this.conditions;
    }

    public Object evaluate(SumContext sumContext) throws SyntaxException {
        return this.evalExpression.evaluate((IContext)sumContext);
    }

    public FieldDefine getTargetColumn() {
        return this.targetColumn;
    }

    public int getTargetFieldIndex() {
        return this.targetFieldIndex;
    }

    public void setTargetFieldIndex(int targetFieldIndex) {
        this.targetFieldIndex = targetFieldIndex;
    }

    public int getRow() {
        return this.cellDefine.getX();
    }

    public int getCol() {
        return this.cellDefine.getY();
    }

    public String getTableCode() {
        return this.cellDefine.getSummaryZb().getTableName();
    }

    public int getResultDataType() {
        return this.resultDataType;
    }

    public int getStatKind() {
        return this.statKind;
    }

    public IExpression getEvalExpression() {
        return this.evalExpression;
    }

    protected Object clone() throws CloneNotSupportedException {
        RuntimeSummaryCell newCell = new RuntimeSummaryCell(this.cellDefine);
        newCell.targetFieldIndex = this.targetFieldIndex;
        newCell.evalExpression = this.evalExpression;
        newCell.conditions = new ArrayList<SummaryCondition>(this.conditions);
        newCell.resultDataType = this.resultDataType;
        newCell.statKind = this.statKind;
        newCell.targetColumn = this.targetColumn;
        return newCell;
    }

    public String toString() {
        return this.getTargetExp() + ":[" + this.cellDefine.getX() + "," + this.cellDefine.getY() + "]=" + this.cellDefine.getExp();
    }

    public String getTargetExp() {
        return this.cellDefine.getSummaryZb().getTableName() + "." + this.cellDefine.getSummaryZb().getName();
    }
}

