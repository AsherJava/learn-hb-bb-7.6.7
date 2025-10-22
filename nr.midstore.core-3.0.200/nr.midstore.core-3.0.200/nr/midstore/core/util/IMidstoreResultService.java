/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.util;

import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MistoreWorkResultObject;

public interface IMidstoreResultService {
    public void addUnitErrorInfo(MistoreWorkResultObject var1, String var2, String var3, String var4);

    public void addUnitErrorInfo(MidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4, String var5);

    public void addUnitErrorInfo(MidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4);

    public void addFormErrorInfo(MidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4, String var5, String var6, String var7, String var8);

    public void addTableErrorInfo(MidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4, String var5, String var6);

    public void addTableErrorInfo(MidstoreContext var1, MistoreWorkResultObject var2, String var3, String var4, String var5, String var6, String var7, String var8);

    public void reSetErrorInfo(MidstoreContext var1);
}

