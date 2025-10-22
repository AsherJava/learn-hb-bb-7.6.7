/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.chinese.ChineseInt
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.chinese.ChineseInt;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetRowNum
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 7339202110716339106L;

    public GetRowNum() {
        this.parameters().add(new Parameter("start", 3, "\u5f00\u59cb\u884c\u53f7"));
        this.parameters().add(new Parameter("increment", 3, "\u589e\u91cf"));
        this.parameters().add(new Parameter("format", 6, "\u884c\u53f7\u683c\u5f0f", true));
    }

    public String name() {
        return "GETROWNUM";
    }

    public String title() {
        return "\u83b7\u53d6\u884c\u5e8f\u53f7";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u4ed6";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object start = parameters.get(0).evaluate(context);
        if (!(start instanceof Number)) {
            return null;
        }
        Object increment = parameters.get(1).evaluate(context);
        if (!(increment instanceof Number)) {
            increment = 1;
        }
        Object format = parameters.size() <= 2 ? null : parameters.get(2).evaluate(context);
        QueryContext qContext = (QueryContext)context;
        int rowNum = qContext.generateRowNum((Number)start, (Number)increment);
        if (format == null || StringUtils.isEmpty((String)format.toString()) || format == "0") {
            return Integer.toString(rowNum);
        }
        if (format.toString().equals("A") || format.toString().equals("a")) {
            return GetRowNum.intToAlphabetStr(rowNum);
        }
        if (format.toString().equals("\u4e00")) {
            return ChineseInt.parseToChinese((String)String.valueOf(rowNum), (boolean)true, (boolean)false);
        }
        return Integer.toString(rowNum);
    }

    public static String intToAlphabetStr(int rowNum) {
        if (rowNum <= 0) {
            return Integer.toString(rowNum);
        }
        ArrayList<Character> characters = new ArrayList<Character>();
        while (rowNum > 0) {
            char ch = (char)(rowNum % 26 + 65 - 1);
            rowNum /= 26;
            if (ch < 'A') {
                ch = 'Z';
                --rowNum;
            }
            characters.add(Character.valueOf(ch));
        }
        Collections.reverse(characters);
        return StringUtils.join((Object[])characters.toArray(), (String)"");
    }
}

