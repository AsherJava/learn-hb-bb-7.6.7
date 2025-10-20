/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.formula.intf.FieldNode
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 */
package com.jiuqi.va.filter.bill.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.filter.bill.formula.OrgFormulaContext;
import com.jiuqi.va.formula.intf.FieldNode;
import com.jiuqi.va.formula.intf.FunRefDataDefine;

public class OrgFieldNode
extends DynamicNode
implements FieldNode {
    private static final long serialVersionUID = 1348943564522987149L;
    private int datatype;
    public final FunRefDataDefine refDataDefine;

    public OrgFieldNode(Token token, FunRefDataDefine refDataDefine) {
        super(token);
        this.refDataDefine = refDataDefine;
    }

    public int getType(IContext context) throws SyntaxException {
        return 33 == this.refDataDefine.getDataType() ? 6 : this.refDataDefine.getDataType();
    }

    public Object evaluate(IContext context) throws SyntaxException {
        OrgFormulaContext formulaContext = (OrgFormulaContext)context;
        Object dataNode = formulaContext.getOrg().get((Object)this.refDataDefine.getFieldName().toLowerCase());
        if (dataNode == null) {
            return null;
        }
        return formulaContext.valueOf(dataNode, this.getType(context));
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("[%s]", this.refDataDefine.getFieldName()));
    }

    public String getName() {
        return this.refDataDefine.getFieldName();
    }

    public void setDataType(int datatype) {
        this.datatype = datatype;
    }

    public int getDataType() {
        return this.datatype;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        buffer.append(String.format("%s.%s", this.refDataDefine.getTableName(), this.refDataDefine.getFieldName()));
    }
}

