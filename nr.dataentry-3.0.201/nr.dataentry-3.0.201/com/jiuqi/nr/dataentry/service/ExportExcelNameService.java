/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.ExportRuleSettings;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface ExportExcelNameService {
    public String compileNameInfo(String var1, JtableContext var2, String var3, boolean var4, String var5);

    public String compileNameInfoWithSetting(String var1, JtableContext var2, String var3, boolean var4, String var5, ExportRuleSettings var6);

    public String getSysSeparator();

    public String getSysOptionOfExcelName();

    public String getPeriodTitle(String var1, String var2);
}

