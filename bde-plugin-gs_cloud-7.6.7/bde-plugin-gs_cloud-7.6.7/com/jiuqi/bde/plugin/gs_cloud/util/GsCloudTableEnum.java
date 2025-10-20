/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.gs_cloud.util;

import java.util.ArrayList;

public enum GsCloudTableEnum {
    BFACCOUNTTITLE,
    BFACCOUNTINGDEPARTMENT,
    BFACCOUNTINGEMPLOYEE,
    BFCUSTOMITEM;

    private static final String TMPL_REGEX = "(?i)(%1$s)\\d{4}";
    private static final String TMPL_VALUE = "$%d";
    private static String regex;
    private static String valueTmpl;

    public static String replaceAccYear(String srcStr, int acctYear) {
        return srcStr.replaceAll(regex, valueTmpl + acctYear);
    }

    static {
        ArrayList<String> regexList = new ArrayList<String>();
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 0; i < GsCloudTableEnum.values().length; ++i) {
            GsCloudTableEnum tableEnum = GsCloudTableEnum.values()[i];
            regexList.add(String.format(TMPL_REGEX, tableEnum.name()));
            valueBuilder.append(String.format(TMPL_VALUE, i + 1));
        }
        regex = String.join((CharSequence)"|", regexList);
        valueTmpl = valueBuilder.toString();
    }
}

