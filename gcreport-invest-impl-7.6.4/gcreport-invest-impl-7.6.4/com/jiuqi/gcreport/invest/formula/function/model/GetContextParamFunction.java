/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.gcreport.invest.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetContextParamFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;
    private static final String PREFIXKEY = "X--";

    public GetContextParamFunction() {
        this.parameters().add(new Parameter("key", 6, "\u4e0a\u4e0b\u6587\u53c2\u6570\u6807\u8bc6"));
    }

    public String addDescribe() {
        return "\u83b7\u53d6\u4e0a\u4e0b\u6587\u7279\u5b9a\u53c2\u6570\u7684\u503c";
    }

    public String name() {
        return "getContextParam";
    }

    public String title() {
        return "\u83b7\u53d6\u4e0a\u4e0b\u6587\u7279\u5b9a\u53c2\u6570\u7684\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("key").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u4e0a\u4e0b\u6587\u53c2\u6570\u6807\u8bc6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)1)).append("\uff1b").append("\u4e0a\u4e0b\u6587\u53c2\u6570\u5bf9\u5e94\u7684\u503c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u5f97\u5355\u636e\u53f0\u8d26\u4e0a\u4e0b\u6587\u53c2\u6570\uff1amergeUnitCode\u7684\u503c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("getContextParam('mergeUnitCode')").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String key = (String)parameters.get(0).evaluate(null);
        BillModelImpl model = (BillModelImpl)((ModelDataContext)context).model;
        return model.getContext().getContextValue(PREFIXKEY + key);
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return super.validate(context, parameters);
    }
}

