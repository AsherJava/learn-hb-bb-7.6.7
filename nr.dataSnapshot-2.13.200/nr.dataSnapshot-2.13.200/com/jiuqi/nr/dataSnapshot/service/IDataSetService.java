/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataSnapshot.service;

import com.jiuqi.nr.dataSnapshot.param.FormInfo;
import com.jiuqi.nr.dataSnapshot.param.FormSchemeInfo;
import com.jiuqi.nr.dataSnapshot.param.RegionInfo;
import java.util.List;

public interface IDataSetService {
    public List<FormSchemeInfo> getformSchemes(String var1);

    public List<FormInfo> getFormInfo(String var1);

    public List<RegionInfo> getRegionInfo(String var1);
}

