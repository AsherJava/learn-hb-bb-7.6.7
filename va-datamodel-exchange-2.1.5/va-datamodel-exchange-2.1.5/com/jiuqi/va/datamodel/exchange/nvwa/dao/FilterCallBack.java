/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.dao;

import com.jiuqi.va.datamodel.exchange.nvwa.dao.VaFieldDefine;
import java.util.List;

public interface FilterCallBack {
    public String initFilter(List<VaFieldDefine> var1);

    public boolean hasField(String var1);
}

