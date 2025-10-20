/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataUpdate
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.Map;

public interface BillDataService {
    public Map<String, List<Map<String, Object>>> load(BillContext var1, String var2, String var3);

    public Map<String, List<List<Object>>> load(BillContext var1, String var2, String var3, boolean var4);

    public Map<String, List<Map<String, Object>>> save(BillContext var1, String var2, boolean var3, Map<String, List<Map<String, Object>>> var4);

    public Map<String, List<Map<String, Object>>> update(BillContext var1, String var2, String var3, Map<String, ? extends DataUpdate> var4);

    public void delete(BillContext var1, String var2, String var3);

    public Map<String, R<?>> batchDelete(BillContext var1, Map<String, List<String>> var2);

    public BillDefine getBillDefineByCode(TenantDO var1);
}

