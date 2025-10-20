/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.util.ASTHelper
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.HyperlinkData;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.util.ASTHelper;
import com.jiuqi.bi.util.StringUtils;
import java.text.Format;
import java.util.List;

public class Q_LinkOf
extends Function {
    private static final long serialVersionUID = 1L;

    public Q_LinkOf() {
        this.parameters().add(new Parameter("text", 0, "\u663e\u793a\u6587\u672c"));
        this.parameters().add(new Parameter("url", 6, "\u94fe\u63a5\u76ee\u6807URL"));
        this.parameters().add(new Parameter("target", 6, "\u94fe\u63a5\u76ee\u6807\u7a97\u53e3", true));
    }

    public String name() {
        return "Q_LinkOf";
    }

    public String title() {
        return "\u751f\u6210\u5355\u5143\u683c\u8d85\u94fe\u63a5\u4fe1\u606f";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 5051;
    }

    public String category() {
        return "\u5206\u6790\u8868\u51fd\u6570";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String url;
        ReportContext rptContext = (ReportContext)context;
        String text = this.formatValue(context, parameters.get(0));
        rptContext.setFormattable(false);
        try {
            url = (String)parameters.get(1).evaluate(context);
        }
        finally {
            rptContext.setFormattable(true);
        }
        if (StringUtils.isEmpty((String)url)) {
            throw new SyntaxException(parameters.get(1).getToken(), "\u94fe\u63a5\u76ee\u6807\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        String target = null;
        if (parameters.size() > 2) {
            target = (String)parameters.get(2).evaluate(context);
        }
        return new HyperlinkData(text, url, target);
    }

    private String formatValue(IContext context, IASTNode param) throws SyntaxException {
        Object value = param.evaluate(context);
        if (value == null) {
            return null;
        }
        Format formator = ASTHelper.getNodeFormat((IContext)context, (IASTNode)param);
        if (formator != null) {
            return formator.format(value);
        }
        return DataType.formatValue((int)0, (Object)value);
    }
}

