/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.intf.model.Model;

public interface BillFrontDefineService {
    public FrontModelDefine getDefine(Model var1, String var2, String var3);

    public FrontModelDefine getDefine(Model var1, String var2, String var3, long var4);

    public FrontModelDefine getExternalDefine(Model var1, String var2, String var3);

    public void clearCache(String var1, String var2);
}

