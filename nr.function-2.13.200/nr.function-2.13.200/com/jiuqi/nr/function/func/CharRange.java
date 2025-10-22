/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public class CharRange
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 1483792689630084829L;

    public CharRange() {
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
        this.parameters().add(new Parameter("startChar", 6, "\u8d77\u59cb\u5b57\u7b26"));
        this.parameters().add(new Parameter("endChar", 6, "\u7ed3\u675f\u5b57\u7b26"));
    }

    public String name() {
        return "CharRange";
    }

    public String title() {
        return "\u5224\u65ad\u5b57\u6bb5\u4e32\u6bcf\u4e2a\u5b57\u6bcd\u662f\u5426\u5728\u67d0\u8303\u56f4\u5185";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return true;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("CharRange()\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String text = (String)obj;
        obj = parameters.get(1).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("CharRange()\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String startChar = (String)obj;
        obj = parameters.get(2).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("CharRange()\u7684\u7b2c\u4e09\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String endChar = (String)obj;
        return CharRange.callFunction(text, startChar, endChar);
    }

    public static boolean callFunction(String text, String startCharStr, String endCharStr) {
        char[] chars = text.toCharArray();
        char startChar = startCharStr.charAt(0);
        char endChar = endCharStr.charAt(0);
        boolean result = true;
        for (char c : chars) {
            if (c >= startChar && c <= endChar) continue;
            result = false;
            break;
        }
        return result;
    }
}

