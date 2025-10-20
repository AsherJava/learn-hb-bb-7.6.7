/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CheckIdentityLegitimateFunction
extends ModelFunction {
    private static final Logger logger = LoggerFactory.getLogger(CheckIdentityLegitimateFunction.class);
    private static final Pattern isNumericP = Pattern.compile("[0-9]*");
    private static final Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
    private static final List<String> areaCodes = Arrays.asList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91");

    public CheckIdentityLegitimateFunction() {
        this.parameters().add(new Parameter("identity", 6, "\u8eab\u4efd\u8bc1\u53f7"));
    }

    @Override
    public String addDescribe() {
        return "\u6821\u9a8c\u5f53\u524d\u8f93\u5165\u7684\u8eab\u4efd\u8bc1\u662f\u5426\u5408\u6cd5";
    }

    public String name() {
        return "CheckIdentityLegitimate";
    }

    public String title() {
        return "\u8eab\u4efd\u8bc1\u53f7\u6821\u9a8c\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        String IDStr = (String)list.get(0).evaluate(iContext);
        String Ai = "";
        if (IDStr == null || IDStr.length() != 15 && IDStr.length() != 18) {
            return false;
        }
        Ai = IDStr.length() == 18 ? IDStr.substring(0, 17) : IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        if (!CheckIdentityLegitimateFunction.isNumeric(Ai)) {
            return false;
        }
        String strYear = Ai.substring(6, 10);
        String strMonth = Ai.substring(10, 12);
        String strDay = Ai.substring(12, 14);
        if (!CheckIdentityLegitimateFunction.isDate(strYear + "-" + strMonth + "-" + strDay)) {
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (gc.get(1) - Integer.parseInt(strYear) > 150 || gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime() < 0L) {
                return false;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            return false;
        }
        if (!areaCodes.contains(Ai.substring(0, 2))) {
            return false;
        }
        if (!CheckIdentityLegitimateFunction.isVarifyCode(Ai, IDStr)) {
            return false;
        }
        return true;
    }

    private static boolean isVarifyCode(String Ai, String IDStr) {
        String[] VarifyCode = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] Wi = new String[]{"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        int sum = 0;
        for (int i = 0; i < 17; ++i) {
            sum += Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = sum % 11;
        String strVerifyCode = VarifyCode[modValue];
        Ai = Ai + strVerifyCode;
        if (IDStr.length() == 18) {
            return Ai.equals(IDStr);
        }
        return true;
    }

    private static boolean isNumeric(String strnum) {
        return CheckIdentityLegitimateFunction.isNumber(strnum);
    }

    public static boolean isDate(String strDate) {
        return CheckIdentityLegitimateFunction.pattern(strDate);
    }

    public static boolean isNumber(String str) {
        Matcher m = isNumericP.matcher(str);
        return m.matches();
    }

    public static boolean pattern(String str) {
        Matcher m = p.matcher(str);
        return m.matches();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = FunctionUtils.handleFormula((IFunction)this);
        formulaDescription.setDescription(this.addDescribe());
        return formulaDescription;
    }
}

