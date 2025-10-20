/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Model
 */
package com.jiuqi.va.bill.action.param;

import com.jiuqi.va.bill.action.param.ActionParamItem;
import com.jiuqi.va.biz.intf.model.Model;
import java.util.List;

public interface CommitActionParam {
    public Class<? extends Model> getDependModel();

    public List<ActionParamItem> getParams();
}

