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
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.ArrayList;
import java.util.List;

public class GetUpperLevelCode
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 3873491640377974044L;

    public GetUpperLevelCode() {
        this.parameters().add(new Parameter("code", 6, "\u539f\u59cb\u7f16\u7801"));
        this.parameters().add(new Parameter("structure", 6, "\u7f16\u7801\u7ed3\u6784"));
        this.parameters().add(new Parameter("sameWidth", 1, "\u662f\u5426\u5b9a\u957f\u7f16\u7801", true));
    }

    public String name() {
        return "GETUPPERLEVELCODE";
    }

    public String title() {
        return "\u53d6\u4e0a\u7ea7\u7f16\u7801";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public boolean isDeprecated() {
        return true;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return null;
        }
        String levelCode = obj.toString();
        if (StringUtils.isEmpty((String)levelCode)) {
            return null;
        }
        boolean sameWidth = parameters.size() < 3 ? true : this.getBoolenData(parameters.get(2).evaluate(context));
        List<Integer> levelLengths = this.getLevelLengths(parameters.get(1).evaluate(context));
        Integer codeLength = levelLengths.get(levelLengths.size() - 1);
        String resultCode = this.getUpperLevelCode(levelCode, levelLengths, codeLength, sameWidth);
        return resultCode;
    }

    private String getUpperLevelCode(String codeData, List<Integer> levelLengths, Integer codeLength, Boolean sameWidth) {
        int length = codeData.length();
        Integer i = levelLengths.size() - 1;
        while (i >= 0) {
            int len = levelLengths.get(i);
            boolean hasFind = false;
            while (length > len) {
                char ch;
                hasFind = true;
                if ((ch = codeData.charAt(--length)) == '0') continue;
                return sameWidth != false ? this.getWidthCode(codeData, codeLength, len) : codeData.substring(0, len);
            }
            if (hasFind && !sameWidth.booleanValue()) {
                return codeData.substring(0, length);
            }
            Integer n = i;
            Integer n2 = i = Integer.valueOf(i - 1);
        }
        return "";
    }

    private String getWidthCode(String codeData, int codeLength, int len) {
        int i;
        char[] buffer = new char[codeLength];
        for (i = 0; i < len; ++i) {
            buffer[i] = codeData.charAt(i);
        }
        for (i = len; i < codeLength; ++i) {
            buffer[i] = 48;
        }
        return new String(buffer);
    }

    private List<Integer> getLevelLengths(Object value) throws SyntaxException {
        if (value == null || StringUtils.isEmpty((String)value.toString())) {
            throw new SyntaxException("\u7f16\u7801\u7ed3\u6784\u65e0\u6548\uff0c\u5e94\u8be5\u4e3a\u9017\u53f7\u5206\u9694\u7684\u5bbd\u5ea6\u5217\u8868\uff0c\u5f62\u5982\u201c2,2,2\u201d");
        }
        String structure = value.toString();
        ArrayList<Integer> levelArray = new ArrayList<Integer>();
        try {
            String[] levelObjs = structure.split(",|;");
            int codeLength = 0;
            for (String levelObj : levelObjs) {
                int level = Integer.parseInt(levelObj);
                if (level <= 0) {
                    throw new SyntaxException("\u7f16\u7801\u7ed3\u6784\u65e0\u6548\uff1a\u7f16\u7801\u5bbd\u5ea6\u5e94\u5927\u4e8e0");
                }
                levelArray.add(codeLength += level);
            }
        }
        catch (Exception e) {
            throw new SyntaxException("\u7f16\u7801\u7ed3\u6784\u65e0\u6548\uff1a" + e.getMessage());
        }
        return levelArray;
    }

    private boolean getBoolenData(Object value) {
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        if (value instanceof Number) {
            return DataType.compare((double)((Number)value).doubleValue(), (double)0.0) != 0;
        }
        return false;
    }
}

