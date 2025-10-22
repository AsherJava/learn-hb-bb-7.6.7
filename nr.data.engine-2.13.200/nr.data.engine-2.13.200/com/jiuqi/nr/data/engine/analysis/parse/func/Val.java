/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.data.engine.analysis.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Val
extends Function {
    private static final Logger logger = LoggerFactory.getLogger(Val.class);
    private static final long serialVersionUID = 3758095211416327526L;

    public Val() {
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("Val()\u7684\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String s = ((String)obj).trim();
        return Val.callFunction(s);
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String name() {
        return "Val";
    }

    public String title() {
        return "\u5c06\u6587\u672c\u8f6c\u5316\u4e3a\u6570\u503c";
    }

    public String[] aliases() {
        return new String[]{"Value"};
    }

    public boolean support(Language lang) {
        return true;
    }

    public static Object callFunction(String s) {
        try {
            if (s == null || s.length() == 0) {
                return null;
            }
            return Double.valueOf(s);
        }
        catch (NumberFormatException e) {
            char[] chars = s.toCharArray();
            int val = 0;
            for (char c : chars) {
                if (c < '0' || c > '9') break;
                int i = c - 48;
                val = val * 10 + i;
            }
            return val;
        }
    }

    public static void main(String[] args) {
        logger.info((String)Val.callFunction("5011\u7ea6"));
        logger.info((String)Val.callFunction("\u7ea650"));
        logger.info((String)Val.callFunction("5\u7ea60"));
    }
}

