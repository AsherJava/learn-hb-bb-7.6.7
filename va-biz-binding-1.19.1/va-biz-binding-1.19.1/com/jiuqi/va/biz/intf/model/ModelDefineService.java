/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.mapper.domain.TenantDO;

public interface ModelDefineService {
    public ModelDefine createDefine(String var1);

    public ModelDefine getDefine(String var1);

    public ModelDefine getDefine(String var1, String var2);

    public ModelDefine getDefine(String var1, Long var2);

    public ModelDefine getDefine(TenantDO var1);

    public Model createModel(ModelContext var1, ModelDefine var2);

    public Model createModel(ModelContext var1, String var2);

    public void clearCache(String var1, String var2);

    public void clearBillCache(String var1, String var2);

    public void clearLocalBillCache(String var1, String var2);
}

