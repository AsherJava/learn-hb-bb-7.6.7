/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.va.biz.intf.value.Convert
 */
package com.jiuqi.va.workflow.formula;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowNode;
import com.jiuqi.va.workflow.formula.WorkflowTableFieldNode;
import java.util.List;
import org.springframework.util.StringUtils;

public class WorkflowNodeProvider
implements IReportDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        if (!StringUtils.hasText(refName)) {
            throw new DynamicNodeException("\u672a\u77e5\u7684\u8bed\u6cd5\u6811\u8282\u70b9\uff1a" + token);
        }
        WorkflowContext cxt = (WorkflowContext)((Object)Convert.cast((Object)context, WorkflowContext.class));
        if (!cxt.getVariables().containsKey(refName) && !refName.startsWith("ARRAY")) {
            throw new DynamicNodeException(String.format("\u53c2\u6570%s\u4e0d\u5b58\u5728\u3002", refName));
        }
        return new WorkflowNode(token, refName);
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        if (objPath.size() != 2) {
            throw new DynamicNodeException("\u672a\u77e5\u7684\u8bed\u6cd5\u6811\u8282\u70b9\uff1a" + token);
        }
        String tableName = objPath.get(0);
        String fieldName = objPath.get(1);
        return new WorkflowTableFieldNode(context, token, tableName, fieldName);
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpec(IContext context, Token token, String tableName, String refName) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpecial(IContext context, Token token, String arg2) throws DynamicNodeException {
        return null;
    }
}

