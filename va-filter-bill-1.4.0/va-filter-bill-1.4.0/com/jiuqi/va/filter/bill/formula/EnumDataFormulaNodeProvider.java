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
package com.jiuqi.va.filter.bill.formula;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.va.filter.bill.formula.EnumDataFieldNode;
import com.jiuqi.va.filter.bill.formula.EnumDataFormulaContext;
import java.util.List;
import org.springframework.util.StringUtils;

public class EnumDataFormulaNodeProvider
implements IReportDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        if (!StringUtils.hasText(refName)) {
            throw new DynamicNodeException("\u672a\u77e5\u7684\u8bed\u6cd5\u6811\u8282\u70b9\uff1a" + token);
        }
        EnumDataFormulaContext ctx = (EnumDataFormulaContext)context;
        EnumDataFieldNode iastNodes = new EnumDataFieldNode(token, 6, ctx.getTitle(), ctx.getVal(), refName.toUpperCase().equals("VAL") ? 1 : 0);
        return iastNodes;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) {
        return null;
    }

    public IASTNode findSpec(IContext arg0, Token arg1, String arg2, String arg3) {
        return null;
    }

    public IASTNode findSpecial(IContext arg0, Token arg1, String arg2) {
        return null;
    }
}

