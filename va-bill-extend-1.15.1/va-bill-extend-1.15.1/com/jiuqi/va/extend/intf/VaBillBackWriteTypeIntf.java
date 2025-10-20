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

public interface VaBillBackWriteTypeIntf {
    public String getType();

    public String getTitle();

    public String getTriggerType();

    public Class<? extends Model> getDependModel();

    public boolean needExecute(BillModel var1);
}

