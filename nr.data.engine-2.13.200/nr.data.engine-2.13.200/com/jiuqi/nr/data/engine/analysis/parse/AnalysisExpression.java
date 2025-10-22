/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;

public class AnalysisExpression
extends CalcExpression {
    private static final long serialVersionUID = -5491872491189242028L;
    private IASTNode rightNode;
    private boolean dependsOther;
    private boolean isCalc;
    private int colConditionIndex = -1;
    private int rowConditionIndex = -1;

    public AnalysisExpression(IExpression expression, Formula source, DynamicDataNode assignNode, IASTNode rightNode, int index) {
        super(expression, source, assignNode, index);
        this.rightNode = rightNode;
    }

    public IASTNode getRightNode() {
        return this.rightNode;
    }

    public void setRightNode(IASTNode rightNode) {
        this.rightNode = rightNode;
    }

    public boolean isCalc() {
        return this.isCalc;
    }

    public void setCalc(boolean isCalc) {
        this.isCalc = isCalc;
    }

    public boolean isDependsOther() {
        return this.dependsOther;
    }

    public void setDependsOther(boolean dependsOther) {
        this.dependsOther = dependsOther;
    }

    public int getColConditionIndex() {
        return this.colConditionIndex;
    }

    public void setColConditionIndex(int colConditionIndex) {
        this.colConditionIndex = colConditionIndex;
    }

    public int getRowConditionIndex() {
        return this.rowConditionIndex;
    }

    public void setRowConditionIndex(int rowConditionIndex) {
        this.rowConditionIndex = rowConditionIndex;
    }

    public int execute(IContext context) throws SyntaxException {
        boolean result = true;
        if (context instanceof AnalysisContext) {
            AnalysisContext aContext = (AnalysisContext)context;
            if (this.rowConditionIndex >= 0) {
                boolean bl = result = result && aContext.getConditionJuder().judge(aContext, this.rowConditionIndex);
            }
            if (this.colConditionIndex >= 0) {
                boolean bl = result = result && aContext.getConditionJuder().judge(aContext, this.colConditionIndex);
            }
        }
        if (result) {
            return super.execute(context);
        }
        return 0;
    }
}

