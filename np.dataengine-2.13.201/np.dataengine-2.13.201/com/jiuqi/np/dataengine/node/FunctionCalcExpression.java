/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.parse.ReadWriteInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public class FunctionCalcExpression
extends CalcExpression {
    private AdvanceFunction function;
    private ReadWriteInfo readWriteInfo;
    private static final long serialVersionUID = -6537890602416612115L;

    public FunctionCalcExpression(QueryContext context, IExpression expression, Formula source, FunctionNode funcNode, int index) {
        super(expression, source, null, index);
        this.function = (AdvanceFunction)funcNode.getDefine();
        List parameters = funcNode.getParameters();
        this.readWriteInfo = this.function.getReadWriteInfo(context, parameters);
    }

    public AdvanceFunction getFunction() {
        return this.function;
    }

    public void setFunction(AdvanceFunction function) {
        this.function = function;
    }

    @Override
    public QueryFields getWriteQueryFields() {
        return this.readWriteInfo == null ? null : this.readWriteInfo.getWriteQueryFields();
    }

    @Override
    protected void initQueryFields(QueryContext qContext) {
        super.initQueryFields(qContext);
        if (this.readWriteInfo != null) {
            this.queryFields.addAll(this.readWriteInfo.getReadQueryFields());
        }
    }

    @Override
    public QueryTable getWriteTable() {
        return this.readWriteInfo == null ? null : this.readWriteInfo.getWriteTable();
    }

    public boolean isClearTable() {
        return this.readWriteInfo == null ? null : Boolean.valueOf(this.readWriteInfo.isClearTable());
    }
}

