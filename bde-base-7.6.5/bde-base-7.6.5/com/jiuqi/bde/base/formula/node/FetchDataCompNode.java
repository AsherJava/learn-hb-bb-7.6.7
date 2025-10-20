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
import java.math.BigDecimal;

public class FetchDataCompNode
extends DynamicNode {
    private static final long serialVersionUID = 1L;
    public final String settingKey;

    public FetchDataCompNode(IContext context, Token token, String settingKey) {
        super(token);
        this.settingKey = settingKey;
    }

    public int getType(IContext context) throws SyntaxException {
        FetchFormulaContext formulaContext = (FetchFormulaContext)context;
        if (formulaContext.getFetchResultMap() == null || formulaContext.getFetchResultMap().isEmpty()) {
            return 0;
        }
        Object val = formulaContext.getFetchResultMap().get(this.settingKey);
        if (val == null) {
            return 0;
        }
        if (val instanceof String) {
            return 6;
        }
        if (val instanceof Integer || val instanceof Double || val instanceof BigDecimal) {
            return 10;
        }
        return 6;
    }

    public Object evaluate(IContext context) {
        FetchFormulaContext formulaContext = (FetchFormulaContext)context;
        if (formulaContext.getFetchResultMap() == null || formulaContext.getFetchResultMap().isEmpty()) {
            return 0.0;
        }
        Object val = formulaContext.getFetchResultMap().get(this.settingKey);
        if (val == null) {
            return null;
        }
        if (val instanceof String) {
            return String.valueOf(val);
        }
        if (val instanceof Integer || val instanceof Double) {
            return new BigDecimal(String.valueOf(val));
        }
        return val;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("\u4e1a\u52a1\u6a21\u578b[%s]", this.settingKey));
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        throw new InterpretException(String.format("\u672a\u652f\u6301SQL\u8f6c\u6362\u4e1a\u52a1\u6a21\u578b[%s]", this.settingKey));
    }
}

