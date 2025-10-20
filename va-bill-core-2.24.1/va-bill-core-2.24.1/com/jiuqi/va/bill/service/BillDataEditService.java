/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Map;

public interface BillDataEditService {
    public R changeBillState(TenantDO var1);

    public Map<String, Object> getBussinessParamVariables(TenantDO var1);

    public Map<String, Object> getMessageTemplateParam(TenantDO var1);

    public Map<String, Object> getTodoParam(TenantDO var1);

    public Map<String, Object> loadTodoParam(BillModel var1, String var2);
}

