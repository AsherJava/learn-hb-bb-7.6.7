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
 */
package com.jiuqi.bde.base.formula.node;

import com.jiuqi.bde.base.formula.FetchFormulaContext;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public class BillFetchCondiNode
extends DynamicNode {
    private static final long serialVersionUID = 4192482166340810926L;
    public static final String FN_BILL_FETCH_CONDI = "FETCH_CONDI";
    public final String constantType;

    public BillFetchCondiNode(IContext context, Token token, String constantType) {
        super(token);
        this.constantType = constantType;
    }

    public int getType(IContext iContext) throws SyntaxException {
        FetchFormulaContext formulaContext = (FetchFormulaContext)iContext;
        Object val = formulaContext.getEnvParamMap().get(this.constantType);
        if (val == null) {
            return 0;
        }
        if (val instanceof String) {
            return 6;
        }
        if (val instanceof Integer || val instanceof Double) {
            return 3;
        }
        return 6;
    }

    public Object evaluate(IContext iContext) throws SyntaxException {
        FetchFormulaContext formulaContext = (FetchFormulaContext)iContext;
        return formulaContext.getEnvParamMap().get(this.constantType);
    }

    public void toString(StringBuilder stringBuilder) {
        stringBuilder.append(String.format("%s[%s]", FN_BILL_FETCH_CONDI, this.constantType));
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        throw new InterpretException(String.format("\u672a\u652f\u6301SQL\u8f6c\u6362%s[%s]", FN_BILL_FETCH_CONDI, this.constantType));
    }
}

