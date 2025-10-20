/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.model.ModelDefineImpl
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.mapper.domain.TenantDO;

public class BillPluginsDTO
extends TenantDO {
    private ModelDefineImpl datas;
    private DataDefineImpl dataDefine;

    public ModelDefineImpl getDatas() {
        return this.datas;
    }

    public void setDatas(ModelDefineImpl datas) {
        this.datas = datas;
    }

    public DataDefineImpl getDataDefine() {
        return this.dataDefine;
    }

    public void setDataDefine(DataDefineImpl dataDefine) {
        this.dataDefine = dataDefine;
    }
}

