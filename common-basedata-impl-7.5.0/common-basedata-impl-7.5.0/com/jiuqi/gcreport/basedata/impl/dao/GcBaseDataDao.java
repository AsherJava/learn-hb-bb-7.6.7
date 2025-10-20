/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.impl.dao;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import java.util.List;

public interface GcBaseDataDao {
    public List<GcBaseData> queryBaseDataSimpleItems(String var1);

    public GcBaseData queryBaseDataSimpleItem(String var1, String var2);
}

