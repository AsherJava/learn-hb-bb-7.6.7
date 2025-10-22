/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datastatus.facade.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.datastatus.facade.obj.BatchRefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.ICopySetting;
import com.jiuqi.nr.datastatus.facade.obj.RefreshStatusPar;
import com.jiuqi.nr.datastatus.facade.obj.RollbackStatusPar;
import java.util.List;

public interface IDataStatusService {
    public List<String> getFilledPeriod(String var1, DimensionCollection var2);

    public List<String> getFilledAdjust(String var1, String var2);

    public List<String> getFilledUnit(String var1, DimensionCollection var2);

    public List<String> getFilledFormKey(String var1, DimensionCombination var2);

    public void refreshDataStatus(RefreshStatusPar var1) throws Exception;

    public void rollbackDataStatus(RollbackStatusPar var1) throws Exception;

    public void clearDataStatusByForm(ClearStatusPar var1) throws Exception;

    public void copyDataStatus(ICopySetting var1) throws Exception;

    public void batchRefreshDataStatus(BatchRefreshStatusPar var1) throws Exception;
}

