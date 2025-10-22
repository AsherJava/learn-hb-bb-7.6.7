/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.parse.ReadWriteInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;

public class CalcExpression
extends CheckExpression
implements Comparable<CalcExpression> {
    private static final long serialVersionUID = 781113796117481080L;
    private DynamicDataNode assignNode;
    private int index;
    private boolean needExpand = false;
    private String extendAssignKey;
    private QueryFields writeQueryFields;

    public CalcExpression(IExpression expression, Formula source, DynamicDataNode assignNode, int index) {
        super(expression, source);
        this.assignNode = assignNode;
        this.index = index;
    }

    @Override
    public QueryFields getWriteQueryFields() throws UnknownReadWriteException {
        if (this.unknownReadWriteFunc != null) {
            throw new UnknownReadWriteException("\u51fd\u6570" + this.unknownReadWriteFunc.name() + "()\u7684\u8bfb\u5199\u8303\u56f4\u672a\u77e5");
        }
        return this.writeQueryFields;
    }

    public QueryFields getParsedWriteFields() {
        return this.writeQueryFields;
    }

    @Override
    protected void initQueryFields(QueryContext qContext) {
        super.initQueryFields(qContext);
        this.initAssignQueryFields(qContext, this.getRealExpression().getChild(0));
    }

    public QueryTable getWriteTable() throws UnknownReadWriteException {
        QueryFields writeQueryFields = this.getWriteQueryFields();
        if (writeQueryFields != null && writeQueryFields.getCount() > 0) {
            return writeQueryFields.getItem(0).getTable();
        }
        return null;
    }

    @Override
    public DynamicDataNode getAssignNode() {
        return this.assignNode;
    }

    public void setAssignNode(DynamicDataNode assignNode) {
        this.assignNode = assignNode;
    }

    @Override
    public DataEngineConsts.FormulaType getFormulaType() {
        return DataEngineConsts.FormulaType.CALCULATE;
    }

    @Override
    public int compareTo(CalcExpression o) {
        return this.getIndex() - o.getIndex();
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isNeedExpand() {
        return this.needExpand;
    }

    public void setNeedExpand(boolean needExpand) {
        this.needExpand = needExpand;
    }

    public String getExtendAssignKey() {
        return this.extendAssignKey;
    }

    public void setExtendAssignKey(String extendAssignKey) {
        this.extendAssignKey = extendAssignKey;
    }

    @Override
    public Object clone() {
        CalcExpression newExpression = new CalcExpression((IExpression)this.getRealExpression().clone(), this.getSource(), this.assignNode, this.index);
        return newExpression;
    }

    private void initAssignQueryFields(QueryContext qContext, IASTNode node) {
        this.writeQueryFields = new QueryFields();
        for (IASTNode child : node) {
            if (child instanceof IfThenElse) {
                for (int i = 1; i < child.childrenSize(); ++i) {
                    IASTNode thenElseNode = child.getChild(i);
                    this.initAssignQueryFields(qContext, thenElseNode);
                }
                break;
            }
            if (child instanceof Equal) {
                IASTNode leftNode = child.getChild(0);
                for (IASTNode leftChild : leftNode) {
                    if (!(leftChild instanceof DynamicDataNode)) continue;
                    this.writeQueryFields.add(((DynamicDataNode)leftChild).getQueryField());
                }
                continue;
            }
            if (!(child instanceof FunctionNode)) continue;
            FunctionNode funcNode = (FunctionNode)child;
            IFunction func = funcNode.getDefine();
            DataEngineConsts.FuncReadWriteType funcType = DataEngineConsts.FuncReadWriteType.UNKNOWN;
            if (func instanceof IReportFunction) {
                IReportFunction reportFunc = (IReportFunction)func;
                funcType = reportFunc.getReadWriteType();
                if (funcType == DataEngineConsts.FuncReadWriteType.CUSTOM) {
                    ReadWriteInfo readWriteInfo = reportFunc.getReadWriteInfo(qContext, funcNode.getParameters());
                    if (readWriteInfo == null) {
                        this.unknownReadWriteFunc = func;
                    } else {
                        this.writeQueryFields.addAll(readWriteInfo.getWriteQueryFields());
                    }
                }
            } else if (DataEngineFormulaParser.innerFunctions.contains(func.name())) {
                funcType = DataEngineConsts.FuncReadWriteType.AUTO;
            }
            if (funcType != DataEngineConsts.FuncReadWriteType.UNKNOWN) continue;
            this.unknownReadWriteFunc = func;
        }
    }
}

