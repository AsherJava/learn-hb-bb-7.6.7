/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.bill.intf.BillModel;
import java.util.List;
import java.util.Map;

public interface IBillSublistImportValidator {
    public List<Map<String, String>> validator(List<Map<String, Object>> var1, List<Map<String, Object>> var2, BillModel var3, String var4);

    public boolean isEnable(BillModel var1, String var2);
}

