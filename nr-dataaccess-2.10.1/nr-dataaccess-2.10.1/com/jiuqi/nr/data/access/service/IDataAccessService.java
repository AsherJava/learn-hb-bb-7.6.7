/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessExtendService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface IDataAccessService
extends IDataAccessExtendService {
    public IAccessResult visible(DimensionCombination var1, String var2);

    public IAccessResult zbVisible(DimensionCombination var1, String var2);

    public IAccessResult readable(DimensionCombination var1, String var2);

    public IAccessResult zbReadable(DimensionCombination var1, String var2);

    public IAccessResult writeable(DimensionCombination var1, String var2);

    public IAccessResult zbWriteable(DimensionCombination var1, String var2);

    public IAccessResult zbSysWriteable(DimensionCombination var1, String var2);

    public IAccessResult sysWriteable(DimensionCombination var1, String var2);

    public IBatchAccessResult getVisitAccess(DimensionCollection var1, List<String> var2);

    public IBatchZBAccessResult getZBVisitAccess(DimensionCollection var1, List<String> var2);

    public IBatchAccessResult getReadAccess(DimensionCollection var1, List<String> var2);

    public IBatchZBAccessResult getZBReadAccess(DimensionCollection var1, List<String> var2);

    public IBatchAccessResult getWriteAccess(DimensionCollection var1, List<String> var2);

    public IBatchZBAccessResult getZBWriteAccess(DimensionCollection var1, List<String> var2);

    public IBatchZBAccessResult getZBSysWriteAccess(DimensionCollection var1, List<String> var2);

    public IBatchAccessResult getSysWriteAccess(DimensionCollection var1, List<String> var2);
}

