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
 *  com.jiuqi.bi.util.IDCard
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
import com.jiuqi.bi.util.IDCard;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class IDCardDate
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 4743025307787387026L;

    public IDCardDate() {
        this.parameters().add(new Parameter("idcard", 6, "\u8eab\u4efd\u8bc1\u53f7\u7801"));
    }

    public static final long callFunction(String idcard) {
        if (idcard == null || !IDCard.isIDCard((String)idcard)) {
            return 0L;
        }
        Date date = IDCardDate.stringToDate(idcard.substring(6, 14), "yyyyMMdd");
        return date != null ? date.getTime() : 0L;
    }

    public static final boolean isNullResult(boolean idcard) {
        return idcard;
    }

    public String name() {
        return "IDCardDate";
    }

    public String title() {
        return "\u53d6\u8eab\u4efd\u8bc1\u65e5\u671f";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u4ece\u8eab\u4efd\u8bc1\u53f7");
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u53d6\u51fa\u751f\u65e5\u671f");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 2;
    }

    public String category() {
        return "\u4fe1\u606f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        if (p0 == null) {
            return null;
        }
        String idcard = (String)p0;
        if (!IDCard.isIDCard((String)idcard)) {
            return null;
        }
        idcard = IDCard.IDCard18((String)idcard);
        Calendar date = Calendar.getInstance();
        Date formatedDate = IDCardDate.stringToDate(idcard.substring(6, 14), "yyyyMMdd");
        if (formatedDate != null) {
            date.setTime(formatedDate);
        }
        return date.getTimeInMillis() == 0L ? null : date;
    }

    private static Date stringToDate(String dateString, String formatStr) {
        try {
            if (formatStr == null) {
                formatStr = "yyyy-MM-dd";
            }
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            format.setLenient(true);
            Date result = format.parse(dateString);
            return result;
        }
        catch (ParseException e) {
            return null;
        }
    }
}

