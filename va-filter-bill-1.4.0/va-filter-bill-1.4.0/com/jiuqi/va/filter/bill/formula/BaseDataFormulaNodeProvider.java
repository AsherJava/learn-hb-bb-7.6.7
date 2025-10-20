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
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 */
package com.jiuqi.va.filter.bill.formula;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.filter.bill.formula.BaseDataFieldNode;
import com.jiuqi.va.filter.bill.formula.BaseDataFormulaContext;
import com.jiuqi.va.filter.bill.formula.BaseDataTableFieldNode;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import java.util.List;
import org.springframework.util.StringUtils;

public class BaseDataFormulaNodeProvider
implements IReportDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        if (!StringUtils.hasText(refName)) {
            throw new DynamicNodeException("\u672a\u77e5\u7684\u8bed\u6cd5\u6811\u8282\u70b9\uff1a" + token);
        }
        BaseDataFormulaContext cxt = (BaseDataFormulaContext)((Object)Convert.cast((Object)context, BaseDataFormulaContext.class));
        FunRefDataDefine refDataDefine = cxt.getBaseDataDefine().filter(o -> refName.toUpperCase().equals(o.getFieldName())).findFirst().orElse(null);
        if (refDataDefine == null) {
            throw new DynamicNodeException(String.format("\u5b57\u6bb5%s\u7684\u5b9a\u4e49\u4e0d\u5b58\u5728\u3002", refName));
        }
        return new BaseDataFieldNode(token, refDataDefine, context);
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        if (objPath.size() != 2) {
            throw new DynamicNodeException("\u672a\u77e5\u7684\u8bed\u6cd5\u6811\u8282\u70b9\uff1a" + token);
        }
        String tableName = objPath.get(0).toUpperCase();
        String fieldName = objPath.get(1).toUpperCase();
        return new BaseDataTableFieldNode(context, token, tableName, fieldName);
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

