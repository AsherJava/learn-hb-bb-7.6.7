/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public final class IDC
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -2832861125848857421L;
    private static int[] IDC_WEIGHTs = new int[]{3, 7, 9, 10, 5, 8, 4, 2};

    public IDC() {
        this.parameters().add(new Parameter("UnitCode", 6, "\u5355\u4f4d\u6cd5\u4eba\u4ee3\u7801"));
    }

    private static int getIDCHash(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if ((c = Character.toUpperCase(c)) >= 'A' && c <= 'Z') {
            return c - 65 + 10;
        }
        return c == '#' ? 36 : -1;
    }

    public static char getIDCCode(String unitCode) {
        if (unitCode == null || unitCode.length() < 8) {
            return '\u0000';
        }
        int hash = 0;
        for (int i = 0; i < 8; ++i) {
            int h = IDC.getIDCHash(unitCode.charAt(i));
            if (h < 0) {
                return '\u0000';
            }
            hash += h * IDC_WEIGHTs[i];
        }
        if ((hash = 11 - hash % 11) < 10) {
            return (char)(hash + 48);
        }
        if (hash > 10) {
            return '0';
        }
        return 'X';
    }

    public static final String callFunction(String unitCode) {
        char code = IDC.getIDCCode(unitCode);
        return code != '\u0000' ? String.valueOf(code) : null;
    }

    public static final boolean isNullResult(boolean unitCode) {
        return unitCode;
    }

    public String name() {
        return "IDC";
    }

    public String title() {
        return "\u5355\u4f4d\u6cd5\u4eba\u4ee3\u7801\u7684\u6821\u9a8c\u4f4d";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u4f01\u4e1a\u6cd5\u4eba\u4ee3\u7801\u7684\u6821\u9a8c\u7801\u6b63\u786e");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u4fe1\u606f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        String unitCode = (String)p0;
        char code = IDC.getIDCCode(unitCode);
        return code != '\u0000' ? String.valueOf(code) : null;
    }
}

