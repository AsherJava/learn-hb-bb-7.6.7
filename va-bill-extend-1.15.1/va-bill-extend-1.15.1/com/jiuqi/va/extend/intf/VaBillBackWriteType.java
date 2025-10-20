/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.intf.model.Model
 */
package com.jiuqi.va.extend.intf;

import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.extend.common.VaBackWriteTriggerTypeEnum;

public interface VaBillBackWriteType {
    public String getType();

    public String getTitle();

    public VaBackWriteTriggerTypeEnum getTime();

    public Class<? extends Model> getDependModel();

    public boolean needExecute(BillModel var1);
}

