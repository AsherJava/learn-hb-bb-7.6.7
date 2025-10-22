/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.util;

import java.util.HashMap;
import java.util.Map;

public class InputDataCheckLevelOptionI18Const {
    private static final String ALL_MERGE = "gc.inputdata.checklevel.allmerge";
    private static final String CURRENT_MERGE = "gc.inputdata.checklevel.currentmerge";
    private static final String PARENT_MERGE = "gc.inputdata.checklevel.parentmerge";
    private static final String CHILDREN_MERGE = "gc.inputdata.checklevel.childrenmerge";
    private static final String CUSTOM = "gc.inputdata.checklevel.custom";
    private static Map<String, String> code2I18nCodeMap = new HashMap<String, String>();

    public static String getI18nForCode(String code) {
        if (code2I18nCodeMap.isEmpty()) {
            code2I18nCodeMap.put("ALL_MERGE", ALL_MERGE);
            code2I18nCodeMap.put("CURRENT_MERGE", CURRENT_MERGE);
            code2I18nCodeMap.put("PARENT_MERGE", PARENT_MERGE);
            code2I18nCodeMap.put("CHILDREN_MERGE", CHILDREN_MERGE);
            code2I18nCodeMap.put("CUSTOM", CUSTOM);
        }
        return code2I18nCodeMap.get(code);
    }
}

