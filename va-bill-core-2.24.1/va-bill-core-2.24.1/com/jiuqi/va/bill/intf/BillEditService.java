/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefine;
import java.util.Map;

public interface BillEditService {
    public Map<String, Object> add(BillContext var1, String var2, long var3, String var5, String var6, Map<String, Object> var7);

    public Map<String, Object> edit(BillContext var1, String var2, long var3, String var5, String var6);

    public Map<String, Object> edit(BillContext var1, String var2, long var3, String var5, String var6, String var7);

    public Map<String, Object> load(BillContext var1, String var2, long var3, String var5, String var6);

    public Map<String, Object> load(BillContext var1, String var2, long var3, String var5, String var6, String var7);

    public Map<String, Object> load(BillContext var1, String var2, long var3, String var5, String var6, String var7, String var8);

    public Map<String, Object> load(BillContext var1, BillDefine var2, String var3, String var4);

    public Map<String, Object> sync(BillContext var1, String var2, long var3, String var5, String var6, Map<String, Object> var7, String var8);

    public Map<String, Object> add(BillContextImpl var1, String var2, long var3, String var5, Map<String, Object> var6, String var7);
}

