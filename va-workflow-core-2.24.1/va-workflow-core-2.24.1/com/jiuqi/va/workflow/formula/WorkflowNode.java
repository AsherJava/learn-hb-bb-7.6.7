/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.formula.intf.FieldNode
 */
package com.jiuqi.va.workflow.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.formula.intf.FieldNode;
import com.jiuqi.va.workflow.formula.FormulaParam;
import com.jiuqi.va.workflow.formula.WorkflowContext;

public class WorkflowNode
extends DynamicNode
implements FieldNode {
    private static final long serialVersionUID = 1L;
    private String field;
    private int dataType;

    public WorkflowNode(Token token, String field) {
        super(token);
        this.field = field;
    }

    public int getType(IContext context) throws SyntaxException {
        return 0;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        WorkflowContext workflowContext = (WorkflowContext)context;
        Object dataNode = workflowContext.get(this.field);
        if (dataNode instanceof FormulaParam) {
            return ((FormulaParam)Convert.cast((Object)dataNode, FormulaParam.class)).getValue();
        }
        return dataNode;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("@%s", this.field));
    }

    public String getName() {
        return this.field;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getDataType() {
        return this.dataType;
    }
}

