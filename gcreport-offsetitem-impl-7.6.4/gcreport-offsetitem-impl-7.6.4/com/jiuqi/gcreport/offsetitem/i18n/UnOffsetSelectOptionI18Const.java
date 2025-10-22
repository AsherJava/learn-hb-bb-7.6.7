/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.i18n;

import java.util.HashMap;
import java.util.Map;

public class UnOffsetSelectOptionI18Const {
    private static final String UNIT = "gc.calculate.unoffset.selectoption.UNIT";
    private static final String RULE = "gc.calculate.unoffset.selectoption.RULE";
    private static final String AMT = "gc.calculate.unoffset.selectoption.AMT";
    private static final String UNITGROUP = "gc.calculate.unoffset.selectoption.UNITGROUP";
    private static final String RULESUMMARY = "gc.calculate.unoffset.selectoption.RULESUMMARY";
    private static final String CHILDRENUNITGROUP = "gc.calculate.unoffset.selectoption.CHILDRENUNITGROUP";
    private static Map<String, String> code2I18nCodeMap = new HashMap<String, String>();

    public static String getI18nForCode(String code) {
        if (code2I18nCodeMap.isEmpty()) {
            code2I18nCodeMap.put("UNIT", UNIT);
            code2I18nCodeMap.put("RULE", RULE);
            code2I18nCodeMap.put("AMT", AMT);
            code2I18nCodeMap.put("UNITGROUP", UNITGROUP);
            code2I18nCodeMap.put("RULESUMMARY", RULESUMMARY);
            code2I18nCodeMap.put("CHILDRENUNITGROUP", CHILDRENUNITGROUP);
        }
        return code2I18nCodeMap.get(code);
    }
}

