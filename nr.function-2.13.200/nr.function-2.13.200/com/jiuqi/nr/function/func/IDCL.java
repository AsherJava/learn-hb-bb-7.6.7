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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class IDCL
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -2832861125848857421L;
    private static final int[] WEIGHTS = new int[]{1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
    private static final Map<Character, Integer> valueMap = new HashMap<Character, Integer>();

    public IDCL() {
        this.parameters().add(new Parameter("UnitCode", 6, "\u5355\u4f4d\u6cd5\u4eba\u4ee3\u7801"));
    }

    private static int calculateCharacterValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if ((c = Character.toUpperCase(c)) >= 'A' && c <= 'Z' || c == '#') {
            return valueMap.get(Character.valueOf(c));
        }
        return -1;
    }

    private static char getCheckBit(String unitCode) {
        int sum = 0;
        for (int i = 0; i < 17; ++i) {
            int h = IDCL.calculateCharacterValue(unitCode.charAt(i));
            if (h < 0) {
                return '\u0000';
            }
            sum += h * WEIGHTS[i];
        }
        int n = 31 - sum % 31;
        if (n < 10) {
            return (char)(n + 48);
        }
        if (n >= 31) {
            return '0';
        }
        for (Map.Entry<Character, Integer> entry : valueMap.entrySet()) {
            if (!entry.getValue().equals(n)) continue;
            return entry.getKey().charValue();
        }
        return '#';
    }

    public static final boolean isNullResult(boolean unitCode) {
        return unitCode;
    }

    public String name() {
        return "IDCL";
    }

    public String title() {
        return "\u8ba1\u7b97\u6cd5\u4eba\u548c\u5176\u4ed6\u7ec4\u7ec7\u7edf\u4e00\u793e\u4f1a\u4fe1\u7528\u4ee3\u7801\u6821\u9a8c\u4f4d";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7edf\u4e00\u793e\u4f1a\u4fe1\u7528\u4ee3\u7801\u7684\u6821\u9a8c\u4f4d");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u4fe1\u606f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String unitCode = (String)parameters.get(0).evaluate(context);
        return IDCL.callFunction(unitCode);
    }

    public static final String callFunction(String unitCode) {
        if (unitCode == null || unitCode.length() < 17) {
            return null;
        }
        char code = IDCL.getCheckBit(unitCode);
        return code != '\u0000' ? String.valueOf(code) : null;
    }

    static {
        valueMap.put(Character.valueOf('A'), 10);
        valueMap.put(Character.valueOf('B'), 11);
        valueMap.put(Character.valueOf('C'), 12);
        valueMap.put(Character.valueOf('D'), 13);
        valueMap.put(Character.valueOf('E'), 14);
        valueMap.put(Character.valueOf('F'), 15);
        valueMap.put(Character.valueOf('G'), 16);
        valueMap.put(Character.valueOf('H'), 17);
        valueMap.put(Character.valueOf('J'), 18);
        valueMap.put(Character.valueOf('K'), 19);
        valueMap.put(Character.valueOf('L'), 20);
        valueMap.put(Character.valueOf('M'), 21);
        valueMap.put(Character.valueOf('N'), 22);
        valueMap.put(Character.valueOf('P'), 23);
        valueMap.put(Character.valueOf('Q'), 24);
        valueMap.put(Character.valueOf('R'), 25);
        valueMap.put(Character.valueOf('T'), 26);
        valueMap.put(Character.valueOf('U'), 27);
        valueMap.put(Character.valueOf('W'), 28);
        valueMap.put(Character.valueOf('X'), 29);
        valueMap.put(Character.valueOf('Y'), 30);
        valueMap.put(Character.valueOf('I'), 31);
        valueMap.put(Character.valueOf('O'), 32);
        valueMap.put(Character.valueOf('Z'), 33);
        valueMap.put(Character.valueOf('S'), 34);
        valueMap.put(Character.valueOf('V'), 35);
        valueMap.put(Character.valueOf('#'), 36);
    }
}

