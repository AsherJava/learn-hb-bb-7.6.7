/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formulaschemeconfig.utils;

import java.util.ArrayList;
import java.util.List;

public class BillFormulaSchemeConfigUtils {
    public static final String BILLFETCH = "billFetch";

    public static List<String> listBillTitleName(String tabSelect) {
        ArrayList<String> titleNames = new ArrayList<String>();
        titleNames.add("\u5355\u636e");
        if ("batchStrategy".equals(tabSelect)) {
            titleNames.add("\u5408\u5e76\u5355\u4f4d");
        } else if ("batchUnit".equals(tabSelect)) {
            titleNames.add("\u5355\u4f4d");
        }
        titleNames.add("\u53d6\u6570\u65b9\u6848");
        return titleNames;
    }

    public static List<String> listTitleCode() {
        ArrayList<String> titleCodes = new ArrayList<String>();
        titleCodes.add("billId");
        titleCodes.add("orgId");
        titleCodes.add("fetchScheme");
        return titleCodes;
    }
}

