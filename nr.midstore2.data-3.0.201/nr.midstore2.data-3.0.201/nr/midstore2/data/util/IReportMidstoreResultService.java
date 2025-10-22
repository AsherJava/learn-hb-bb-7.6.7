/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject
 */
package nr.midstore2.data.util;

import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstoreResultService {
    public void addUnitErrorInfo(MistoreWorkResultObject var1, String var2, String var3, String var4);

    public void addUnitErrorInfo(ReportMidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4, String var5);

    public void addUnitErrorInfo(ReportMidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4);

    public void addFormErrorInfo(ReportMidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4, String var5, String var6, String var7, String var8);

    public void addTableErrorInfo(ReportMidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4, String var5, String var6);

    public void addTableErrorInfo(ReportMidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4, String var5, String var6, String var7, String var8);

    public void reSetErrorInfo(ReportMidstoreContext var1);
}

