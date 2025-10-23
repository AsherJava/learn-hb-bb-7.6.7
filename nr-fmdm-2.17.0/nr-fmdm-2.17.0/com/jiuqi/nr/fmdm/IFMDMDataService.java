/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.fmdm;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMUpdateResult;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import java.util.List;

public interface IFMDMDataService {
    @Deprecated
    public List<IFMDMData> list(FMDMDataDTO var1);

    public IFMDMData queryFmdmData(FMDMDataDTO var1, DimensionCombination var2);

    public List<IFMDMData> list(FMDMDataDTO var1, DimensionCollection var2);

    public IFMDMUpdateResult add(FMDMDataDTO var1) throws FMDMUpdateException;

    public IFMDMUpdateResult update(FMDMDataDTO var1) throws FMDMUpdateException;

    public IFMDMUpdateResult delete(FMDMDataDTO var1) throws FMDMUpdateException;

    public IFMDMUpdateResult batchAddFMDM(BatchFMDMDTO var1) throws FMDMUpdateException;

    public IFMDMUpdateResult batchUpdateFMDM(BatchFMDMDTO var1) throws FMDMUpdateException;
}

