/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.mapper.domain.TenantDO;

public interface BillDefineService {
    public BillDefine getDefine(String var1);

    public BillDefine getDefine(String var1, boolean var2);

    public BillDefine getDefine(String var1, String var2);

    public BillDefine getDefine(TenantDO var1);

    public BillModel createModel(BillContext var1, BillDefine var2);

    public BillModel createModel(BillContext var1, String var2);

    public BillModel createModel(BillContext var1, String var2, long var3);

    public BillModel createModel(BillContext var1, String var2, String var3);
}

