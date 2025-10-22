/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.datacrud.RegionGradeInfo;
import java.util.List;
import java.util.Map;

public interface ICustomRegionsGradeService {
    public Map<String, RegionGradeInfo> getCustomRegionsGrade(List<String> var1, String var2);
}

