/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 */
package com.jiuqi.bde.base.formula.node;

import com.jiuqi.bde.base.formula.FetchFormulaContext;
import com.jiuqi.bde.base.formula.node.BillFetchCondiNode;
import com.jiuqi.bde.base.formula.node.FetchBillNode;
import com.jiuqi.bde.base.formula.node.FetchDataCompNode;
import com.jiuqi.bde.base.formula.node.FetchEnvNode;
import com.jiuqi.bde.base.formula.node.FetchFloatNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import java.util.List;

public class FetchFormulaNodeProvider
implements IReportDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        return null;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        if (!(context instanceof FetchFormulaContext)) {
            throw new DynamicNodeException("\u4e0a\u4e0b\u6587\u4fe1\u606f\u9519\u8bef\uff1a" + context);
        }
        FetchFormulaContext formulaContext = (FetchFormulaContext)context;
        if (objPath.size() != 2) {
            throw new DynamicNodeException("\u672a\u77e5\u7684\u8bed\u6cd5\u6811\u8282\u70b9\uff1a" + token);
        }
        String arg0 = objPath.get(0);
        String arg1 = objPath.get(1);
        if (arg0.equalsIgnoreCase("ENV")) {
            return new FetchEnvNode((IContext)formulaContext, token, arg1);
        }
        if (arg0.equalsIgnoreCase("FLOAT")) {
            return new FetchFloatNode((IContext)formulaContext, token, arg1);
        }
        if (arg0.equalsIgnoreCase("BILL")) {
            return new FetchBillNode((IContext)formulaContext, token, arg1);
        }
        if (arg0.equalsIgnoreCase("FETCH_CONDI")) {
            return new BillFetchCondiNode((IContext)formulaContext, token, arg1);
        }
        return new FetchDataCompNode((IContext)formulaContext, token, arg1);
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpec(IContext arg0, Token arg1, String arg2, String arg3) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpecial(IContext arg0, Token arg1, String arg2) throws DynamicNodeException {
        return null;
    }
}

